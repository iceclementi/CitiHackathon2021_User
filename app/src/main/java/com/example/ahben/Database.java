package com.example.ahben;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Database {
    private static final String STORE_VOUCHERS_KEY = "STORE_VOUCHERS";
    private static final String MY_VOUCHERS_KEY = "MY_VOUCHERS";

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
        vouchers.add(new Voucher("$5 OFF", "WITH MINIMUM SPENDING OF $50", 5, 1, 30));
        vouchers.add(new Voucher("$10 OFF", "WITH MINIMUM SPENDING OF $100", 10, 2, 30));
        vouchers.add(new Voucher("$20 OFF", "WITH MINIMUM SPENDING OF $200", 20, 5, 30));
        vouchers.add(new Voucher("$50 OFF", "WITH MINIMUM SPENDING OF $500", 50, 20, 30));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(STORE_VOUCHERS_KEY, new Gson().toJson(vouchers));
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
}
