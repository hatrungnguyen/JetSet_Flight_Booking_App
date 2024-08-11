package edu.birzeit.jetset.model;

import java.util.Date;

public class Passenger extends User {
    private String passportNumber;
    private Date passportIssueDate;
    private String passportIssuePlace;
    private Date passportExpirationDate;
    private String foodPreference;
    private Date dateOfBirth;
    private String nationality;

    public Passenger() {
        super();
    }

    public Passenger(String email, String phoneNumber, String firstName, String lastName, String password,
                     String passportNumber, Date passportIssueDate, String passportIssuePlace, Date passportExpirationDate,
                     String foodPreference, Date dateOfBirth, String nationality) {

        super(email, phoneNumber, firstName, lastName, password);
        this.passportNumber = passportNumber;
        this.passportIssueDate = passportIssueDate;
        this.passportIssuePlace = passportIssuePlace;
        this.passportExpirationDate = passportExpirationDate;
        this.foodPreference = foodPreference;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public Date getPassportIssueDate() {
        return passportIssueDate;
    }

    public void setPassportIssueDate(Date passportIssueDate) {
        this.passportIssueDate = passportIssueDate;
    }

    public String getPassportIssuePlace() {
        return passportIssuePlace;
    }

    public void setPassportIssuePlace(String passportIssuePlace) {
        this.passportIssuePlace = passportIssuePlace;
    }

    public Date getPassportExpirationDate() {
        return passportExpirationDate;
    }

    public void setPassportExpirationDate(Date passportExpirationDate) {
        this.passportExpirationDate = passportExpirationDate;
    }

    public String getFoodPreference() {
        return foodPreference;
    }

    public void setFoodPreference(String foodPreference) {
        this.foodPreference = foodPreference;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}

