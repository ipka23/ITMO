package server_utility;

public enum CollectionType {
    HashSet, LinkedList;
    public static String choosingTypePrompt() {
        StringBuilder message = new StringBuilder();
        message.append("Выберите тип коллекции:").append("\n").append("HashSet - \"1\"").append("\n").append("LinkedList - \"2\"\n% ");
        return message.toString();
    }
}