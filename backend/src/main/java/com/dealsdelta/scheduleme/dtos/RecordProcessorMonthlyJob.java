package com.dealsdelta.scheduleme.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 25/06/22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecordProcessorMonthlyJob extends MonthlyJob {
    private int recordCount;
    private int recordProcessed;
}