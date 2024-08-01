package telran.strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.SourceVersion;

public class Strings {

    static Pattern pattern;
    static {
        pattern = Pattern.compile(getArithmeticRegex());
    }

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
        return string.matches(javaVariable()) && !SourceVersion.isKeyword(string);
    }

    private static boolean isJavaKeyword(String string) {
        return SourceVersion.isKeyword(string);
    }

    public static boolean isArithmeticExpression(String expr) {
        Matcher matcher = pattern.matcher(expr);
        boolean exprMatch = matcher.matches();
        boolean pairness = pairnessCheck(expr);
        boolean javaNames = javaNamesCheck(expr);
        return exprMatch && pairness && javaNames;
    }

    private static boolean javaNamesCheck(String expr) {
        String[] operands = expr.split(getRegexForOperandsSep());
        int index = 0;
        while (index < operands.length && !isJavaKeyword(operands[index])) {
            index++;
        }
        return index == operands.length;
    }

    private static String getRegexForOperandsSep() {
        return String.format("%s|[\\s()]+", getOperatorRegex());
    }

    private static String getOperatorRegex() {
        return "[+/*-]";
    }

    private static boolean pairnessCheck(String expr) {
        char[] exprChars = expr.toCharArray();
        int index = 0;
        int counter = 0;
        while (index < exprChars.length && counter >= 0) {
            if (exprChars[index] == '(') {
                counter++;
            } else if (exprChars[index] == ')') {
                counter--;
            }
            index++;
        }
        return counter == 0;
    }

    private static String getArithmeticRegex() {
        String operator = getOperatorRegex();
        String operand = getOperandRegex();
        String regex = String.format("%s(%s%s)*", operand, operator, operand);
        return regex;
    }

    private static String getOperandRegex() {
        String number = getNumberRegex();
        String regex = String.format("[\\s(]*(%s|%s)[\\s)]*", number, javaVariable());
        return regex;
    }

    private static String getNumberRegex() {
        return "\\d+\\.?\\d*|\\.\\d+";
    }

}
