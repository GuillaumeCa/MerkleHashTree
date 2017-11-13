package org.iseplab.merklehashtree;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Guillaume on 13/11/2017.
 * MerkleHashTree
 */
public class MerkleTreeFactory {

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

        int treesSize = leafTrees.size();
        List<MerkleTree> trees = leafTrees;
        while (treesSize != 1) {
            List<MerkleTree> newTrees = new ArrayList<>();
            for (int i = 0; i < treesSize; i++) {
                if (i % 2 == 0 && i + 1 < treesSize) {
                    newTrees.add(new MerkleTree(trees.get(i), trees.get(i+1)));
                }
            }
            treesSize = newTrees.size();
            trees = newTrees;
        }
        return trees.get(0);
    }

}
