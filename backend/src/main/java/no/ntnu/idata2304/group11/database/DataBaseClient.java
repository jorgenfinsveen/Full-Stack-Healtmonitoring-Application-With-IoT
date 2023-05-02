package no.ntnu.idata2304.group11.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * With this class, you can connect to the db server.
 * The database which is connected to is finsveen_dev.
 * Makes it possible for the program to do queries and
 * manipulate the data stored in the database.
 * 
 * @author  Ole Kristian Dvergsdal 
 * @author  Jørgen Finsveen 
 * @version 2.0
 * @since   18-11-2022
 */
public class DataBaseClient {
    
    private static final String HOSTNAME = "jdbc:mariadb://mysql579.loopia.se/finsveen_dev";
    private static final String USERNAME = "backend@f328341";
    private static final String PASSWORD = "NtnuBckGroup11!#?";

    /** The session which is used for communicating with the db-server. */
    private static Connection session;



    /**
     * Create a connection to the database server.
     * 
     * @throws SQLException
     *      if connection fails.
     */
    private static void connect() throws SQLException {
        if (session == null) {
            session = DriverManager.getConnection(HOSTNAME, USERNAME, PASSWORD);
        }
    }


    /**
     * Disconnect the database server.
     * 
     * @throws SQLException
     *      if disconnection fails.
     */
    private static void disconnect() throws SQLException {
        if (!session.isClosed()) {
            session.close();
            session = null;
        }
    }


    /**
     * Get status of patient from the database.
     * 
     * @param pid int PatientID
     * @return status of the patient.
     * @throws SQLException
     *      if database server connection/disconnection fails.
     */
    public static String getStatus(int pid) throws SQLException {
        String result = "";
        connect();
        try (
            PreparedStatement statement = session.prepareStatement(
            """
            SELECT Status
            FROM Patient
            WHERE PID = 
            """ + pid + ";")
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString("Status");
                disconnect();
            }
        }
        
        return result;
    }


    /**
     * Get PatientID from the database using the sensor id.
     * 
     * @param sensor sensor id assigned to a patient.
     * @return the PID for the patient.
     * @throws SQLException
     *      if database server connection/disconnection fails.
     */
    public static String getPID(String sensor) throws SQLException {
        String result = "";
        connect();
        try (
            PreparedStatement statement = session.prepareStatement(
                """
                SELECT PID
                FROM Patient
                WHERE Sensor =
                """ + "'" + sensor + "';");
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString("PID");
            }
            disconnect();
        }
        return result;
    }


    /**
     * Update the status value for a patient.
     * 
     * @param pid PatientID for the patient.
     * @param status the new value for the status.
     * @throws SQLException
     *      if database server connection/disconnection fails.
     */
    public static void setStatus(String pid, String status) throws SQLException {
        status = "'" + status + "'";
        connect();
        try (
            PreparedStatement statement = session.prepareStatement(
                """
                UPDATE Patient
                SET Status =       
                """ + status + "" +
                """
                 WHERE PID =  
                """ + "'" + pid + "';");
        ) {
            statement.executeQuery();
            disconnect();
        }
    }


    /**
     * Get the n last measurements done one a patient.
     * 
     * @param pid PatientID of the patient.
     * @param amount of measurements to fetch.
     * @return dataset of the measurments.
     * @throws SQLException
     *      if database server connection/disconnection fails.
     */
    public static ArrayList<ArrayList<String>> getMeasurements(String pid, int amount) throws SQLException {

        ArrayList<String> time = new ArrayList<>();
        ArrayList<String> sys = new ArrayList<>();
        ArrayList<String> dia = new ArrayList<>();
        ArrayList<String> hr  = new ArrayList<>();

        ArrayList<ArrayList<String>> result = new ArrayList<>();

        connect();
        try (
            PreparedStatement statement = session.prepareStatement(
                """
                SELECT Time, Dia, Sys, HR
                FROM PatientData
                WHERE PID =        
                """ + "'" + pid + "' " +
                """
                ORDER BY Time DESC LIMIT
                """ + "" + amount + ";"
            )
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                time.add(resultSet.getString("Time"));
                sys.add(resultSet.getString("Sys"));
                dia.add(resultSet.getString("Dia"));
                hr.add(resultSet.getString("HR"));
            }
            result.add(time);
            result.add(sys);
            result.add(dia);
            result.add(hr);
            disconnect();
        }
        return result;
    }


    /**
     * Insert a new measurement for a patient into the database.
     * 
     * @param pid PatientID
     * @param time Time
     * @param dia Dialostic
     * @param sys Systolic
     * @param hr Heartrate
     * @throws SQLException
     *      if database server connection/disconnection fails.
     */
    public static void insertMeasurement( String pid, String time, 
                                          String dia, String sys, 
                                          String hr)  
                                        throws SQLException {
        
        connect();
        try (
            PreparedStatement statement = session.prepareStatement(
                """
                INSERT INTO PatientData
                (PID, Time, Dia, Sys, HR) 
                VALUES ('
                """ + pid + "','" + time + "','" + dia + "','" + sys + "','" + hr + "');"
            )
        ) {
            statement.executeQuery();
            disconnect();                 
        }                    
    }


    /**
     * Get the gender of a patient from the database using the sensor id.
     * 
     * @param sensor sensor id assigned to a patient.
     * @return the gender of the patient.
     * @throws SQLException
     *      if database server connection/disconnection fails.
     */
    public static String getGender(String sensor) throws SQLException {
        String result = "";
        connect();
        try (
            PreparedStatement statement = session.prepareStatement(
                """
                SELECT gender
                FROM Patient
                WHERE Sensor =
                """ + "'" + sensor + "';");
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getString("gender");
            }
            disconnect();
        }
        return result;
    }


    /**
     * Get the age of a patient from the database using the sensor id.
     * 
     * @param sensor sensor id assigned to a patient.
     * @return the age of the patient.
     * @throws SQLException
     *      if database server connection/disconnection fails.
     */
    public static Date getAge(String sensor) throws SQLException {
        Date date = new Date();
        connect();
        try (
            PreparedStatement statement = session.prepareStatement(
                """
                SELECT DateOfBirth
                FROM Patient
                WHERE Sensor =
                """ + "'" + sensor + "';");
        ) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                date = (resultSet.getDate("DateOfBirth"));
            }
            disconnect();
        }
        return date;
    }
}