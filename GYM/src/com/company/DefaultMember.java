package com.company;

public class DefaultMember {
    
    private int membershipNo;
    private String name;
    private Date startMembershipDate;

    public int getMembershipNumber() {
        return membershipNo;
    }
    public void setMembershipNumber(int membershipNumber) {
        this.membershipNo = membershipNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getStartMembershipDate() {
        return startMembershipDate;
    }
    public void setStartMembershipDate(Date startMembershipDate) {
        this.startMembershipDate = startMembershipDate;
    }
}
