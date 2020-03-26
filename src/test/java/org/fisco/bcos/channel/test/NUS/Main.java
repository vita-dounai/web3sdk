package org.fisco.bcos.channel.test.NUS;

public class Main {
    public static void Usage() {
        System.out.println("Usage:");
        System.out.println(" \tjava -cp conf/:lib/*:apps/* org.fisco.bcos.channel.test.NUS.Main count qps");
        System.exit(0);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            Usage();
        }

        int count = Integer.parseInt(args[0]);
        int qps = Integer.parseInt(args[1]);

        SmallBankCollector collector = new SmallBankCollector();
        collector.setTotal(count);

        SmallBankTest test = new SmallBankTest();
        test.setCollector(collector);
        test.Test(count, qps);
    }
}