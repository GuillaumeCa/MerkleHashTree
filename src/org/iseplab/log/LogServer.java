package org.iseplab.log;

import org.iseplab.merklehashtree.MerkleTree;
import org.iseplab.merklehashtree.MerkleTreeFactory;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class LogServer implements LogServerInterface {

    private MerkleTree tree;

    public MerkleTree getTree() {
        return tree;
    }

    public LogServer(String filePath) {
        MerkleTreeFactory factory = MerkleTreeFactory.getFactory();
        this.tree = factory.createTree(filePath);
    }

    public String getRootHash(){
        return this.tree.toString();
    }


    public void add(String log) throws RemoteException {
        this.tree = this.addSingleLog(log, this.tree, this.tree.getNbLeaves()+1);
    }

    public void add(ArrayList<String> logs) throws RemoteException {
        this.tree = this.addMultipleLogs(logs, this.tree, this.tree.getNbLeaves()+1);
    }


    private MerkleTree addSingleLog(String log, MerkleTree currentTree, int newIndex) {
        try{

            // if 0 elems in the tree
            if(currentTree == null) {
                return new MerkleTree(log, 0);
            }

            // if currentTree is the left Leaf OR if the number of Leaf is odd
            else if(currentTree.getNbLeaves() == currentTree.getMaxNbLeaves()) {
                MerkleTree newNode = new MerkleTree(log, newIndex);
                return new MerkleTree(currentTree, newNode);
            }

            // else : go to the right subtree
            else {
                return new MerkleTree(currentTree.getLeftNode(), this.addSingleLog(log, currentTree.getRightNode(), newIndex));
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return currentTree;

    }

    private MerkleTree addMultipleLogs(ArrayList<String> logs, MerkleTree currentTree, int newIndex) {

        try{
            if(logs.isEmpty()) return currentTree;

            else if(currentTree == null){
                currentTree = new MerkleTree(logs.get(0), 0);
                logs.remove(0);
                return this.addMultipleLogs(logs, currentTree, newIndex+1);
            }

            else {

                // Nombre de feuilles pouvant etre ajoutées
                int freeNodePlaces = currentTree.getMaxNbLeaves() - currentTree.getNbLeaves();
                MerkleTree tmpResultTree;

                if(freeNodePlaces == 0) {
                    tmpResultTree = new MerkleTree(currentTree, new MerkleTree(logs.get(0), newIndex));
                    logs.remove(0);
                    return this.addMultipleLogs(logs, tmpResultTree, newIndex + 1);
                }

                /* Si le nombre de nouveaux logs depasse le nombre X de places dispo avant de remplir l'arbre,
                 * on ajoute les X premiers logs pour remplir les places disponibles, puis on ajoute les logs
                 * restants à l'arbe complet qui vient d'etre construit
                 */

                else if (logs.size() > freeNodePlaces) {
                    ArrayList<String> elems = logs;
                    ArrayList<String> fullTreeElements = new ArrayList<>();

                    for (int i = 0; i < freeNodePlaces; i++) {
                        fullTreeElements.add(elems.get(0));
                        elems.remove(0);
                    }

                    tmpResultTree = new MerkleTree(currentTree.getLeftNode(), this.addMultipleLogs(fullTreeElements, currentTree.getRightNode(), newIndex));
                    tmpResultTree = new MerkleTree(tmpResultTree, new MerkleTree(elems.get(0), newIndex + freeNodePlaces));
                    elems.remove(0);
                    return new MerkleTree(tmpResultTree.getLeftNode(), addMultipleLogs(elems, tmpResultTree.getRightNode(), newIndex + freeNodePlaces + 1));
                }

                else {
                    if (logs.size() == 1) {
                        String log = logs.get(0);
                        logs.remove(0);
                        return addSingleLog(log, currentTree, newIndex);
                    }
                    else return new MerkleTree(currentTree.getLeftNode(), this.addMultipleLogs(logs, currentTree.getRightNode(), newIndex));
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return currentTree;
    }

    public ArrayList<MerkleTree> genPath(int n) throws RemoteException {
        return genPath(this.tree, new ArrayList<>(), n);
    }

    private ArrayList<MerkleTree> genPath(MerkleTree currentTree, ArrayList<MerkleTree> nodeList, int n) {

        if(currentTree.getBeginIndex() == currentTree.getEndIndex() && currentTree.getEndIndex() == n){
            return nodeList;
        }

        else if(currentTree.getLeftNode() != null && currentTree.getLeftNode().getEndIndex() >= n) {
            nodeList.add(0, currentTree.getRightNode());
            return genPath(currentTree.getLeftNode(), nodeList, n);
        }

        else if(currentTree.getRightNode() != null && currentTree.getRightNode().getEndIndex() >= n) {
            nodeList.add(0, currentTree.getLeftNode());
            return genPath(currentTree.getRightNode(), nodeList, n);
        }

        else {
            System.out.println("Error  : Tree size < " + n);
            return nodeList;
        }
    }

    public ArrayList<MerkleTree> genProof(int n) throws RemoteException {
        return genProof(this.tree, new ArrayList<>(), n);
    }

    private ArrayList<MerkleTree> genProof(MerkleTree currentTree, ArrayList<MerkleTree> nodeList, int n) {

        if(currentTree.getEndIndex() < n || currentTree.getBeginIndex() > n) {
            System.out.println("Error  : Tree size < " + n);
            return nodeList;
        }

        else {
            if(currentTree.getEndIndex() == n){
                nodeList.add(0, currentTree);
                return nodeList;
            }

            else if(currentTree.getLeftNode() != null && currentTree.getLeftNode().getEndIndex() < n) {
                nodeList.add(0, currentTree.getLeftNode());
                return genProof(currentTree.getRightNode(), nodeList, n);
            }

            else if(currentTree.getLeftNode() != null && currentTree.getLeftNode().getEndIndex() >= n) {
                nodeList.add(0, currentTree.getRightNode());
                nodeList = genProof(currentTree.getLeftNode(), nodeList, n);
                return nodeList;
            }
        }

        return nodeList;
    }

}
