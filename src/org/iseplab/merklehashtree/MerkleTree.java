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

    /**
     * Create a MerkelTree for a leaf
     * @param event
     * @throws Exception
     */
    public MerkleTree(String event) throws Exception {
        this.hash = sha256((char) 0x00 + event);
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
        this.hash = sha256((char) 0x01 + new String(leftNode.getHash()) + new String(rightNode.getHash()));
    }

    private byte[] sha256(String data) throws Exception {
        try {
            MessageDigest digest= null;
            digest = MessageDigest.getInstance("SHA-256");
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
        return "["+ DatatypeConverter.printHexBinary(hash) +"]";
    }
}
