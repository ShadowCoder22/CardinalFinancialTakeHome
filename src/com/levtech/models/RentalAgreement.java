package com.levtech.models;

import com.levtech.util.Utils;

import java.text.DecimalFormat;
import java.time.LocalDate;

public class RentalAgreement {
    private String toolCode;
    private String toolType;
    private String toolBrand;
    private int duration;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private double dailyCharge;
    private int chargeDays;
    private double preDiscountCharge;
    private int discountPercent;
    private double discountAmount;
    private double finalCharge;

    public RentalAgreement(String toolCode, String toolType, String toolBrand, int duration, LocalDate checkoutDate, double dailyCharge, int chargeDays, int discountPercent) {
        DecimalFormat df = new DecimalFormat("#.##");

        this.toolCode = toolCode;
        this.toolType = toolType;
        this.toolBrand = toolBrand;
        this.duration = duration;
        this.checkoutDate = checkoutDate;
        this.dueDate = this.checkoutDate.plusDays(duration);
        this.dailyCharge = dailyCharge;
        this.chargeDays = chargeDays;
        this.preDiscountCharge = this.chargeDays * this.dailyCharge;
        this.discountPercent = discountPercent;
        this.discountAmount = Double.parseDouble(df.format(this.preDiscountCharge * (this.discountPercent / 100.0)));
        this.finalCharge = Double.parseDouble(df.format(this.preDiscountCharge - this.discountAmount));
    }

    public String getToolCode() {
        return toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public String getToolBrand() {
        return toolBrand;
    }

    public int getDuration() {
        return duration;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public double getDailyCharge() {
        return dailyCharge;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public double getPreDiscountCharge() {
        return preDiscountCharge;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getFinalCharge() {
        return finalCharge;
    }

    public String toString() {
        return "Rental Details:\n"+
                "\tCode: "+this.toolCode+"\n"+
                "\tBrand: "+this.toolBrand+"\n"+
                "\tType: "+this.toolType+"\n"+
                "\tCheck out on: "+this.checkoutDate.format(Utils.getDateFormat())+"\n"+
                "\tDue back on: "+this.dueDate.format(Utils.getDateFormat())+"\n"+
                "\tLength of rental: "+this.duration+"\n"+
                "\tChargeable days: "+this.chargeDays+"\n"+
                "\tDaily rate: $"+this.dailyCharge+"\n"+
                "\tPercentage discounted: "+this.discountPercent+"%\n"+
                "\tCharge: $"+this.preDiscountCharge+"\n"+
                "\tDiscount amount: $"+this.discountAmount+"\n"+
                "\tFinal charge: $"+this.finalCharge+"\n";
    }
}
