package org.phonestoremanager.services;

import org.phonestoremanager.models.EmployeeModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeService {

    public String createNewEmployee(String firstNameValue, String lastNameValue, String emailValue,
                                    String phoneNumberValue,
                                           String addressValue, String positionValue,
                                    String genderValue, String salaryValue_raw, EmployeeModel newEmployee) {


        if(firstNameValue == null || firstNameValue.equals("")) {
            return "Dữ liệu bị thiếu!";
        }
        if(lastNameValue == null || lastNameValue.equals("")) {
            return "Dữ liệu bị thiếu!";
        }
        if(phoneNumberValue == null || phoneNumberValue.equals("")) {
            return "Dữ liệu bị thiếu!";
        }

        newEmployee.setFirstName(firstNameValue);
        newEmployee.setLastName(lastNameValue);
        newEmployee.setEmail(emailValue);
        newEmployee.setPhoneNumber(phoneNumberValue);
        newEmployee.setAddress(addressValue);
        newEmployee.setPosition(positionValue);

        if(genderValue.equals("Nam")) {
            newEmployee.setGender(1); // 1 la Nam
        }else {
            newEmployee.setGender(0); // 0 la Nu
        }

        double salaryValue = 0;
        try {
            salaryValue = Double.parseDouble(salaryValue_raw);
        } catch (Exception e) {
            e.printStackTrace();
            return "Dữ liệu không hợp lệ!";
        }

        newEmployee.setSalary(salaryValue);

        return "success";

    }

    public static EmployeeModel createByResultSet(ResultSet rs) throws SQLException {
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setEmployeeID(rs.getInt("EmployeeID"));
        employeeModel.setFirstName(rs.getString("FirstName"));
        employeeModel.setLastName(rs.getString("LastName"));
        if(rs.getInt("Gender") == 0) {
            employeeModel.setGenderText("Nữ");
        }else {
            employeeModel.setGenderText("Nam");
        }
        employeeModel.setEmail(rs.getString("Email"));
        employeeModel.setPhoneNumber(rs.getString("PhoneNumber"));
        employeeModel.setAddress(rs.getString("Address"));
        employeeModel.setPosition(rs.getString("Position"));
        employeeModel.setSalary(rs.getDouble("Salary"));
        return employeeModel;
    }
}
