package com.example.jinkejinfuapplication.utils;

/**
 * Created by Administrator on 2016/11/30.
 */
public class TextBean
{
    private boolean isSelected=false;
    private String name;
    private String text1;
    public TextBean(String name) {
        this.name = name;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }
    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
