package com.socgen.saviour;

import java.util.Date;
import java.util.Map;

public class Request {
    private String patientName;
    private String hospital;
    private String address;
    private String city;
    private String bloodGroup;
    private String units;
    private String relationship;
    private String requesterNumber;
    private String requesterName;
    private Long timestamp;
    private Map<String,String> donors;

    public Request() {
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getRequesterNumber() {
        return requesterNumber;
    }

    public void setRequesterNumber(String requesterNumber) {
        this.requesterNumber = requesterNumber;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, String> getDonors() {
        return donors;
    }

    public void setDonors(Map<String, String> donors) {
        this.donors = donors;
    }
}
