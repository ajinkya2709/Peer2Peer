package edu.ufl.cise.p2p;

import edu.ufl.cise.p2p.message.Choke;
import edu.ufl.cise.p2p.message.Unchoke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
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
        ArrayList<RemotePeer> prefferedNeighbours;
        OptimisticallyUnchokedNeighbour optUnchokedScheduler;
        RemotePeer optUnchokedNeighbour;

        public PreferredNeighbours(ArrayList<RemotePeer> peers, CommonPeerProperties prop,
                                   Boolean hasFile, OptimisticallyUnchokedNeighbour optUnchokedScheduler){
            remotePeers = peers;
            commonProp = prop;
            this.hasFile.set(hasFile);
            this.optUnchokedScheduler = optUnchokedScheduler;
        }

        public void run(){
            interestedPeers = getInterestedRemotePeers();

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

            prefferedNeighbours.clear();
            prefferedNeighbours.addAll(interestedPeers.subList(0,
                    Math.min(commonProp.getNumberOfPreferredNeighbors(),interestedPeers.size())));

            LinkedList<RemotePeer> chokedPeers = new LinkedList<RemotePeer>(remotePeers);
            chokedPeers.removeAll(prefferedNeighbours);

            optUnchokedNeighbour = interestedPeers.get(prefferedNeighbours.size());

            optUnchokedScheduler.unchokeablePeersLock.lock();
            optUnchokedScheduler.updateOptUnchokeablePeers(optUnchokedNeighbour);
            optUnchokedScheduler.unchokeablePeersLock.unlock();

            for(RemotePeer chokedPeer: chokedPeers){

                try {
                    chokedPeer.getConnection().sendMessage(new Choke());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            for(RemotePeer preferredNeighbour: prefferedNeighbours){

                try {
                    preferredNeighbour.getConnection().sendMessage(new Unchoke());
                } catch (IOException e) {
                    e.printStackTrace();
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
    }

    class OptimisticallyUnchokedNeighbour implements Runnable{

        RemotePeer optUnchokeablePeer;
        CommonPeerProperties commonProp;
        public final ReentrantLock unchokeablePeersLock;

        public OptimisticallyUnchokedNeighbour(CommonPeerProperties prop){
            commonProp = prop;
            optUnchokeablePeer = null;
            unchokeablePeersLock = new ReentrantLock();
        }
        public void run(){
            unchokeablePeersLock.lock();
            if(optUnchokeablePeer != null) {
                try {
                    optUnchokeablePeer.getConnection().sendMessage(new Unchoke());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            unchokeablePeersLock.unlock();
        }

        
        public void updateOptUnchokeablePeers(RemotePeer unchokeablePeer){
            unchokeablePeersLock.lock();
            optUnchokeablePeer = unchokeablePeer;
            unchokeablePeersLock.unlock();
        }
    }

}
