package managers;

import entities.MusicBand;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class CollectionManager {
    private Collection<MusicBand> collection = new HashSet<>();
    private Map<Long, MusicBand> musicBandsMap = new HashMap<>();
    private FileManager fileManager;

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void loadCollection(String filename){
        fileManager.loadCollectionFromFile(filename);
    }

    public Collection<MusicBand> getCollection() {
        return collection;
    }

    public void setCollection(Collection<MusicBand> collection){
        this.collection = collection;
    }
}
