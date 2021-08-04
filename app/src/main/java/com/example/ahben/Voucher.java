package com.example.ahben;

public class Voucher {
    private String id;
    private String title;
    private String details;
    private int cost;
    private int loyaltyBonus;
    private int validity;
    private String expiryDate;
    private VoucherStatus status;

    public Voucher(String id, String title, String details, int cost, int loyaltyBonus, int validity) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.cost = cost;
        this.loyaltyBonus = loyaltyBonus;
        this.validity = validity;

        status = VoucherStatus.VALID;
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

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", cost=" + cost +
                ", loyaltyBonus=" + loyaltyBonus +
                ", validity=" + validity +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
