package telran.strings;

public class Strings {
    public static String firstName() {
        // regex for strings starting with capital letter and rest as lowercase letters
        // min length is 5 letters
        return "[A-Z][a-z]{4,}";
    }

    public static String javaVariable() {
        // TODO
        // regular expression for testing syntax Java variable names
        return "";
    }
}
