package org.iseplab.log;

import org.iseplab.merklehashtree.MerkleTree;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface LogServerInterface extends Remote {

    public void add(String log) throws RemoteException;;

    public void add(ArrayList<String> logs) throws RemoteException;;

    public ArrayList<MerkleTree> genPath(int n) throws RemoteException;;

    public ArrayList<MerkleTree> genProof(int n) throws RemoteException;;

}
