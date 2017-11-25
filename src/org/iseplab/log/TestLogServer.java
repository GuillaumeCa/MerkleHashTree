package org.iseplab.log;

import org.iseplab.merklehashtree.MerkleTree;

import javax.xml.bind.DatatypeConverter;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class TestLogServer {

    public static void main(String[] args) {

        try{


            LogServer ls = new LogServer("traces/DS1-trace.txt");

            System.out.println("Root Hash :");
            System.out.println(ls.getRootHash());
            System.out.println();


            // Test add()
            ArrayList<String> logs = new ArrayList<>();
            logs.add("START 1");
            logs.add("START 1");
            logs.add("START 1");
            logs.add("START 1");
            logs.add("START 1");

            ls.add(logs);


            System.out.println("Root Hash after ADD :");
            System.out.println(ls.getRootHash());
            System.out.println();


            // Test genPath()
            ArrayList<MerkleTree> treeList = ls.genPath(1);

            System.out.println("GenPath Test : ");

            for(MerkleTree tree : treeList) {
                System.out.println(tree);
            }
            System.out.println();


            // Test genProof()
            ArrayList<MerkleTree> proofList = ls.genProof(6);

            System.out.println("GenProof Test : ");

            for(MerkleTree proof : proofList) {
                System.out.println(proof);
            }
            System.out.println();

        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}
