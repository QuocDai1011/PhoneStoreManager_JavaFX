package org.phonestoremanager.models;

import java.sql.Date;
import java.sql.Timestamp;

public class EmployeeModel {
    private int employeeID;
    private int accountID;
    private String firstName;
    private String lastName;
    private int gender; // Có thể dùng 0: Nam, 1: Nữ
    private String genderText;
    private String email;
    private String phoneNumber;
    private String address;
    private String position;
    private double salary;

    private Timestamp createAt;
    private Timestamp updateAt;
    private String note;
    private Date dateOfBirth;
    private Date startDate;

    public EmployeeModel() {}

    public EmployeeModel(int employeeID, int accountID, String firstName, String lastName, int gender, String email,
                         String phoneNumber, String address, String position, double salary,
                         Timestamp createAt, Timestamp updateAt, String note, Date dateOfBirth, Date startDate) {
        this.employeeID = employeeID;
        this.accountID = accountID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.position = position;
        this.salary = salary;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.note = note;
        this.dateOfBirth = dateOfBirth;
        this.startDate = startDate;
    }

    // Getter & Setter
    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getGenderText() {
        return genderText;
    }

    public void setGenderText(String genderText) {
        this.genderText = genderText;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "EmployeeModel{" +
                "employeeID=" + employeeID +
                ", accountID=" + accountID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", createAt=" + createAt +
                ", updateAt=" + updateAt +
                ", note='" + note + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", startDate=" + startDate +
                '}';
    }
}
