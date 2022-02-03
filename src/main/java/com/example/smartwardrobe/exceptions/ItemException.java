package com.example.smartwardrobe.exceptions;

public class ItemException extends Exception{
    private String code;
    public ItemException(String code) {
        super("You need to buy more " + code );
        this.setCode(code);
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
