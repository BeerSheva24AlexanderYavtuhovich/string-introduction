package telran.strings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static telran.strings.Strings.isArithmeticExpression;
import static telran.strings.Strings.javaVariable;
import static telran.strings.Strings.stringWithJavaNames;

public class RegularExpressionsTest {
    @Test
    void javaVariableTest() {
        String javaVariable = javaVariable();

        assertTrue("validValid".matches(javaVariable));
        assertTrue("valid999".matches(javaVariable));
        assertTrue("v1".matches(javaVariable));
        assertTrue("ValidValid".matches(javaVariable));
        assertTrue("_valid".matches(javaVariable));
        assertTrue("$valid".matches(javaVariable));

        assertFalse("1invalid".matches(javaVariable));
        assertFalse("".matches(javaVariable));
        assertFalse("1_a".matches(javaVariable));
        assertFalse("_".matches(javaVariable));
        assertFalse("#1".matches(javaVariable));
        assertFalse("_*".matches(javaVariable));
        assertFalse("ab_cd_$12?".matches(javaVariable));
        assertFalse("ab_cd_$12 ".matches(javaVariable));
    }

    @Test
    void number0_300TrueTest() {
        String regex = Strings.number0_300();
        assertTrue("0".matches(regex));
        assertTrue("300".matches(regex));
        assertTrue("250".matches(regex));
        assertTrue("25".matches(regex));
        assertTrue("12".matches(regex));
        assertTrue("299".matches(regex));
        assertTrue("1".matches(regex));

    }

    @Test
    void number0_300FalseTest() {
        String regex = Strings.number0_300();
        assertFalse("00".matches(regex));
        assertFalse("301".matches(regex));
        assertFalse("01".matches(regex));
        assertFalse("1(".matches(regex));
        assertFalse("1000".matches(regex));
        assertFalse(" 20".matches(regex));
        assertFalse("1001".matches(regex));
    }

    @Test
    void ipv4OctetTrueTest() {
        String regex = Strings.ipV4Octet();
        assertTrue("0".matches(regex));
        assertTrue("00".matches(regex));
        assertTrue("000".matches(regex));
        assertTrue("249".matches(regex));
        assertTrue("255".matches(regex));
        assertTrue("199".matches(regex));
        assertTrue("100".matches(regex));
        assertTrue("10".matches(regex));
    }

    @Test
    void ipv4OctetFalseTest() {
        String regex = Strings.ipV4Octet();
        assertFalse("0000".matches(regex));
        assertFalse("t".matches(regex));
        assertFalse("-1".matches(regex));
        assertFalse("1111".matches(regex));
        assertFalse("0001".matches(regex));
        assertFalse("256".matches(regex));
        assertFalse("300".matches(regex));
        assertFalse("*".matches(regex));
        assertFalse("1 ".matches(regex));
    }

    @Test
    void ipV4AddressTrueTest() {
        String regex = Strings.ipV4Address();
        assertTrue("0.0.0.0".matches(regex));
        assertTrue("255.255.255.255".matches(regex));
    }

    @Test
    void ipV4AddressFalseTest() {
        String regex = Strings.ipV4Address();
        assertFalse("0.0.0".matches(regex));
        assertFalse("0.0.0+0".matches(regex));
        assertFalse("0".matches(regex));
        assertFalse("0.-".matches(regex));
        assertFalse("0.0.0*0".matches(regex));
        assertFalse("0.0.0 0".matches(regex));

    }

    @Test
    void stringWithJavaNamesTest() {
        String names = "123 1a _ abs int enum null lmn";
        String expected = "abs lmn";
        assertEquals(expected, stringWithJavaNames(names));

    }

    @Test
    public void testValidArithmeticExpressions() {
        assertTrue(isArithmeticExpression("5 + 3"));
        assertTrue(isArithmeticExpression("10 - 4 * 2"));
        assertTrue(isArithmeticExpression("(7 + 3) / 2"));
        assertTrue(isArithmeticExpression("2 * (3 + 4)"));
        assertTrue(isArithmeticExpression("10 / 3"));
        assertTrue(isArithmeticExpression("a / b"));
        assertTrue(isArithmeticExpression("c * (a + b)"));
        assertTrue(isArithmeticExpression("abc + 5"));
        assertTrue(isArithmeticExpression("1 + 1 - 1 * 1 / 1"));
        assertTrue(isArithmeticExpression("3.14 * 2"));
        assertTrue(isArithmeticExpression("5.5 + (4.5 - 2)"));
        assertTrue(isArithmeticExpression("10 * (2 + 5) / 3"));
        assertTrue(isArithmeticExpression("8 - 3 * (2 + 1) / 3"));
        assertTrue(isArithmeticExpression("7 * 8 / (4 - 2)"));
        assertTrue(isArithmeticExpression("2 + (3 + 4) * 5"));
        assertTrue(isArithmeticExpression("9 / (3 - 1) + 2"));
        assertTrue(isArithmeticExpression("0 * 5 + 8 / 4"));
        assertTrue(isArithmeticExpression("5.5 / 1.1"));
        assertTrue(isArithmeticExpression("100 / (10 - 5) * 2 + 3"));
        assertTrue(isArithmeticExpression("5 + (10 / 2) * (3 - 1)"));
        assertTrue(isArithmeticExpression("2 * (3 + 4) - 1"));
        assertTrue(isArithmeticExpression("10 + (5 - 2) * 3 / 5"));
        assertTrue(isArithmeticExpression("6 + 2 * (3 + 1) - 4 / 2"));
    }
    
    @Test
    public void testInvalidArithmeticExpressions() {

        assertFalse(isArithmeticExpression("a f+ b"));
        assertFalse(isArithmeticExpression("5 +   * (2 +)   "));
        assertFalse(isArithmeticExpression("())())"));
        assertFalse(isArithmeticExpression("int + char"));
        assertFalse(isArithmeticExpression("5 + double"));
        assertFalse(isArithmeticExpression(""));
        assertFalse(isArithmeticExpression("5 ++ 3"));
        assertFalse(isArithmeticExpression("10 / / 2"));
        assertFalse(isArithmeticExpression("7 + 3)"));
        assertFalse(isArithmeticExpression("(2 * (3 + 4)"));
        assertFalse(isArithmeticExpression("10 % 3.5"));
        assertFalse(isArithmeticExpression("5 + 3 *"));
        assertFalse(isArithmeticExpression("10 / (5 + "));
        assertFalse(isArithmeticExpression("5 * (3 + 2))"));
        assertFalse(isArithmeticExpression(" (2 + 5 / "));
        assertFalse(isArithmeticExpression("2 * (3 + 4 /"));
        assertFalse(isArithmeticExpression("5 + (3 / (2 - 1"));
        assertFalse(isArithmeticExpression("10 / (2 * (3 + 4)))"));
        assertFalse(isArithmeticExpression("5 + (3 * (2 + 4)) / "));
        assertFalse(isArithmeticExpression("5 + 2 * (3 + 4) / )"));
        assertFalse(isArithmeticExpression("5 + (3 * 2"));
        assertFalse(isArithmeticExpression("* 5 + 10"));
        assertFalse(isArithmeticExpression("5 + 3 * (2 +)"));
    }

}
