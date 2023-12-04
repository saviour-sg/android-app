package com.socgen.saviour;

public class User {
    private String phone;
    private String name;
    private String empId;
    private String email;
    private String bloodGroup;
    private String baseLocation;

    public User(String phone, String name, String empId, String email, String bloodGroup, String baseLocation) {
        this.phone = phone;
        this.name = name;
        this.empId = empId;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.baseLocation = baseLocation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getBaseLocation() {
        return baseLocation;
    }

    public void setBaseLocation(String baseLocation) {
        this.baseLocation = baseLocation;
    }
}
