package codingdojo.rest;

import java.util.Date;


public class SavingExpedient  implements Cloneable {

    private int id = 0;
    private String idahorro = null;
    private String reference = null;
    private int isPaid = 0;
    private Date uploadTime = new Date();
    private String userEmail = null;
    private String additionalInfo = null;

    public Object clone() throws CloneNotSupportedException {
        SavingExpedient clonedInstance = new SavingExpedient();
        clonedInstance.setId(this.id);

        clonedInstance.setIdahorro(this.idahorro);
        clonedInstance.setReference(this.reference);
        clonedInstance.setPaid(this.isPaid());
        clonedInstance.setUploadTime(this.uploadTime);
        clonedInstance.setUserEmail(this.userEmail);
        clonedInstance.setAdditionalInfo(this.additionalInfo);
        return clonedInstance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdahorro() {
        return idahorro;
    }

    public void setIdahorro(String idahorro) {
        this.idahorro = idahorro;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean isPaid() {
        return isPaid == 1;
    }

    // has to be like this for hibernate to work
    public boolean getIsPaid(){
        return isPaid();
    }

    public void setPaid(boolean isPaid) {
        if (isPaid)
            this.isPaid = 1;
        else
            this.isPaid = 0;
    }

    // has to be like this for hibernate to work
    public void setIsPaid(boolean isPaid) {
        setPaid(isPaid);
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}