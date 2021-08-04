package com.example.savelah;

public class Voucher {
    private String title;
    private String details;
    private int value;
    private int cost;
    private int loyaltyBonus;
    private int validity;

    public Voucher(String title, String details, int value, int cost, int loyaltyBonus, int validity) {
        this.title = title;
        this.details = details;
        this.value = value;
        this.cost = cost;
        this.loyaltyBonus = loyaltyBonus;
        this.validity = validity;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getLoyaltyBonus() {
        return loyaltyBonus;
    }

    public void setLoyaltyBonus(int loyaltyBonus) {
        this.loyaltyBonus = loyaltyBonus;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    @Override
    public String toString() {
        return "Voucher {" +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", cost=" + cost +
                ", loyaltyBonus=" + loyaltyBonus +
                ", validity=" + validity +
                '}';
    }
}
