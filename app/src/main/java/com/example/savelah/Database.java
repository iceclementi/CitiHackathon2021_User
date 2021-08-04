package com.example.savelah;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Database {
    private static final String STORE_VOUCHERS_KEY = "STORE_VOUCHERS";
    private static final String MY_VOUCHERS_KEY = "MY_VOUCHERS";
    private static final String MY_LP_KEY = "MY_LP";
    private static final String MY_LOYALTY_KEY = "MY_LOYALTY";
    private static final String MY_LOYALTY_VH_Key = "MY_LOYALTY_VH";

    private static Database instance = null;

    // TODO: To change to cloud database
    private SharedPreferences sharedPreferences;

    private Database(Context context) {
        sharedPreferences = context.getSharedPreferences("db", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (getStoreVouchers() == null) {
            initialiseVouchers();
        }

        if (getMyVouchers() == null) {
            editor.putString(MY_VOUCHERS_KEY, new Gson().toJson(new ArrayList<MyVoucher>()));
            editor.commit();
        }

        if (getMyLP() == 0) {

        }

        initialiseLoyalty();
        if (getMyLoyaltyPoints() == null) {
            initialiseLoyalty();
        }
    }

    public static synchronized Database getInstance(Context context) {
        if (instance == null) {
            instance = new Database(context);
        }

        return instance;
    }

    private void reset() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MY_VOUCHERS_KEY, new Gson().toJson(new ArrayList<MyVoucher>()));
        editor.commit();
    }


    private void initialiseVouchers() {
        ArrayList<Voucher> vouchers = new ArrayList<>();
        vouchers.add(new Voucher("$5 OFF", "WITH MINIMUM SPENDING OF $50", 5, 5, 1, 14));
        vouchers.add(new Voucher("$10 OFF", "WITH MINIMUM SPENDING OF $100", 10, 10, 2, 14));
        vouchers.add(new Voucher("$20 OFF", "WITH MINIMUM SPENDING OF $200", 20, 20, 5, 30));
        vouchers.add(new Voucher("$50 OFF", "WITH MINIMUM SPENDING OF $500", 50, 50, 20, 30));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STORE_VOUCHERS_KEY, new Gson().toJson(vouchers));
        editor.commit();
    }

    private void initialiseLoyalty() {
        ArrayList<Voucher> vouchers = new ArrayList<>();
        vouchers.add(new Voucher("$30 OFF", "WITH MINIMUM SPENDING OF $99", 30, 100, 0, 30));
        vouchers.add(new Voucher("$2 OFF", "WITH MINIMUM SPENDING OF $15", 2, 20, 0, 14));
        vouchers.add(new Voucher("$5 OFF", "WITH MINIMUM SPENDING OF $20", 5, 30, 0, 14));
        vouchers.add(new Voucher("$4 OFF", "WITH MINIMUM SPENDING OF $30", 4, 25, 0, 14));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MY_LOYALTY_VH_Key, new Gson().toJson(vouchers));
        editor.commit();
    }

    public ArrayList<Voucher> getStoreVouchers() {
        Type type = new TypeToken<ArrayList<Voucher>>(){}.getType();
        return new Gson().fromJson(sharedPreferences.getString(STORE_VOUCHERS_KEY, null), type);
    }

    public ArrayList<MyVoucher> getMyVouchers() {
        Type type = new TypeToken<ArrayList<MyVoucher>>(){}.getType();
        return new Gson().fromJson(sharedPreferences.getString(MY_VOUCHERS_KEY, null), type);
    }

    public int getMyLP() {
        return sharedPreferences.getInt(MY_LP_KEY, 0);
    }

    public ArrayList<MyLoyaltyPoints> getMyLoyaltyPoints() {
        Type type = new TypeToken<ArrayList<MyLoyaltyPoints>>(){}.getType();
        return new Gson().fromJson(sharedPreferences.getString(MY_LOYALTY_KEY, null), type);
    }

    public ArrayList<Voucher> getLoyaltyVouchers() {
        Type type = new TypeToken<ArrayList<Voucher>>(){}.getType();
        return new Gson().fromJson(sharedPreferences.getString(MY_LOYALTY_VH_Key, null), type);
    }

    // To use cloud database instead
    public boolean addToMyVouchers(MyVoucher voucher) {
        ArrayList<MyVoucher> vouchers = getMyVouchers();
        if (vouchers != null) {
            if (vouchers.add(voucher)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(MY_VOUCHERS_KEY);
                editor.putString(MY_VOUCHERS_KEY, new Gson().toJson(vouchers));
                editor.commit();
                return true;
            }
        }

        return false;
    }

    // To use cloud database instead
    public boolean removeFromMyVouchers(MyVoucher voucher) {
        ArrayList<MyVoucher> vouchers = getMyVouchers();
        if (vouchers != null) {
            for (MyVoucher v : vouchers) {
                if (v.getId().equals(voucher.getId())) {
                    if (vouchers.remove(v)) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(MY_VOUCHERS_KEY);
                        editor.putString(MY_VOUCHERS_KEY, new Gson().toJson(vouchers));
                        editor.commit();
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void addLP(int points) {
        int currentPoints = getMyLP();
        currentPoints += points;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(MY_LP_KEY, currentPoints);
        editor.commit();
    }

    public boolean removeLP(int points) {
        int currentPoints = getMyLP();

        if (currentPoints >= points) {
            currentPoints -= points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(MY_LP_KEY, currentPoints);
            editor.commit();
            return true;
        } else {
            return false;
        }
    }

    public boolean addToMyLoyalty(MyLoyaltyPoints voucher) {
        ArrayList<MyLoyaltyPoints> loyalty = getMyLoyaltyPoints();
        if (loyalty != null) {
            if (loyalty.add(voucher)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(MY_LOYALTY_KEY);
                editor.putString(MY_LOYALTY_KEY, new Gson().toJson(loyalty));
                editor.commit();
                return true;
            }
        }
        return false;
    }
}
