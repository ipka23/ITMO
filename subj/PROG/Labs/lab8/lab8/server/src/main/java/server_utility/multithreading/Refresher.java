package server_utility.multithreading;

import common_entities.MusicBand;
import common_utility.network.Response;
import server_utility.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashSet;

public class Refresher {
    public static void addRefresh(Collection<MusicBand> collection, MusicBand band) {
        for (ObjectOutputStream out : new HashSet<>(Server.outputStreams)) {
            try {
                Collection<MusicBand> copy = new HashSet<>(collection);
                synchronized (out) {
                    out.reset();
                    out.writeObject(new Response(false, "add_refresh", copy, band));
                    System.out.println("Response collection  (add): " + collection.size());
                    out.flush();
                }
            } catch (IOException e) {
                Server.outputStreams.remove(out);
            }
        }
    }
    public static void refresh(Collection<MusicBand> collection) {
        for (ObjectOutputStream out : new HashSet<>(Server.outputStreams)) {
            try {
                Collection<MusicBand> copy = new HashSet<>(collection);
                synchronized (out) {
                    out.reset();
                    out.writeObject(new Response(false, "refresh", copy));
                    System.out.println("Response collection  (): " + collection.size());
                    out.flush();
                }
            } catch (IOException e) {
                Server.outputStreams.remove(out);
            }
        }
    }


    public static void deleteRefresh(Collection<MusicBand> collection) {
        for (ObjectOutputStream out : new HashSet<>(Server.outputStreams)) {
            try {
                Collection<MusicBand> copy = new HashSet<>(collection);
                synchronized (out) {
                    out.reset();
                    out.writeObject(new Response(false, "delete_refresh", copy));
                    System.out.println("Response collection (del): " +collection.size());
                    out.flush();
                }
            } catch (IOException e) {
                Server.outputStreams.remove(out);
            }
        }
    }
    public static void updateRefresh(MusicBand oldBand, MusicBand newBand, Collection<MusicBand> collection) {
        for (ObjectOutputStream out : new HashSet<>(Server.outputStreams)) {
            try {
                Collection<MusicBand> copy = new HashSet<>(collection);
                synchronized (out) {
                    out.reset();
                    out.writeObject(new Response(false, "update_refresh", oldBand, newBand, copy));
                    System.out.println("Response collection  (updt): " + collection.size());
                    out.flush();
                }
            } catch (IOException e) {
                Server.outputStreams.remove(out);
            }
        }
    }
}
