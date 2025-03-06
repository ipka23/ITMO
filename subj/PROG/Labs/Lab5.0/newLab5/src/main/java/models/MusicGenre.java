package models;

/**
 * Данный класс enum представляет доступные жанры музыки
 *
 * @author ipka23
 */
public enum MusicGenre {
    PROGRESSIVE_ROCK,
    HIP_HOP,
    POP;

    /**
     * Метод для получения списка всех жанров музыки в виде строки, разделенной запятыми.
     *
     * @return строка, содержащая все жанры музыки
     */
    public static String names() {
        StringBuilder nameList = new StringBuilder();
        for (var musicGenre : values()) {
            nameList.append(musicGenre.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}