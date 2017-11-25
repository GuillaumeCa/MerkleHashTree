package org.iseplab.log;

import org.iseplab.merklehashtree.MerkleTree;
import org.iseplab.merklehashtree.MerkleTreeFactory;

public class LogServer {

    private MerkleTree tree;

    public LogServer(String filePath) {
        MerkleTreeFactory factory = MerkleTreeFactory.getFactory();
        this.tree = factory.createTree(filePath);
    }

    public byte[] getRootHash(){
        return this.tree.getHash();
    }

    public void genPath(int n) {


    }

}
