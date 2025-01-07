package models;

public enum OrganizationType {
    COMMERCIAL,
    PUBLIC,
    TRUST,
    PRIVATE_LIMITED_COMPANY,
    OPEN_JOINT_STOCK_COMPANY;

    public static void printAvailableTypes() {
        for (OrganizationType organizationType : values()) {
            System.out.println(organizationType);
        }
    }
}