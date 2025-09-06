package common_entities;

import java.io.Serializable;

/**
 * Данный класс enum представляет доступные жанры музыки
 *
 * @author ipka23
 */
public enum MusicGenre implements Serializable {
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
        for (int i = 0 ; i < MusicGenre.values().length ; i++) {
            nameList.append(i).append(" - ").append(MusicGenre.values()[i]).append(";").append("\n");
        }
        nameList = new StringBuilder(nameList.substring(0, nameList.length() - 2));
        nameList.append(".");
        return nameList.toString();
    }

}