package com.comlu.foodnepal.easyreadmvp;

public class ChildRow {

    private String text;
    private String text2;
    private String text3;
    private String text4;
    private String text5;


    public ChildRow(String text, String text2, String text3, String text4, String text5) {
        this.text = text;
        this.text2 = text2;
        this.text3 = text3;
        this.text4 = text4;
        this.text5 = text5;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getText2() {
        return text2;
    }
    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }
    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getText4() {
        return text4;
    }
    public void setText4(String text4) {
        this.text4 = text4;
    }

    public String getText5() {
        return text5;
    }
    public void setText5(String text5) {
        this.text5 = text5;
    }
}
