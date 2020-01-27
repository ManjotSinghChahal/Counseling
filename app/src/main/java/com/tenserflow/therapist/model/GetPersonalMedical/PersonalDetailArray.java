package com.tenserflow.therapist.model.GetPersonalMedical;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PersonalDetailArray {

    @SerializedName("personal_status")
    @Expose
    private String personalStatus;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("sub_cat")
    @Expose
    private String subCat;
    @SerializedName("therapy_id")
    @Expose
    private String therapyId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("country_code_C")
    @Expose
    private String countryCodeC;
    @SerializedName("cell_phone")
    @Expose
    private String cellPhone;
    @SerializedName("country_code_H")
    @Expose
    private String countryCodeH;
    @SerializedName("home_phone")
    @Expose
    private String homePhone;

    public String getPersonalStatus() {
        return personalStatus;
    }

    public void setPersonalStatus(String personalStatus) {
        this.personalStatus = personalStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSubCat() {
        return subCat;
    }

    public void setSubCat(String subCat) {
        this.subCat = subCat;
    }

    public String getTherapyId() {
        return therapyId;
    }

    public void setTherapyId(String therapyId) {
        this.therapyId = therapyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCodeC() {
        return countryCodeC;
    }

    public void setCountryCodeC(String countryCodeC) {
        this.countryCodeC = countryCodeC;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getCountryCodeH() {
        return countryCodeH;
    }

    public void setCountryCodeH(String countryCodeH) {
        this.countryCodeH = countryCodeH;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

}