package models;

public enum OrganizationType {
    COMMERCIAL,
    PUBLIC,
    TRUST,
    PRIVATE_LIMITED_COMPANY,
    OPEN_JOINT_STOCK_COMPANY;

    public static String printNames() {
        StringBuilder nameList = new StringBuilder();
        for (OrganizationType type : OrganizationType.values()) {
            nameList.append(type.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}