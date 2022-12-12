package com.ashfaq.systemproject;

public class Job {
    private String JobTitle, JobCompany, JobSite, JobKey;

    public Job(String jobTitle, String jobCompany, String jobSite, String jobKey) {
        JobTitle = jobTitle;
        JobCompany = jobCompany;
        JobSite = jobSite;
        JobKey = jobKey;
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
