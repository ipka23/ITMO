package models;

public enum MusicGenre {
    PROGRESSIVE_ROCK,
    HIP_HOP,
    POP;

    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var musicGenre : values()) {
            nameList.append(musicGenre.name()).append(", ");
        }
        return nameList.substring(0, nameList.length()-2);
    }
}