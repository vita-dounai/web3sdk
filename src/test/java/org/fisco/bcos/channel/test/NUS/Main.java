package org.fisco.bcos.channel.test.NUS;

public class Main {
    public static void Usage() {
        System.out.println("Usage:");
        System.out.println(
                " \tjava -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.NUS.Main count qps hotCount hotProb");
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 4) {
            Usage();
        }

        int count = Integer.parseInt(args[0]);
        int qps = Integer.parseInt(args[1]);
        int hotCount = Integer.parseInt(args[2]);
        int hotProb = Integer.parseInt(args[3]);

        SmallBankCollector collector = new SmallBankCollector();
        collector.setTotal(count);

        SmallBankTest test = new SmallBankTest(hotCount, hotProb);
        test.setCollector(collector);
        test.Test(count, qps);
    }
}