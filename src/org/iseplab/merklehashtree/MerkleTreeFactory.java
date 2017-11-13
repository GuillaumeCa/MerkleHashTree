package org.iseplab.merklehashtree;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guillaume on 13/11/2017.
 * MerkleHashTree
 */
public class MerkleTreeFactory {

    /**
     * Create a MerkelTree
     * @param file
     * @return
     */
    public static MerkleTree createTree(String file) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(file))) {
            return computeTree(br);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static MerkleTree computeTree(BufferedReader reader) throws Exception {
        List<MerkleTree> leafTrees = new ArrayList<>();
        reader.lines().forEach(line -> {
            try {
                leafTrees.add(new MerkleTree(line));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        List<MerkleTree> trees = leafTrees;
        while (trees.size() != 1) {
            List<MerkleTree> tempTrees = new ArrayList<>();
            for (int i = 0; i < trees.size(); i++) {
                // currently omitting last value if number of events is not even
                if (i % 2 == 0 && i + 1 < trees.size()) {
                    tempTrees.add(new MerkleTree(trees.get(i), trees.get(i+1)));
                }
            }
            trees = tempTrees;
        }
        return trees.get(0);
    }

}
