package telran.strings;

public class Strings {
    public static String firstName() {
        return "[A-Z][a-z]{4,}";
    }

    public static String javaVariable() {
        return "[a-zA-Z$]|[a-zA-Z$_][\\w\\d$]+";
    }
}
