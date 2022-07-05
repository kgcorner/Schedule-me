package com.dealsdelta.scheduleme.dtos;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 24/06/22
 */

public class HourlyJob extends RepeatableJob {
    private int repeatFrequencyInMinutes;

    public int getRepeatFrequencyInMinutes() {
        return repeatFrequencyInMinutes;
    }

    public void setRepeatFrequencyInMinutes(int repeatFrequencyInMinutes) {
        this.repeatFrequencyInMinutes = repeatFrequencyInMinutes;
    }
}