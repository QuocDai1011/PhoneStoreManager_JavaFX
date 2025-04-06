package org.phonestoremanager.services;

import org.phonestoremanager.exeptions.PhoneNumberValidation;
import org.phonestoremanager.models.CustomerModel;

public class CustomerService {

    public String createNewCustomer (String firstName, String lastName, String address, String phoneNumber,
                                     String email, CustomerModel newCustomer) {
        // xu li cac du lieu NOT NULL
        if(firstName.isEmpty() && lastName.isEmpty()) {
            return "Chưa nhập họ và tên!";
        }else if (firstName.isEmpty()) {
            return "Bạn chưa nhập họ!";
        } else if (lastName.isEmpty()) {
            return "Bạn chưa nhập tên!";
        } else if (phoneNumber.isEmpty()) {
            return "Bạn chưa nhập số điện thoại";
        }

        // xu li ngoai le
        try {
            PhoneNumberValidation.validatePhoneNumber(phoneNumber);
        }catch (IllegalArgumentException e) {
            return "Số điện thoại chỉ chứa chữ số và bắt đầu bằng số 0";
        }


        //tao doi tuong customer
        newCustomer.setFirstName(firstName);
        newCustomer.setLastName(lastName);
        newCustomer.setAddress(address);
        newCustomer.setPhoneNumber(phoneNumber);
        newCustomer.setEmail(email);

        return "success";
    }
}
