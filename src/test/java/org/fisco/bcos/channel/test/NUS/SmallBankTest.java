package org.fisco.bcos.channel.test.NUS;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.utils.Web3AsyncThreadPoolSize;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.Executors;
import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.util.concurrent.RateLimiter;

import java.util.List;
import java.math.BigInteger;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.Contract;
import org.fisco.bcos.web3j.tx.TransactionManager;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmallBankTest {
    final int userCount = 10000;

    private static Logger logger = LoggerFactory.getLogger(SmallBankTest.class);
    private Web3j web3;
    private Credentials credentials;
    private TransactionManager transactionManager;
    private SmallBank smallBank;
    private StaticGasProvider gasProvider;
    private Random random;
    private SmallBankCollector collector;
    private static CountDownLatch latch;

    public Web3j getWeb3() {
        return web3;
    }

    public void setWeb3(Web3j web3) {
        this.web3 = web3;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public SmallBankCollector getCollector() {
        return collector;
    }

    public void setCollector(SmallBankCollector collector) {
        this.collector = collector;
    }

    public SmallBankTest() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        Service service = context.getBean(Service.class);
        service.setGroupId(1);
        service.run();

        ChannelEthereumService channelEthereumService = new ChannelEthereumService();
        channelEthereumService.setChannelService(service);

        Web3AsyncThreadPoolSize.web3AsyncCorePoolSize = 3000;
        Web3AsyncThreadPoolSize.web3AsyncPoolSize = 2000;

        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(500);

        web3 = Web3j.build(channelEthereumService, 15 * 100, scheduledExecutorService, 1);
        credentials = GenCredential.create();
        transactionManager = Contract.getTheTransactionManager(web3, credentials);
        gasProvider = new StaticGasProvider(new BigInteger("30000000"), new BigInteger("30000000"));

        random = new Random(System.currentTimeMillis());
    }

    public String getUser() {
        int user = random.nextInt(userCount);
        return Integer.toString(user);
    }

    public void Test(int count, int qps) {
        List<String> signedTransactions = new ArrayList<String>();
        List<SmallBankCallback> callbacks = new ArrayList<SmallBankCallback>();
        Lock lock = new ReentrantLock();

        try {
            ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
            threadPool.setCorePoolSize(200);
            threadPool.setMaxPoolSize(500);
            threadPool.setQueueCapacity(count);
            threadPool.initialize();

            smallBank = SmallBank.deploy(web3, credentials, gasProvider).send();
            AtomicLong signed = new AtomicLong(0);
            latch = new CountDownLatch(count);

            System.out.println("creating transactions ...");
            for (int i = 0; i < count; ++i) {
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            String transaction = null;
                            int op = random.nextInt(6);

                            switch (op) {
                                case 0: {
                                    String userA = getUser();
                                    String userB = getUser();
                                    transaction = smallBank.almagateSeq(userA, userB);
                                    break;
                                }
                                case 1: {
                                    String user = getUser();
                                    transaction = smallBank.getBalanceSeq(user);
                                    break;
                                }
                                case 2: {
                                    String user = getUser();
                                    transaction = smallBank.updateBalanceSeq(user, new BigInteger("0"));
                                    break;
                                }
                                case 3: {
                                    String user = getUser();
                                    transaction = smallBank.updateSavingSeq(user, new BigInteger("0"));
                                    break;
                                }
                                case 4: {
                                    String userA = getUser();
                                    String userB = getUser();
                                    transaction = smallBank.sendPaymentSeq(userA, userB, new BigInteger("0"));
                                    break;
                                }
                                case 5: {
                                    String user = getUser();
                                    transaction = smallBank.writeCheckSeq(user, new BigInteger("0"));
                                    break;
                                }
                            }

                            SmallBankCallback callback = new SmallBankCallback();
                            callback.setCollector(collector);

                            try {
                                lock.lock();
                                signedTransactions.add(transaction);
                                callbacks.add(callback);

                                long currentSigned = signed.incrementAndGet();
                                if (currentSigned % (count / 10) == 0) {
                                    System.out.println(
                                            "already signed: " + String.valueOf(currentSigned * 100 / count) + "%");
                                }

                                break;
                            } catch (Exception e) {
                                e.printStackTrace();
                                continue;
                            } finally {
                                lock.unlock();
                            }
                        }
                        latch.countDown();
                    }
                });
            }
            latch.await();

            System.out.println("sending transactions ...");

            long startTime = System.currentTimeMillis();
            collector.setStartTimestamp(startTime);
            latch = new CountDownLatch(count);
            RateLimiter limiter = RateLimiter.create(qps);
            AtomicInteger sent = new AtomicInteger(0);

            for (int i = 0; i < count; ++i) {
                limiter.acquire();
                final int index = i;

                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                callbacks.get(index).recordStartTime();
                                transactionManager.sendTransaction(signedTransactions.get(index), callbacks.get(index));
                                break;
                            } catch (Exception e) {
                                continue;
                            }
                        }

                        int currentSent = sent.incrementAndGet();

                        if (currentSent % (count / 10) == 0) {
                            long elapsed = System.currentTimeMillis() - startTime;
                            double speed = currentSent / ((double) elapsed / 1000);
                            System.out.println("already sent: " + String.valueOf(currentSent * 100 / count + "%, QPS=" + speed));
                        }

                        latch.countDown();
                    }
                });
            }

            latch.await();

            while (!collector.isEnd()) {
                Thread.sleep(3000);
                logger.info(" received: {}, total: {}", collector.getReceived().intValue(), collector.getTotal());
            }

            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
