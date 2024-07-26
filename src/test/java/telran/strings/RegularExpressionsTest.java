package telran.strings;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static telran.strings.Strings.javaVariable;

public class RegularExpressionsTest {
    @Test
    void javaVariableTest() {
        String javaVariable = javaVariable();

        assertTrue("validValid".matches(javaVariable));
        assertTrue("valid999".matches(javaVariable));
        assertTrue("v1".matches(javaVariable));

        assertFalse("InvalidInvalid".matches(javaVariable));
        assertFalse("1invalid".matches(javaVariable));
        assertFalse("_nvalid".matches(javaVariable));
        assertFalse("$nvalid".matches(javaVariable));
        assertFalse("".matches(javaVariable));

    }

}
