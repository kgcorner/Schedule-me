package com.dealsdelta.scheduleme.dtos;


import lombok.Data;

import java.util.Date;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 06/07/22
 */

@Data
public class JobRunStat {
    private Date date;
    private long count;
}