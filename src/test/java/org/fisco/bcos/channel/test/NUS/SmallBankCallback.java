package org.fisco.bcos.channel.test.NUS;

import java.math.BigInteger;
import org.fisco.bcos.channel.client.TransactionSucCallback;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmallBankCallback extends TransactionSucCallback {
    static Logger logger = LoggerFactory.getLogger(SmallBankCallback.class);

    private Long startTime;
    private SmallBankCollector collector = null;
    private BigInteger amount = null;

    public void recordStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    public SmallBankCollector getCollector() {
        return collector;
    }

    public void setCollector(SmallBankCollector collector) {
        this.collector = collector;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public SmallBankCallback() {
    }

    @Override
    public void onResponse(TransactionReceipt receipt) {
        Long cost = System.currentTimeMillis() - startTime;

        try {
            collector.onMessage(receipt, cost);
        } catch (Exception e) {
            logger.error("onMessage error: ", e);
        }
    }
}
