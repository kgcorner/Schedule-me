package com.dealsdelta.scheduleme.dtos;


import com.dealsdelta.scheduleme.deserializers.DailyJobDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 24/06/22
 */
public class DailyJob extends RepeatableJob {

    @Override
    public boolean isRepeatable() {
        return true;
    }
}