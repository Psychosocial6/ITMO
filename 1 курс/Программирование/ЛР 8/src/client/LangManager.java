package client;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class LangManager {
    private static ResourceBundle bundle;
    private static Map<String, String> language = new HashMap<>(){
    	{
    		put("русский", "ru");
    		put("english", "en");
    		put("český", "cs");
    		put("українська", "uk");
    		put("español", "es-HN");
    	}
    };

    public static void setLanguage(String lang) {
    	String langCode = language.get(lang);
    	Locale locale = Locale.forLanguageTag(langCode);
        bundle = ResourceBundle.getBundle("lang.messages", locale);
    }

    public static String get(String key) {
        if (bundle == null) {
            setLanguage("en");
        }
        return bundle.getString(key);
    }

    public static Locale getCurrentLocale() {
        if (bundle == null) {
            setLanguage("en");
        }
        return bundle.getLocale();
    }
}
