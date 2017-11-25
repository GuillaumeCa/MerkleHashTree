package org.iseplab.log;

public class TestLogServer {

    public static void main(String[] args) {
        LogServer ls = new LogServer("traces/DS1-trace.txt");

        System.out.println(ls.getRootHash());
    }
}
