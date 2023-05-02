package no.ntnu.idata2304.group11.database;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import org.junit.Test;

/**
 * JUnit-testing for the DataBaseClient.
 * 
 * The JUnit-testing of such program proves to
 * be inefficient as the a complete test of all
 * functionality would require us to access the database
 * to access specific values, which would require own
 * methods specifically for testing in the DataBaseClient
 * program.
 * 
 * It is considered more efficient to do DML on the database
 * and then access the database directly through phpMyAdmin,
 * so the main portion of the testing is done in this manner,
 * thus leading to fewer test-methods in this class.
 * 
 * @version 25-11-2022
 * @since   24-11-2022
 * @author  jorgenfinsveen
 */
public class DataBaseClientTest {
    
    /**
     * Assert that a correct patient status is retrieved from a Patient-
     * entity in the database.
     */
    @Test
    public void getStatusTest() throws SQLException {
        /* Makes sure that the patient status is stable before assertion. */
        DataBaseClient.setStatus("1001", "stable");

        assertEquals("stable", DataBaseClient.getStatus(1001));
    }

    /**
     * Check if a request for a patients PID is correctly retrieved when
     * querying with the serial number of the sensor which is assigned
     * to the patient.
     */
    @Test
    public void getPIDTest() throws SQLException {
        assertEquals("1003", DataBaseClient.getPID("ABCD-EFGH-AC81"));
    }

    /**
     * Test that the manipulation of the Status-attribute of the Patient-
     * table is done correctly.
     */
    @Test
    public void testStatus() throws SQLException {
        /* 
         * Sets the status to stable and confirms that the getStatus 
         * method retrives the same value.
        */
        DataBaseClient.setStatus("1003", "stable");
        assertEquals("stable", DataBaseClient.getStatus(1003));

        /*
         * Changes the status to critical and confirms that the
         * status is altered in the database as well.
         */
        DataBaseClient.setStatus("1003", "critical");
        assertEquals("critical", DataBaseClient.getStatus(1003));
    }
}
