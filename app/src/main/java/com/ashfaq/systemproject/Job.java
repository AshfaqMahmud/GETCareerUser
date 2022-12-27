package com.ashfaq.systemproject;

public class Job {
    private String JobTitle, JobCompany, JobSite, JobKey, Jobdate;

    public Job(String jobTitle, String jobCompany, String jobSite, String jobKey, String jobdate) {
        JobTitle = jobTitle;
        JobCompany = jobCompany;
        JobSite = jobSite;
        JobKey = jobKey;
        Jobdate = jobdate;
    }

    public String getJobdate() {
        return Jobdate;
    }

    public void setJobdate(String jobdate) {
        Jobdate = jobdate;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getJobCompany() {
        return JobCompany;
    }

    public void setJobCompany(String jobCompany) {
        JobCompany = jobCompany;
    }

    public String getJobSite() {
        return JobSite;
    }

    public void setJobSite(String jobSite) {
        JobSite = jobSite;
    }

    public String getJobKey() {
        return JobKey;
    }

    public void setJobKey(String jobKey) {
        JobKey = jobKey;
    }
}
