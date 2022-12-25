package com.ashfaq.systemproject;

public class Review {
    private String rev_no, rev_star, rev_fed;

    public Review(String rev_no, String rev_star, String rev_fed) {
        this.rev_no = rev_no;
        this.rev_star = rev_star;
        this.rev_fed = rev_fed;
    }

    public String getRev_no() {
        return rev_no;
    }

    public void setRev_no(String rev_no) {
        this.rev_no = rev_no;
    }

    public String getRev_star() {
        return rev_star;
    }

    public void setRev_star(String rev_star) {
        this.rev_star = rev_star;
    }

    public String getRev_fed() {
        return rev_fed;
    }

    public void setRev_fed(String rev_fed) {
        this.rev_fed = rev_fed;
    }
}
