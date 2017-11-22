package org.iseplab.merklehashtree;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Guillaume on 13/11/2017.
 * MerkleHashTree
 */
public class MerkleTreeFactory {

    private static MerkleTreeFactory factory = new MerkleTreeFactory();

    /**
     * Create a MerkelTree
     * @param file
     * @return
     */
    public MerkleTree createTree(String file) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(file))) {
            List<MerkleTree> leafTrees = new ArrayList<>();
            List<String> lines = br.lines().collect(Collectors.toList());
            for (int i = 0; i < lines.size(); i++) {
                leafTrees.add(new MerkleTree(lines.get(i), i + 1));
            }
            // odd leaf number
            if (leafTrees.size() % 2 != 0) {
                leafTrees.add(leafTrees.get(leafTrees.size() - 1));
            }
            return computeTree(leafTrees);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private MerkleTree computeTree(List<MerkleTree> leafTrees) throws Exception {
        List<MerkleTree> trees = leafTrees;
        while (trees.size() != 1) {
            List<MerkleTree> tempTrees = new ArrayList<>();
            for (int i = 0; i < trees.size(); i += 2) {
                if (i + 1 >= trees.size()) {
                    tempTrees.add(trees.get(i));
                } else {
                    tempTrees.add(new MerkleTree(trees.get(i), trees.get(i + 1)));
                }
            }
            trees = tempTrees;
        }
        return trees.get(0);
    }

    public static MerkleTreeFactory getFactory() {
        return factory;
    }
}
