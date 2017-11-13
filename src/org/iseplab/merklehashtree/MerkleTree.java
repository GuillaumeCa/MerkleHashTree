package org.iseplab.merklehashtree;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Guillaume on 13/11/2017.
 * MerkleHashTree
 */
public class MerkleTree {
    private byte[] hash;
    private MerkleTree leftNode;
    private MerkleTree rightNode;

    private int beginIndex;
    private int endIndex;


    public MerkleTree(String event) {
        sha256(0x00 | event.getBytes("UTF-8"));
    }

    private byte[] sha256(byte[] data) {
        try {
            MessageDigest digest= null;
            digest = MessageDigest.getInstance("SHA256");
            byte [] hash = digest.digest(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
