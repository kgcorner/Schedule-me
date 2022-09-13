package com.dealsdelta.scheduleme.processors;

import com.dealsdelta.scheduleme.dtos.JOB_KIND;
import com.dealsdelta.scheduleme.dtos.Job;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/22
 */

public class JobProcessorFactoryTest {


    @Test
    public void getJobProcessor() {
        JobProcessorFactory jobProcessorFactory = new JobProcessorFactory();
        Job mailJob = new Job();
        mailJob.setJobKind(JOB_KIND.MAIL_SENDER);

        Job instaJob = new Job();
        instaJob.setJobKind(JOB_KIND.INSTAGRAM_POST_CREATOR);

        Job fbJob = new Job();
        fbJob.setJobKind(JOB_KIND.FACEBOOK_POST_CREATOR);
        JobProcessor mailJobProcessor = JobProcessorFactory.getJobProcessor(mailJob);
        JobProcessor instaJobProcessor = JobProcessorFactory.getJobProcessor(instaJob);
        JobProcessor fbJobProcessor = JobProcessorFactory.getJobProcessor(fbJob);
        //Should return correct type of JobProcessor
        assertEquals(FacebookJobProcessor.class, fbJobProcessor.getClass());
        assertEquals(InstagramJobProcessor.class, instaJobProcessor.getClass());
        assertEquals(MailJobProcessor.class, mailJobProcessor.getClass());

        //Should return new instance of processor always
        JobProcessor newMailJobProcessor = JobProcessorFactory.getJobProcessor(mailJob);
        JobProcessor newInstaJobProcessor = JobProcessorFactory.getJobProcessor(instaJob);
        JobProcessor newFbJobProcessor = JobProcessorFactory.getJobProcessor(fbJob);
        assertNotEquals(mailJobProcessor, newMailJobProcessor);
        assertNotEquals(instaJobProcessor, newInstaJobProcessor);
        assertNotEquals(fbJobProcessor, newFbJobProcessor);
    }
}