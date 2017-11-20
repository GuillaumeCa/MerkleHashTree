package org.iseplab.merklehashtree;

/**
 * Created by Guillaume on 13/11/2017.
 * MerkleHashTree
 */
public class Main {

    public static void main(String[] args) {
        MerkleTreeFactory factory = MerkleTreeFactory.getFactory();
        MerkleTree tree = factory.createTree("traces/DS1-trace.txt");
        System.out.println(tree);
    }

}
