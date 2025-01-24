import models.Organization;
import utility.StandardConsole;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Organization> organizations = new ArrayList<Organization>();
    public static void main(String[] args) throws Ask.AskBreak {
        var console = new StandardConsole();
        organizations.add(Ask.askOrganization(console, 1L));
        for (var e: organizations) {
            System.out.println(e);
        }
    }
}