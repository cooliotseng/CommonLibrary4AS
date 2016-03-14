package com.example.dragrelativelayout.constants;

/**
 * @author: vision
 * @function:
 * @date: 16/3/11
 */
public enum UmengEvent {

    ONLOGIN("onLogin");

    private String mValue; //事件ID

    UmengEvent(String value) {

        this.mValue = value;
    }

    public String getValue() {

        return this.mValue;
    }
}
