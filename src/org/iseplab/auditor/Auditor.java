package org.iseplab.auditor;

import org.iseplab.log.LogServer;
import org.iseplab.merklehashtree.MerkleTree;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Auditor {

    private LogServer server;
    private byte[] rootHash;
    private int treeSize;

    public Auditor(LogServer ls) {
        this.server = ls;
        this.rootHash = ls.getTree().getHash();
        this.treeSize = ls.getTree().getNbLeaves();

        this.displayTreeInfos();
    }

    public void displayTreeInfos() {
        System.out.println("Root Hash : ");
        System.out.println(DatatypeConverter.printHexBinary(this.rootHash));

        System.out.println("Tree size : " + this.treeSize);
    }

    public boolean isMember(String event, int index) {

        byte[] calculatedHash;

        try {
            ArrayList<MerkleTree> treeList = this.server.genPath(index);

            calculatedHash = this.sha256((char) 0x00 + event);

            int minIndex = index;
            int maxIndex = index;

            for(MerkleTree node : treeList) {
                if(maxIndex > node.getEndIndex()) {
                    calculatedHash = this.sha256((char) 0x01  + new String(node.getHash())+ new String(calculatedHash));
                    minIndex = node.getBeginIndex();
                }

                else if( minIndex < node.getBeginIndex()) {
                    calculatedHash = this.sha256((char) 0x01 + new String(calculatedHash + new String(node.getHash())));
                    maxIndex = node.getEndIndex();
                }
            }

            return this.compareHash(this.rootHash, calculatedHash);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    private boolean compareHash(byte[] hash1, byte[] hash2) {
        boolean equals = true;
        int i =0;

        if(hash1.length != hash2.length) return false;

        while(equals && i<hash1.length) {
            equals = hash1[i] == hash2[i];
            i++;
        }

        return equals;
    }

    private byte[] sha256(String data) throws Exception {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(data.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new Exception("cannot hash data: "+data, e);
        }
    }
}
