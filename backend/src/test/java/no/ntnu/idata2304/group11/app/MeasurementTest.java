package no.ntnu.idata2304.group11.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;

import org.junit.Test;

/**
 * JUnit-testing for the Measurement class.
 * 
 * @version 25-11-2022
 * @since   25-11-2022
 * @author  jorgenfinsveen
 */
public class MeasurementTest {
    

    /**
     * Create a measurement object which can be used to
     * run tests on.
     *
     * @return a measurement object.
     */
    public Measurement createMeasurement() {
        return new Measurement(
            "2020-11-10 15:12:16",
            "130", "80", "75",
            "ABCD-1234-EFGH");
    }

    /**
     * Confirm that a measurement object can be
     * created successfully.
     */
    @Test
    public void testCreationOfMeasurement() {
        Measurement measurement = createMeasurement();
        assertNotNull(measurement);
    }

    /**
     * Test that the mutator-method in the Measurement-class
     * successfully sets the PID variable to the correct value
     * when a valid parameter is given.
     */
    @Test
    public void testMutatorMethods_validParameter() {
        Measurement measurement = createMeasurement();
        measurement.setPid("1003");
        assertEquals("1003", measurement.getPid());
    }

    /**
     * Test that the mutator-method in the Measurement-class
     * does not set the PID variable to any value
     * when an invalid parameter is given.
     */
    @Test
    public void testMutatorMethods_invalidParameter() {
        Measurement measurement = createMeasurement();
        measurement.setPid("");
        assertNull(measurement.getPid());
    }

    /**
     * Assert that all accessor-methods returns the
     * expected values.
     */
    @Test
    public void testAllAccessorMethods() {
        Measurement measurement = createMeasurement();
        measurement.setPid("1003");

        /*
         * Needs to convert the date excpected into an
         * instance of the LocalDateTime-class, to succesfully
         * compare excpected and actual value.
         */
        String[] dateArr = "2020-11-10 15:12:16".split(" ");
        String[] ymd = dateArr[0].split("-");
        String[] hms = dateArr[1].split(":");
        LocalDateTime date = LocalDateTime.of(
            Integer.parseInt(ymd[0]), Integer.parseInt(ymd[1]), 
            Integer.parseInt(ymd[2]), Integer.parseInt(hms[0]), 
            Integer.parseInt(hms[1]), 0);

        assertEquals(date, measurement.getDate());
        assertEquals(130, measurement.getSys());
        assertEquals(80, measurement.getDia());
        assertEquals(75, measurement.getPulse());
        assertEquals("ABCD-1234-EFGH", measurement.getSensor());
    }
}
