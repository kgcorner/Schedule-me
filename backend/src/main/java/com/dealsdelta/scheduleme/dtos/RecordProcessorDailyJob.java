package com.dealsdelta.scheduleme.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 24/06/22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordProcessorDailyJob  extends DailyJob {
    private int recordCount;
    private int recordProcessed;
}