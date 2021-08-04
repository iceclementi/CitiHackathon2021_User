package com.example.savelah;

import com.google.gson.Gson;

public class MyVoucher {

    private String id;
    private String title;
    private String details;
    private int value;
    private String expiryDate;
    private VoucherStatus status;

    public MyVoucher(String id, String title, String details, int value, String expiryDate, VoucherStatus status) {
        this.id = id;
        this.title = title;
        this.details = details;
        this.value = value;
        this.expiryDate = expiryDate;
        this.status = status;
    }

    public MyVoucher(Voucher voucher) {
        this.id = generateRandomId();
        this.title = voucher.getTitle();
        this.details = voucher.getDetails();
        this.value = voucher.getValue();
        this.expiryDate = Utils.getExpiryDate(voucher.getValidity());
        this.status = validateStatus();
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    private VoucherStatus getStatus() {
        return status;
    }

    private void setStatus(VoucherStatus status) {
        this.status = status;
    }

    private String generateRandomId() {
        return Utils.generateRandomString(20);
    }

    private VoucherStatus validateStatus() {
        if (Utils.getDaysTo(expiryDate) < 0) {
            return VoucherStatus.EXPIRED;
        } else {
            return VoucherStatus.VALID;
        }
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
