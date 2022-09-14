package com.dealsdelta.scheduleme.config;

import com.dealsdelta.scheduleme.executors.JobProcessorExecutorService;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/22
 */

public class ApplicationEventListenerTest {

    @Test
    public void onApplicationEvent() {
        ApplicationEventListener listener = new ApplicationEventListener();
        JobProcessorExecutorService service = PowerMockito.mock(JobProcessorExecutorService.class);
        Whitebox.setInternalState(listener, "jobProcessorExecutorService", service);
        listener.onApplicationEvent(null);
        Mockito.verify(service, Mockito.times(1)).start();
    }
}