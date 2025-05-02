package org.phonestoremanager.services;

import org.phonestoremanager.repositories.AccountRepository;
import org.phonestoremanager.repositories.RoleRepository;
import org.phonestoremanager.models.AccountModel;
import org.phonestoremanager.utils.PasswordEncrypt;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountService {
    public String createNewAccount(String position, String userName, String password,
            String confirmPassword, AccountModel accountModel) {


        if(!password.equals(confirmPassword)) {
            return "Xác nhận mật khẩu không hợp lệ!";
        }

        String roleName = "";
        if(position.equals("Nhân Viên")) {
            roleName = "NV";
        }else if(position.equals("Admin")) {
            roleName = "AD";
        }else {
            roleName = "KH";
        }

        accountModel.setRoleID(RoleRepository.getRoleIDByRoleName(roleName));
        accountModel.setUserName(userName);
        accountModel.setPassword(password);

        return "success";

    }

    public boolean checkAccountWhenLogIn(String userName, String password) throws SQLException {
        ArrayList <String> result = AccountRepository.getUserNameAndPasswordByUserName(userName);

        if (result.isEmpty()) return false;

        if(result.get(0).equals(userName) && password.equals(PasswordEncrypt.decryptAES(result.get(1)))) {
            return true;
        }

        return false;
    }

}
