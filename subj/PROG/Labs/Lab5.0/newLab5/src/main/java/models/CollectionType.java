package models;

public enum CollectionType {
    HashSet, LinkedList;
    public static String names() {
        StringBuilder s = new StringBuilder();
        s.append("Чтобы выбрать HashSet введите \"1\"").append("\n").append("Чтобы выбрать LinkedList введите \"2\"");
        return s.toString();
    }
}
