package com.dealsdelta.scheduleme.dtos;


import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 25/06/22
 */

public class MonthlyJob extends RepeatableJob {
    private int dayOfMonth;

    @Override
    public boolean isRepeatable() {
        return true;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }
}