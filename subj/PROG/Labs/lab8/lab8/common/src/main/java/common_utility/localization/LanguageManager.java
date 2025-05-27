package common_utility.localization;

import lombok.Getter;
import lombok.Setter;

import java.util.Locale;
import java.util.ResourceBundle;
@Getter
public class LanguageManager {
    private static Locale en = new Locale("en", "New Zealand");
    private static Locale ru = new Locale("ru", "Russia");
    private static Locale bg = new Locale("bg", "Bulgaria");
    private static Locale de = new Locale("de", "Germany");
    @Getter @Setter
    private static String currentLanguage = "Русский";
    @Getter
    private static ResourceBundle en_bundle = ResourceBundle.getBundle("strings", en);
    @Getter
    private static ResourceBundle ru_bundle = ResourceBundle.getBundle("strings", ru);
    @Getter
    private static ResourceBundle bg_bundle = ResourceBundle.getBundle("strings", bg);
    @Getter
    private static ResourceBundle de_bundle = ResourceBundle.getBundle("strings", de);
    @Getter @Setter
    private static ResourceBundle bundle = ResourceBundle.getBundle("strings", ru);
}
