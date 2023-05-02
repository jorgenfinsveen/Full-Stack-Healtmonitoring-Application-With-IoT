package no.ntnu.idata2304.group11.json;

import java.io.InputStream;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Tool for reading the JSON-file containing
 * the chart data of the different health-parameters
 * regarding males and females in different stages of
 * life.
 * 
 * @version 25-11-2022
 * @author jorgenfinsveen
 * @since 25-11-2022
 */
public class JsonHandler {
    

    /** Path to the JSON-file. */
    private static final String PATH = "json/json/pressure_chart.json";
    /** Exception for error when trying to read the JSON-file. */
    private static final NullPointerException NO_FILE = 
        new NullPointerException("File not found: " + PATH);


    /**
     * Access the JSON-file and retrieve the health-parameters
     * for a patient based on the patients age and sex.
     * 
     * @param sex of the patient. ("male"/"female")
     * @param age integer representing the age of the patient. 
     * @return array on the format [sys, dia, hr_min, hr_max]
     */
    public static int[] getParameters(String sex, int age) {

        /* Locating the JSON-file in this directory. */
        InputStream stream = JsonHandler.class.getResourceAsStream(PATH);
        /* If not found, an exception will be thrown. */
        if (stream == null) throw NO_FILE;

        /* Accessing the JSON-file and reading content. */
        JSONTokener tokener = new JSONTokener(stream);
        JSONObject json = new JSONObject(tokener);
        JSONObject task;

        /* Containers for the different parameters */
        int sys = 0;
        int dia = 0;
        int hr_min = 0;
        int hr_max = 0;
        int ageMax;
        int ageMin;

        /* Iterates over each JSON-Object finding. */
        for (int i = 0; i < json.getInt("length"); i++) {
            task = json.getJSONObject(i + "");
            ageMax = task.getInt("age_max");
            ageMin = task.getInt("age_min");

            /* Locates the JSON-Object which answers to the patients age. */
            if (age <= ageMax && age >= ageMin) {
                JSONObject details = task.getJSONObject(sex+"");
                sys = details.getInt("sys");
                dia = details.getInt("dia");
                hr_min  = details.getInt("hr_min");
                hr_max  = details.getInt("hr_max");
            }
        }

        return new int[] {sys, dia, hr_min, hr_max};
    }
}
