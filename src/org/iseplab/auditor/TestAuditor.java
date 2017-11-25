package org.iseplab.auditor;

import org.iseplab.log.LogServer;

public class TestAuditor {

    public static void main(String[] args) {

        try{
            LogServer ls = new LogServer("traces/DS1-trace.txt");
            ls.add("Start");

            Auditor audit = new Auditor(ls);

            // Test isMember()
            System.out.println("isMember result : " + audit.isMember("Start", audit.getTreeSize()));

        } catch(Exception e) {
            e.printStackTrace();
        }


    }
}
