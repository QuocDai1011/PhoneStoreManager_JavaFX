package org.phonestoremanager.services;

import org.phonestoremanager.daos.RoleDAO;
import org.phonestoremanager.models.AccountModel;

public class AccountService {
    public String createNewAccount(String position, String userName, String password,
            String confirmPassword, AccountModel accountModel) {


        if(!password.equals(confirmPassword)) {
            return "Xác nhận mật khẩu không hợp lệ!";
        }

        String roleName = "";
        if(position.equals("Nhân Viên")) {
            roleName = "NV";
        }else {
            roleName = "AD";
        }

        accountModel.setRoleID(RoleDAO.getRoleIDByRoleName(roleName));
        accountModel.setUserName(userName);
        accountModel.setPassword(password);

        return "success";

    }
}
