package server_utility.multithreading;

import common_entities.MusicBand;
import common_utility.network.Response;
import server_utility.Server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Collection;

public class Refresher {
    public static void refresh(Collection<MusicBand> collection) {
        for (ObjectOutputStream out : Server.outputStreams) {
            try {
                synchronized (out) {
                    out.reset(); // из-за этой строчки я потерял почти все нервные клетки
                    out.writeObject(new Response(false, "refresh", collection));
                    out.flush();
                }
            } catch (IOException e) {
                Server.outputStreams.remove(out);
            }
        }
    }

    public static void deleteRefresh(Collection<MusicBand> collection) {
        for (ObjectOutputStream out : Server.outputStreams) {
            try {
                synchronized (out) {
                    out.reset();
                    out.writeObject(new Response(false, "delete_refresh", collection));
                    System.out.println("size: " + collection.size());
                    out.flush();
                }
            } catch (IOException e) {
                Server.outputStreams.remove(out);
            }
        }
    }
}
