package com.levtech.models;

public class Tool {
    private String code;
    private String type;
    private String brand;

    public Tool(String code, String type, String brand) {
        this.code = code;
        this.type = type;
        this.brand = brand;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getBrand() {
        return brand;
    }
}
