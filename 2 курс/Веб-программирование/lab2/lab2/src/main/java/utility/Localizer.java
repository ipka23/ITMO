package utility;

import lombok.Getter;
import lombok.Setter;

import java.util.Locale;
import java.util.ResourceBundle;


public class Localizer {
    private static Locale en = new Locale("en");
    private static Locale ru = new Locale("ru");
    @Setter
    private static ResourceBundle ru_bundle = ResourceBundle.getBundle("messages", ru);
    @Setter
    private static ResourceBundle en_bundle = ResourceBundle.getBundle("messages", en);
    @Setter @Getter
    private static ResourceBundle bundle = ResourceBundle.getBundle("messages", ru);


}

