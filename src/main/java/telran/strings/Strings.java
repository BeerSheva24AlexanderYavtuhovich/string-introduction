package telran.strings;

import java.util.Arrays;

public class Strings {
    private static final String keyWords[] = { "abstract", "assert", "boolean",
            "break", "byte", "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else", "enum", "extends", "false",
            "final", "finally", "float", "for", "goto", "if", "implements",
            "import", "instanceof", "int", "interface", "long", "native",
            "new", "null", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch",
            "synchronized", "this", "throw", "throws", "transient", "true",
            "try", "void", "volatile", "while" };

    public static String firstName() {
        return "[A-Z][a-z]{4,}";
    }

    public static String javaVariable() {
        return "(?!_$)[a-zA-Z$_][\\w$]*";
    }

    public static String number0_300() {
        return "[1-9]\\d?|[1-2]\\d\\d|300|0";
    }

    public static String ipV4Octet() {
        return "([0-1]?\\d{1,2}|2([0-4]\\d|5[0-5]))";
    }

    public static String ipV4Address() {
        String octetExpr = ipV4Octet();
        return String.format("%s(\\.%s){3}", octetExpr, octetExpr);
    }

    public static String stringWithJavaNames(String names) {
        String[] tokens = names.split("\\s+");
        int i = getJavaNameIndex(tokens, -1);
        String res = "";
        if (i >= 0) {
            res = tokens[i];
        }
        while ((i = getJavaNameIndex(tokens, i)) > 0) {
            res += " " + tokens[i];
        }
        return res;
    }

    private static int getJavaNameIndex(String[] tokens, int i) {
        i++;
        while (i < tokens.length && !isJavaName(tokens[i])) {
            i++;
        }
        return i < tokens.length ? i : -1;
    }

    private static boolean isJavaName(String string) {
        return string.matches(javaVariable()) && Arrays.binarySearch(keyWords, string) < 0;
    }

    public static boolean isArithmeticExpression(String expr) {
        Boolean res = false;
        if (expr.length() > 0 && isBracketsClosed(expr)) {
            expr = removeAllBrackets(expr);
            if (!expr.matches(".*" + isExtraSpaces() + ".*")) {
                expr = removeAllSpaces(expr);
                if (!expr.matches(".*" + isExtraOperands() + ".*")) {
                    res = splitAndCheckAllSubstrings(expr);
                }
            }
        }
        return res;
    }

    public static Boolean splitAndCheckAllSubstrings(String expr) {
        Boolean res = true;
        String[] substrings = splitStringByOperators(expr);
        int index = 0;
        if (isFirstAndLastValid(substrings)) {
            while (index < substrings.length && res) {
                if (isJavaReservedWord(substrings, index)) {
                    res = false;
                }
                if (!substrings[index].matches(isNumberOrOperatorOrOperand())) {
                    res = false;
                }
                index++;
            }
        } else {
            res = false;
        }
        return res;
    }

    private static boolean isJavaReservedWord(String[] substrings, int index) {
        return Arrays.binarySearch(keyWords, substrings[index]) > 0;
    }

    private static boolean isFirstAndLastValid(String[] substrings) {
        return !substrings[0].matches(isOperand()) && !substrings[substrings.length - 1].matches(isOperand());
    }

    private static String removeAllBrackets(String expr) {
        expr = expr.replaceAll("[()]", "");
        return expr;
    }

    private static String removeAllSpaces(String expr) {
        expr = expr.replaceAll("\\s+", "");
        return expr;
    }

    private static boolean isBracketsClosed(String expr) {
        int res = 0;
        int i = 0;
        boolean goOut = false;
        while (i < expr.length() && !goOut) {
            char ch = expr.charAt(i);
            if (ch == '(') {
                res++;
            } else if (ch == ')') {
                if (res == 0) {
                    goOut = true;
                    res = -1;
                }
                res--;
            }
            i++;
        }
        return res == 0;
    }

    private static String isOperand() {
        return "[+*/-]";
    }

    private static String isNumberOrOperatorOrOperand() {
        return "[a-zA-Z0-9]+|\\d*(\\.\\d+)|[+*/-]";
    }

    private static String isExtraSpaces() {
        return "\\s{2,}";
    }

    private static String isExtraOperands() {
        return "[+*/-]{2,}";
    }

    private static String[] splitStringByOperators(String expr) {
        return expr.split("(?=[+*/-])|(?<=[+*/-])");
    }

}
