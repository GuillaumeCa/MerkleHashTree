package org.iseplab.merklehashtree;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Guillaume on 13/11/2017.
 * MerkleHashTree
 */
public class MerkleTreeFactory {

    public static void createTree(String file) {
        try (BufferedReader br = Files.newBufferedReader(Paths.get(file))) {

            //br returns as stream and convert it into a List
            br.lines().forEach(line -> {

                MerkleTree merkleTree = new MerkleTree(line);

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
