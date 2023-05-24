package com.levtech.models;

public class PriceData {
    private String toolType;
    private double dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    public PriceData(String toolType, double dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.toolType = toolType;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    public String getToolType() {
        return toolType;
    }

    public double getDailyCharge() {
        return dailyCharge;
    }

    public boolean hasWeekdayCharge() {
        return weekdayCharge;
    }

    public boolean hasWeekendCharge() {
        return weekendCharge;
    }

    public boolean hasHolidayCharge() {
        return holidayCharge;
    }
}
