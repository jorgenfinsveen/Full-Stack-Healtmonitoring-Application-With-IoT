package no.ntnu.idata2304.group11.app;

import java.time.LocalDateTime;

/**
 * Represents a proxy for a received
 * measurement. Makes it easier to
 * handle its components.
 * 
 * @author jorgenfinsveen
 * @version 25-11-2022
 * @since 17-11-2022
 */
public class Measurement {

    private String sensor;
    private int sys;
    private int dia;
    private int pulse;
    private LocalDateTime date;
    private String pid;

    /**
     * Create a new Measurement instance.
     *
     * @param date of the measurement.
     * @param sys the systolic blood pressure measured.
     * @param dia the diastolic blood pressure measured.
     * @param pulse the heartrate measured.
     * @param sensor the serial number of the sensor which 
     *      did the measurement.
     * @return new Measurement instance.
     */
    public Measurement(String date, String sys,
                       String dia, String pulse,
                       String sensor) {
        this.sensor = sensor;
        this.sys = Integer.parseInt(sys);
        this.dia = Integer.parseInt(dia);
        this.pulse = Integer.parseInt(pulse);
        String[] dateArr = date.split(" ");
        String[] ymd = dateArr[0].split("-");
        String[] hms = dateArr[1].split(":");
        this.date = LocalDateTime.of(
            Integer.parseInt(ymd[0]), Integer.parseInt(ymd[1]), 
            Integer.parseInt(ymd[2]), Integer.parseInt(hms[0]), 
            Integer.parseInt(hms[1]), 0);
    }

    /**
     * Get the serial number of the sensor which did the measurement.
     *
     * @return sensor serial number as a String.
     */
    public String getSensor() {
        return this.sensor;
    }

    /**
     * Get the systolic blood pressure value which was measured.
     *
     * @return the systolic blood pressure value.
     */
    public int getSys() {
        return this.sys;
    }

    /**
     * Get the diastolic blood pressure value which was measured.
     *
     * @return the diastolic blood pressure value.
     */
    public int getDia() {
        return this.dia;
    }

    /**
     * Get the heartrate value which was measured.
     *
     * @return the heartrate measured.
     */
    public int getPulse() {
        return this.pulse;
    }

    /**
     * Access the date of the measurement.
     *
     * @return date of the measurement.
     */
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Get the PatientID of the patient which was
     * measured.
     *
     * @return PatientID of the measured patient.
     */
    public String getPid() {
        return this.pid;
    }

    /**
     * Mutate the PatientID of the patient which
     * was measured.
     *
     * @param pid the PatientID of the patient.
     */
    public void setPid(String pid) {
        if (!pid.isBlank()) this.pid = pid;
    }
}