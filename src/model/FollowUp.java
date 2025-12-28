package model;

import java.time.LocalDate;

/**
 * FollowUp entity representing patient follow-up visits
 */
public class FollowUp {
    private int id;
    private int patientId;
    private int visitNo;
    private LocalDate visitDate;
    private double height;
    private double weight;
    private double bmi;
    private String bloodPressure;
    private int spo2;
    private LocalDate nextFollowUpDate;
    private String nadi;
    private String samanyaLakshana;
    private String rxTreatment;
    private int days;
    private double totalPayment;
    private double pendingPayment;
    private String notes;
    
    // History fields
    private String kco; // K/C/O
    private String ho;  // H/O
    private String sho; // S/H/O
    private String mh;  // M/H
    private String oh;  // O/H
    private String ah;  // A/H
    
    // Samanya Parikshan fields
    private String mal;
    private String mutra;
    private String jivha;
    private String shudha;
    private String trushna;
    private String nidra;
    private String sweda;
    private String sparsha;
    private String druka;
    private String shabda;
    private String akruti;

    // Constructors
    public FollowUp() {
    }

    public FollowUp(int patientId, int visitNo, LocalDate visitDate) {
        this.patientId = patientId;
        this.visitNo = visitNo;
        this.visitDate = visitDate;
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

    public int getVisitNo() {
        return visitNo;
    }

    public void setVisitNo(int visitNo) {
        this.visitNo = visitNo;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public int getSpo2() {
        return spo2;
    }

    public void setSpo2(int spo2) {
        this.spo2 = spo2;
    }

    public LocalDate getNextFollowUpDate() {
        return nextFollowUpDate;
    }

    public void setNextFollowUpDate(LocalDate nextFollowUpDate) {
        this.nextFollowUpDate = nextFollowUpDate;
    }

    public String getNadi() {
        return nadi;
    }

    public void setNadi(String nadi) {
        this.nadi = nadi;
    }

    public String getSamanyaLakshana() {
        return samanyaLakshana;
    }

    public void setSamanyaLakshana(String samanyaLakshana) {
        this.samanyaLakshana = samanyaLakshana;
    }

    public String getRxTreatment() {
        return rxTreatment;
    }

    public void setRxTreatment(String rxTreatment) {
        this.rxTreatment = rxTreatment;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public double getPendingPayment() {
        return pendingPayment;
    }

    public void setPendingPayment(double pendingPayment) {
        this.pendingPayment = pendingPayment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getKco() {
        return kco;
    }

    public void setKco(String kco) {
        this.kco = kco;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getSho() {
        return sho;
    }

    public void setSho(String sho) {
        this.sho = sho;
    }

    public String getMh() {
        return mh;
    }

    public void setMh(String mh) {
        this.mh = mh;
    }

    public String getOh() {
        return oh;
    }

    public void setOh(String oh) {
        this.oh = oh;
    }

    public String getAh() {
        return ah;
    }

    public void setAh(String ah) {
        this.ah = ah;
    }

    public String getMal() {
        return mal;
    }

    public void setMal(String mal) {
        this.mal = mal;
    }

    public String getMutra() {
        return mutra;
    }

    public void setMutra(String mutra) {
        this.mutra = mutra;
    }

    public String getJivha() {
        return jivha;
    }

    public void setJivha(String jivha) {
        this.jivha = jivha;
    }

    public String getShudha() {
        return shudha;
    }

    public void setShudha(String shudha) {
        this.shudha = shudha;
    }

    public String getTrushna() {
        return trushna;
    }

    public void setTrushna(String trushna) {
        this.trushna = trushna;
    }

    public String getNidra() {
        return nidra;
    }

    public void setNidra(String nidra) {
        this.nidra = nidra;
    }

    public String getSweda() {
        return sweda;
    }

    public void setSweda(String sweda) {
        this.sweda = sweda;
    }

    public String getSparsha() {
        return sparsha;
    }

    public void setSparsha(String sparsha) {
        this.sparsha = sparsha;
    }

    public String getDruka() {
        return druka;
    }

    public void setDruka(String druka) {
        this.druka = druka;
    }

    public String getShabda() {
        return shabda;
    }

    public void setShabda(String shabda) {
        this.shabda = shabda;
    }

    public String getAkruti() {
        return akruti;
    }

    public void setAkruti(String akruti) {
        this.akruti = akruti;
    }

    @Override
    public String toString() {
        return "FollowUp{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", visitNo=" + visitNo +
                ", visitDate=" + visitDate +
                '}';
    }
}

