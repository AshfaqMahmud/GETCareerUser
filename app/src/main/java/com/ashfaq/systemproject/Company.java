package com.ashfaq.systemproject;

import com.google.firebase.database.IgnoreExtraProperties;

public class Company {
    private String CompanyName, CompanyLoc, CompanyType,Companykey;

    public Company() {

    }


    public Company(String companyName, String companyLoc, String companyType, String uid) {
        CompanyName = companyName;
        CompanyLoc = companyLoc;
        CompanyType = companyType;
        Companykey = uid;
    }



    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyLoc() {
        return CompanyLoc;
    }

    public void setCompanyLoc(String companyLoc) {
        CompanyLoc = companyLoc;
    }

    public String getCompanyType() {
        return CompanyType;
    }

    public void setCompanyType(String companyType) {
        CompanyType = companyType;
    }
    public String getCompanykey() {
        return Companykey;
    }

    public void setCompanykey(String companykey) {
        Companykey = companykey;
    }

}
