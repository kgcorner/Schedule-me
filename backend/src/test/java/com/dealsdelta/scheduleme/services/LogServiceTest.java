package com.dealsdelta.scheduleme.services;

import com.dealsdelta.scheduleme.data.dao.LogDao;
import com.dealsdelta.scheduleme.data.models.LogModel;
import com.dealsdelta.scheduleme.dtos.Job;
import com.dealsdelta.scheduleme.dtos.Log;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 06/09/22
 */

public class LogServiceTest  {
    @Test
    public void testWrite() {
        LogService logService = new LogService();
        LogDao logDao = PowerMockito.mock(LogDao.class);
        Whitebox.setInternalState(logService, "logDao", logDao);
        Job job = new Job();
        logService.write(job, Log.INFO, "This is test message", LogServiceTest.class, "Run ID");

        Mockito.verify(logDao, Mockito.times(1)).create(ArgumentMatchers.any(LogModel.class));
    }
}
