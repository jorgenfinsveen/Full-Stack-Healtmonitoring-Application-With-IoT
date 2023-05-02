package no.ntnu.idata2304.group11.app;

import java.util.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import no.ntnu.idata2304.group11.database.DataBaseClient;
import no.ntnu.idata2304.group11.json.JsonHandler;

/**
 * Processes data received from the analyzer.<p>
 * 
 * Unpacks data and classifies the staus of a patient 
 * by comparing health-parameters and measurements done 
 * by sensors. This data will be uploaded to the database
 * which makes it accessible to the user through the web
 * application.
 * 
 * @since    10-11-2022
 * @version  25-11-2022
 * @author   jorgenfinsveen
 */
public class Coordinator {
    
    /**
     * Receive the data from the server and process it.
     * 
     * @param message from the server.
     */
    public static void receive(String measurement) {
        System.out.println(measurement);
        String[] data = measurement.split(";");
        Measurement measure = new Measurement(
                                               data[0], data[1], 
                                               data[2], data[3],
                                               data[4]);
        try {
            identifyPatient(measure);
            classifyPatientStatus(measure, 5);
            uploadToDatabase(measure);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    /**
     * Determine what the health-status of the patient should be
     * considering the most recent measurement and the average og 
     * the n last measurements done.
     *
     * @param measurement the last measurement done.
     * @param num the number of earlier measurements done which should be considered.
     * @throws SQLException
     *      if errors connecting to the database occurs.
     */
    private static void classifyPatientStatus(Measurement measure, int num) throws SQLException {

        /** ArrayList containing the num previous measurements done on the patient. */
        ArrayList<ArrayList<String>> result = DataBaseClient.getMeasurements(measure.getPid(), num);

        /* 
         * Adds the most recent measurement to each ArrayList.
        */
        result.get(1).add(measure.getSys() + "");
        result.get(2).add(measure.getDia() + "");
        result.get(3).add(measure.getPulse() + "");

        /*
         * Creates int-arrays for each ArrayList of string representation of the measurements. 
        */
        int[] sysArr = result.get(1).stream().mapToInt(Integer::parseInt).toArray();
        int[] diaArr = result.get(2).stream().mapToInt(Integer::parseInt).toArray();
        int[] hrArr = result.get(3).stream().mapToInt(Integer::parseInt).toArray();

        /** The biological gender of the patient in question. */
        String sex = DataBaseClient.getGender(measure.getSensor());

        /** The patients date of birth as an instance of the Date class. */
        Date dateOfBirth = DataBaseClient.getAge(measure.getSensor());

        /** The patients age measured in years. */
        int age = convertToAge(dateOfBirth);

        /** The new status  */
        String status = evaluate(sex, age, sysArr, diaArr, hrArr);

        /** Updates the status of the patient stored in the database. */
        DataBaseClient.setStatus(measure.getPid(), status);
    }



    /**
     * Identify which patient the measurement has been performed on.
     *
     * @param measurement the last measurement done.
     * @throws SQLException
     *      if errors connecting to the database occurs.
     */
    private static void identifyPatient(Measurement measure) throws SQLException {
        measure.setPid(DataBaseClient.getPID(measure.getSensor()));
        if ("null".equals(measure.getSensor())) {
            throw new IllegalArgumentException("Sensor not recognized: " + measure.getSensor());
        } 
    }



    /**
     * Send to database-handler for uploading.
     * @throws SQLException
     */
    private static void uploadToDatabase(Measurement measure) throws SQLException {
        String date = measure.getDate() + "";
        String dia = measure.getDia() + "";
        String sys = measure.getSys() + "";
        String pulse = measure.getPulse() + "";
        DataBaseClient.insertMeasurement(
            measure.getPid(), date,
            dia, sys, pulse);
    }



    /**
     * Evaluate earlier and current measurements done on a patient.
     * Will classify the health status of a patient given on a set
     * of rules.
     * 
     * @param sex patient biological gender.
     * @param age of the patient measured in years.
     * @param sys array of systolic blood pressure measurements.
     * @param dia array of diastolic blood pressure measurements.
     * @param hr  array of heartrate measurements.
     * @return String evaluation of the patients health status.
     *      ("stable" / "unusual" / "critical")
     */
    private static String evaluate(String sex, int age, int[] sys, int[] dia, int[] hr) {

        /** 
         * Array of health-parameters which are considered normal for a patient
         * of a certain age and gender.
         */
        int[] optimals = JsonHandler.getParameters(sex, age);

        /** Last systolic blood pressure measurement. */
        int lastSys = sys[sys.length - 1];
        /** Last diastolic blood pressure measurement. */
        int lastDia = dia[dia.length - 1];
        /** Last heartrate measurement. */
        int lastHr  = hr[hr.length - 1];

        /** Default evaluation of the patients health. */
        String result = "stable";

       
        /*
         * Evaluates the last measurements. If the values differs from the
         * excpected values with more than the given bound, the evaluation
         * will be that the patient has an unusual health-status.
         */
        if (considerLastMeasurement(lastSys, optimals[0], 10)
            || considerLastMeasurement(lastDia, optimals[1], 10)
            || lastHr < optimals[2] || lastHr > optimals[3]) {

            result = "unusual";
        }


        /** 
         * Average of the given measurements for heartrate
         * including the most recent. 
         */
        int hrAvg  = getAverage(hr);


        /*
         * Evaluates the average of the n most recent measurements. If the values
         * differs from the excpected values with more than the given bound, the
         * evaluation will be that the patients health is critical, which overwrites
         * both the stable- and the unusual status.
         */
        if (considerAverageMeasurements(getAverage(sys), optimals[0], 15)
            || considerAverageMeasurements(getAverage(dia), optimals[1], 15)
            ) {

            result = "critical";    
        }

        return result;
    }



    /**
     * Determine whether the given measurement is within the given bounds
     * of the excpected health-value for a given patient.
     * 
     * @param measurement int value which was measured.
     * @param expexted value of the measurement.
     * @param bound representing the upper and lower boundary of acceptable values.
     * @return boolean representing the evaluation of the measurement.
     *      true if the measurements are not within the boundary, else false.
     */
    private static boolean considerLastMeasurement(int measurement, int expected, 
                                                   int bound) {
        return (measurement > (expected + bound)
            || measurement < (expected - bound));
    }



    /**
     * Determine whether the given collection of measurements is within 
     * the given bounds of the excpected health-value for a given patient.
     * 
     * @param measurement int the average value of the measurements.
     * @param expexted value of the measurement.
     * @param bound representing the upper and lower boundary of acceptable values.
     * @return boolean representing the evaluation of the measurements.
     *      true if the measurements are not within the boundary, else false.
     */
    private static boolean considerAverageMeasurements(int average, int expected,
                                                       int bound) {
        return (average > (expected + bound) 
                || average < (expected - bound));                                                
    }



    /**
     * Calculate the average of an array of integers.
     * 
     * @param int[] array of integers.
     * @return average of the given integers.
     */
    private static int getAverage(int[] arr) {
        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        return sum/arr.length;
    }
    


    /**
     * Find the current age of a patient by considering
     * the patients date of birth.
     *
     * @param date of birth as an instance of the Date class.
     * @return age measured in years as an integer.
     */
    private static int convertToAge(Date date) {

        /** Finds the current date. */
        Date currDate = new Date();

        /* Defines a DateFormat which is compatible with the Date-objects. */
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        /*
         * Converts each date to an Integer representation.
        */
        int d1 = Integer.parseInt(formatter.format(date));                            
        int d2 = Integer.parseInt(formatter.format(currDate));     
        
        /*
         * Returns the difference between the two dates
         * converted to be measured in years. 
        */
        return (d2 - d1) / 10000; 
    }
}