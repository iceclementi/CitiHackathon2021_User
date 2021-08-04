package com.example.savelah;

public class MyLoyaltyPoints {
    private String id;
    private String title;
    private String details;
    private int cost;
    private int loyalty;


    public MyLoyaltyPoints(Voucher voucher) {
        this.id = generateRandomId();
        this.title = voucher.getTitle();
        this.details = voucher.getDetails();
        this.cost = voucher.getCost();
        this.loyalty = voucher.getLoyaltyBonus();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(int loyalty) {
        this.loyalty = loyalty;
    }

    private String generateRandomId() {
        return Utils.generateRandomString(20);
    }
}
