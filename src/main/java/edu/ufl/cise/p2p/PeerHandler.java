package edu.ufl.cise.p2p;

import edu.ufl.cise.p2p.message.Choke;
import edu.ufl.cise.p2p.message.Unchoke;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class PeerHandler{

    PreferredNeighbours preferredNeighboursTask;
    OptimisticallyUnchokedNeighbour optimisticallyUnchokedTask;
    CommonPeerProperties commonProp;

    public PeerHandler(ArrayList<RemotePeer> peers, CommonPeerProperties prop, Boolean hasFile){

        optimisticallyUnchokedTask = new OptimisticallyUnchokedNeighbour(prop);
        preferredNeighboursTask = new PreferredNeighbours(peers, prop, hasFile, optimisticallyUnchokedTask);
        commonProp = prop;

    }

    /*Method to schedule periodic tasks for updating the preferred neighbours
     and optimistically unchoked neighbour*/

    public void sendChokeAndUnchokeMessages(){
        final ScheduledExecutorService preferredNeighbourSched = Executors.newScheduledThreadPool(1);
        final ScheduledExecutorService optUnchokedSched = Executors.newScheduledThreadPool(1);

        preferredNeighbourSched.scheduleAtFixedRate(preferredNeighboursTask,0,
                commonProp.getUnchokingInterval(), TimeUnit.SECONDS);

        optUnchokedSched.scheduleAtFixedRate(optimisticallyUnchokedTask,0,
                commonProp.getOptimisticUnchokingInterval(), TimeUnit.SECONDS);
    }

    class PreferredNeighbours implements Runnable{

        ArrayList<RemotePeer> remotePeers;
        CommonPeerProperties commonProp;
        AtomicBoolean hasFile = new AtomicBoolean(false);
        ArrayList<RemotePeer> interestedPeers;
        ArrayList<RemotePeer> preferredNeighbours;
        OptimisticallyUnchokedNeighbour optUnchokedScheduler;
        ArrayList<RemotePeer> optUnchokedNeighbours;

        public PreferredNeighbours(ArrayList<RemotePeer> peers, CommonPeerProperties prop,
                                   Boolean hasFile, OptimisticallyUnchokedNeighbour optUnchokedScheduler){
            remotePeers = peers;
            commonProp = prop;
            this.hasFile.set(hasFile);
            this.optUnchokedScheduler = optUnchokedScheduler;
            preferredNeighbours = new ArrayList<RemotePeer>();
            optUnchokedNeighbours = new ArrayList<RemotePeer>();
        }

        public void run(){
            interestedPeers = getInterestedRemotePeers();

            if(interestedPeers.size() != 0){
                if(hasFile.get()){
                    Collections.shuffle(interestedPeers);
                }
                else{
                    Collections.sort(interestedPeers, new Comparator<RemotePeer>() {
                        public int compare(RemotePeer r1, RemotePeer r2) {
                            // Sort in decreasing order
                            return (r2.getBytesDownloaded().get() - r1.getBytesDownloaded().get());
                        }
                    });
                }

                for(RemotePeer i : remotePeers)
                    i.getBytesDownloaded().set(0);

                clearPreviousPreferredNeighbours(); //clearing previous preferredNeighbours from the list
                preferredNeighbours.addAll(interestedPeers.subList(0,
                        Math.min(commonProp.getNumberOfPreferredNeighbors(),interestedPeers.size())));  //updating preferredNeighbours

                LinkedList<RemotePeer> chokedPeers = new LinkedList<RemotePeer>(remotePeers);
                chokedPeers.removeAll(preferredNeighbours);

                optUnchokedNeighbours.clear();
                optUnchokedNeighbours.addAll(interestedPeers.subList(preferredNeighbours.size(),interestedPeers.size()));

                optUnchokedScheduler.updateOptUnchokeablePeers(optUnchokedNeighbours);

                for(RemotePeer chokedPeer: chokedPeers){

                    try {
                        if(!chokedPeer.getIsChoked().get()){
                            chokedPeer.getConnection().sendMessage(new Choke());
                            chokedPeer.getIsChoked().set(true);
                            chokedPeer.getIsUnchoked().set(false);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                for(RemotePeer preferredNeighbour: preferredNeighbours){

                    try {
                        if(!preferredNeighbour.getIsUnchoked().get()){
                            preferredNeighbour.getConnection().sendMessage(new Unchoke());
                            preferredNeighbour.getIsUnchoked().set(true);
                            preferredNeighbour.getIsChoked().set(false);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

        }

        private ArrayList<RemotePeer> getInterestedRemotePeers(){
            ArrayList<RemotePeer> temp = new ArrayList<RemotePeer>();
            for(int i = 0; i < remotePeers.size(); i++){
                if(remotePeers.get(i).getIsInterested().get()){
                    temp.add(remotePeers.get(i));
                }
            }
            return temp;
        }

        private void clearPreviousPreferredNeighbours(){
            for(RemotePeer i : preferredNeighbours)
                i.getIsPreferredNeighbor().set(false);

            preferredNeighbours.clear();
        }
    }

    class OptimisticallyUnchokedNeighbour implements Runnable{

        ArrayList<RemotePeer> optUnchokeablePeers;
        CommonPeerProperties commonProp;
        RemotePeer optimisticallyUnchokedNeighbour;
        public final ReentrantLock unchokeablePeersLock;

        public OptimisticallyUnchokedNeighbour(CommonPeerProperties prop){
            commonProp = prop;
            unchokeablePeersLock = new ReentrantLock();
        }
        public void run(){
            unchokeablePeersLock.lock();

            try{
                if(optUnchokeablePeers.size() != 0){
                    Random rand = new Random();
                    if(optimisticallyUnchokedNeighbour != null)
                        optimisticallyUnchokedNeighbour.getIsOptimisticallyUnchoked().set(false);


                    //Randomly selecting optimisticUnchokedNeighbour
                    optimisticallyUnchokedNeighbour = optUnchokeablePeers.get(rand.nextInt(optUnchokeablePeers.size()));


                    if(!optimisticallyUnchokedNeighbour.getIsUnchoked().get()){


                        optimisticallyUnchokedNeighbour.getConnection().sendMessage(new Unchoke());

                        optimisticallyUnchokedNeighbour.getIsOptimisticallyUnchoked().set(true);
                        optimisticallyUnchokedNeighbour.getIsUnchoked().set(true);
                        optimisticallyUnchokedNeighbour.getIsChoked().set(false);
                    }

                }
            } catch (IOException e){
                e.printStackTrace();
            }
            finally {
                unchokeablePeersLock.unlock();
            }
        }

        
        public void updateOptUnchokeablePeers(ArrayList<RemotePeer> unchokeablePeers){
            unchokeablePeersLock.lock();
            optUnchokeablePeers = unchokeablePeers;
            unchokeablePeersLock.unlock();
        }
    }

}
