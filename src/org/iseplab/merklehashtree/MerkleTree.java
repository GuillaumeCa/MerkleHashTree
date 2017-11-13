package org.iseplab.merklehashtree;

import java.io.UnsupportedEncodingException;
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
        hash = sha256((char) 0x00 + event);
    }

    public MerkleTree(MerkleTree leftNode, MerkleTree rightNode) {
        String leftHash = new String(leftNode.getHash());
        String rightHash = new String(rightNode.getHash());
        hash = sha256((char) 0x01 + leftHash + rightHash);
    }

    private byte[] sha256(String data) {
        try {
            MessageDigest digest= null;
            digest = MessageDigest.getInstance("SHA256");
            byte [] hash = digest.digest(data.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public byte[] getHash() {
        return hash;
    }
}
