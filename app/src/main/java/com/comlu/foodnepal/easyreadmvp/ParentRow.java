package com.comlu.foodnepal.easyreadmvp;

import java.util.ArrayList;

public class ParentRow {

    private String name;
    private String priceText;
    private ArrayList<ChildRow> childList;

    public ParentRow(String name, String priceText, ArrayList<ChildRow> childList) {
        this.name = name;
        this.priceText = priceText;
        this.childList = childList;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPriceText() {
        return priceText;
    }
    public void setPriceText(String priceText) {
        this.priceText = priceText;
    }


    public ArrayList<ChildRow> getChildList() {
        return childList;
    }
    public void setChildList(ArrayList<ChildRow> childList) {
        this.childList = childList;
    }
}
