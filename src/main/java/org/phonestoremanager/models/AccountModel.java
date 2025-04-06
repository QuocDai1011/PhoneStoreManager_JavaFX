package org.phonestoremanager.models;

public class AccountModel {
    private int accountID;
    private int roleID;
    private String userName;
    private String password;

    public AccountModel() {
    }

    public AccountModel(int accountID, int roleID, String userName, String password) {
        this.accountID = accountID;
        this.roleID = roleID;
        this.userName = userName;
        this.password = password;
    }

    public AccountModel(int roleID, String userName, String password) {
        this.roleID = roleID;
        this.userName = userName;
        this.password = password;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    @Override
    public String toString() {
        return "AccountModel{" +
                "accountID=" + accountID +
                ", roleID=" + roleID +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
