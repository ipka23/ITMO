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

//    @Override
//    public String toString() {
//        switch (this) {
//            case COMMERCIAL:
//                return "Commercial";
//            case PUBLIC:
//                return "Public";
//            case TRUST:
//                return "Trust";
//            case PRIVATE_LIMITED_COMPANY:
//                return "Private Limited Company";
//            case OPEN_JOINT_STOCK_COMPANY:
//                return "Open Join Stock Company";
//            default:
//                return "";
//        }
//    }
}