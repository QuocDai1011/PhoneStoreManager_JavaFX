package org.phonestoremanager.services;

import org.phonestoremanager.models.EmployeeModel;

public class EmployeeService {

    public String createNewEmployee(String firstNameValue, String lastNameValue, String emailValue, String phoneNumberValue,
                                           String addressValue, String positionValue, String genderValue, String salaryValue_raw, EmployeeModel newEmployee) {


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
}
