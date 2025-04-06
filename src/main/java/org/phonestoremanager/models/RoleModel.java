package org.phonestoremanager.models;

public class RoleModel {
    private int roleID;
    private String roleName;
    private String roleDecription;

    public RoleModel() {
    }

    public RoleModel(int roleID, String roleName, String roleDecription) {
        this.roleID = roleID;
        this.roleName = roleName;
        this.roleDecription = roleDecription;
    }

    public RoleModel(String roleName, String roleDecription) {
        this.roleName = roleName;
        this.roleDecription = roleDecription;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDecription() {
        return roleDecription;
    }

    public void setRoleDecription(String roleDecription) {
        this.roleDecription = roleDecription;
    }

}
