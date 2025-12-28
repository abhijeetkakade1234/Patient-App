package model;

import java.time.LocalDate;

/**
 * Panchakarma entity representing Panchakarma treatment details
 */
public class Panchakarma {
    private int id;
    private int patientId;
    private String panchakarmaName;
    private String advised;
    private int noOfDays;
    private int day;
    private String typesOfKarmaAndMedicines;
    private double price;
    private String durationTime;
    private String therapistTime;
    private LocalDate dayAndDate;
    private String note;

    // Constructors
    public Panchakarma() {
    }

    public Panchakarma(int patientId, String panchakarmaName) {
        this.patientId = patientId;
        this.panchakarmaName = panchakarmaName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPanchakarmaName() {
        return panchakarmaName;
    }

    public void setPanchakarmaName(String panchakarmaName) {
        this.panchakarmaName = panchakarmaName;
    }

    public String getAdvised() {
        return advised;
    }

    public void setAdvised(String advised) {
        this.advised = advised;
    }

    public int getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        this.noOfDays = noOfDays;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getTypesOfKarmaAndMedicines() {
        return typesOfKarmaAndMedicines;
    }

    public void setTypesOfKarmaAndMedicines(String typesOfKarmaAndMedicines) {
        this.typesOfKarmaAndMedicines = typesOfKarmaAndMedicines;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(String durationTime) {
        this.durationTime = durationTime;
    }

    public String getTherapistTime() {
        return therapistTime;
    }

    public void setTherapistTime(String therapistTime) {
        this.therapistTime = therapistTime;
    }

    public LocalDate getDayAndDate() {
        return dayAndDate;
    }

    public void setDayAndDate(LocalDate dayAndDate) {
        this.dayAndDate = dayAndDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Panchakarma{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", panchakarmaName='" + panchakarmaName + '\'' +
                ", day=" + day +
                '}';
    }
}

