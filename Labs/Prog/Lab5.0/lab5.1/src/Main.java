import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Organization> organizations = new ArrayList<Organization>();
    public static void main(String[] args) {
        organizations.add(new Organization(88005553535L,"UFO SEARCH", new Coordinates(0, 2f), 23, OrganizationType.PRIVATE_LIMITED_COMPANY, new Address("Portovaya 31/12", "685000", new Location(23f, 323, 32, "Magadan"))));
        System.out.println(organizations);
    }
}