package models;

public enum CollectionType {
    HashSet, LinkedList;
    public static String choosingTypePrompt() {
        StringBuilder s = new StringBuilder();
        s.append("Выберите тип коллекции:").append("\n").append("HashSet - \"1\"").append("\n").append("LinkedList - \"2\"");
        return s.toString();
    }
}
