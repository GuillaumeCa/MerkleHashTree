package org.iseplab.merklehashtree;

/**
 * Created by Guillaume on 13/11/2017.
 * MerkleHashTree
 */
public class Main {

    public static void main(String[] args) {
        MerkleTree tree = MerkleTreeFactory.createTree("traces/DS2-trace.txt");
        System.out.println(tree);
    }

}
