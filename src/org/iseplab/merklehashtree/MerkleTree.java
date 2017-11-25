package org.iseplab.merklehashtree;

import javax.xml.bind.DatatypeConverter;
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

    private int nbLeaves;

    private int maxNbLeaves;

    /**
     * Create a MerkelTree for a leaf
     * @param event
     * @throws Exception
     */
    public MerkleTree(String event, int line) throws Exception {
        this.hash = sha256((char) 0x00 + event);
        this.beginIndex = line;
        this.endIndex = line;
        this.nbLeaves = 1;
        this.maxNbLeaves = 1;
    }

    /**
     * Create a MerkelTree for an internal Node
     * @param leftNode
     * @param rightNode
     * @throws Exception
     */
    public MerkleTree(MerkleTree leftNode, MerkleTree rightNode) throws Exception {
        this.leftNode = leftNode;
        this.rightNode = rightNode;

        this.beginIndex = leftNode.beginIndex;
        this.endIndex = rightNode.endIndex;
        this.nbLeaves = leftNode.nbLeaves + rightNode.nbLeaves;

        int maxSubtreeLeaves = Math.max(leftNode.getMaxNbLeaves(), rightNode.getMaxNbLeaves());
        this.maxNbLeaves = this.nbLeaves < maxSubtreeLeaves ? maxSubtreeLeaves : 2 * maxSubtreeLeaves;

        this.hash = sha256((char) 0x01 + new String(leftNode.getHash()) + new String(rightNode.getHash()));
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getNbLeaves() {
        return nbLeaves;
    }

    public void setNbLeaves(int nbLeaves) {
        this.nbLeaves = nbLeaves;
    }

    public int getMaxNbLeaves() {
        return maxNbLeaves;
    }

    public void setMaxNbLeaves(int maxNbLeaves) {
        this.maxNbLeaves = maxNbLeaves;
    }

    public MerkleTree getLeftNode() {
        return leftNode;
    }

    public MerkleTree getRightNode() {
        return rightNode;
    }

    private byte[] sha256(String data) throws Exception {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(data.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new Exception("cannot hash data: "+data, e);
        }
    }

    public byte[] getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return DatatypeConverter.printHexBinary(hash) +" - h: " + beginIndex + ".." + endIndex;
    }
}
