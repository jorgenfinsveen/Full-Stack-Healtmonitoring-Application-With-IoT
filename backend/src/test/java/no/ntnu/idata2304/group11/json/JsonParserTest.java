package no.ntnu.idata2304.group11.json;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit-testing for the JsonHandler-tool.
 * Supposed to verify that the JsonHandler is
 * returning the correct values from the JSON-file.
 * 
 * @version 25-11-2022
 * @author  jorgenfinsveen
 * @since   25-11-2022
 */
public class JsonParserTest {
    
    /**
     * Test that the correct values are returned
     * for a male patient of age 43.
     */
    @Test
    public void testParser_male_43() {
        int[] val = JsonHandler.getParameters("male", 43);

        assertEquals(125, val[0]);
        assertEquals(83, val[1]);
        assertEquals(57, val[2]);
        assertEquals(82, val[3]);
    }


    /**
     * Test that the correct values are returned
     * for a female patient of age 62.
     */
    @Test
    public void testParser_female_62() {
        int[] val = JsonHandler.getParameters("female", 62);

        assertEquals(130, val[0]);
        assertEquals(86, val[1]);
        assertEquals(60, val[2]);
        assertEquals(83, val[3]);
    }


    /**
     * Test that the correct values are returned 
     * for both a female and a male patient of age
     * 98, which should correspond to the last JSONObject
     * in the JSON-file.
     */
    @Test
    public void testHandler_maleAndFemale_98() {
        int[] male   = JsonHandler.getParameters("male", 98);
        int[] female = JsonHandler.getParameters("female", 98);

        /* Expected results for male patient. */
        assertEquals(180, male[0]);
        assertEquals(115, male[1]);
        assertEquals(56,  male[2]);
        assertEquals(79,  male[3]);

        /* Expected results for female patient. */
        assertEquals(150, female[0]);
        assertEquals(100, female[1]);
        assertEquals(60,  female[2]);
        assertEquals(84,  female[3]);
    }
}
