package com.dealsdelta.scheduleme.services;

import com.dealsdelta.scheduleme.dtos.Job;
import com.dealsdelta.scheduleme.dtos.RUN_FREQUENCY;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 23/08/22
 */


class JobServiceTest {

    private Job job;
    private JobService jobService;

    @BeforeEach
    void setUp() {
        job = new Job();
        jobService = new JobService();
    }

    @Test
    void testNoStartTimeJob() {
        try {
            jobService.validateJob(job);
            Assertions.fail("Job with no start time is processed");
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "job must have a start Time", x.getMessage());
        }
    }

    @Test
    void testNoJobFrequency() {
        try {
            job.setStartTime(LocalTime.now());
            jobService.validateJob(job);
            Assertions.fail("Job with no start time is processed");
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "job must have a job frequency", x.getMessage());
        }
    }

    @Test
    void validateNoDateJob() {
        job.setFrequency(RUN_FREQUENCY.ONCE);
        job.setStartTime(LocalTime.now());
        job.setDayOfMonth(10);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Non Repeatable jobs or daily job or job to run on last day of month" +
                " can't have Day of month, " +
                "days of week, days of month or days of week", x.getMessage());
        }
        job.setFrequency(RUN_FREQUENCY.LAST_DAY_OF_MONTH);
        job.setDayOfMonth(0);
        job.setDayOfWeek(2);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Non Repeatable jobs or daily job or job to run on last day of month" +
                " can't have Day of month, " +
                "days of week, days of month or days of week", x.getMessage());
        }
        job.setFrequency(RUN_FREQUENCY.DAILY);
        job.setDayOfMonth(0);
        job.setDayOfWeek(0);
        int[] daysArray = {4,5};
        job.setDaysInMonth(daysArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Non Repeatable jobs or daily job or job to run on last day of month" +
                " can't have Day of month, " +
                "days of week, days of month or days of week", x.getMessage());
        }

        job.setDayOfMonth(0);
        job.setDayOfWeek(0);
        int[] emptyArray = {};
        job.setDaysInMonth(emptyArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Non Repeatable jobs or daily job or job to run on last day of month" +
                " can't have Day of month, " +
                "days of week, days of month or days of week", x.getMessage());
        }

        job.setDayOfMonth(0);
        job.setDayOfWeek(0);
        job.setDaysInMonth(null);
        job.setDaysOfWeek(daysArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Non Repeatable jobs or daily job or job to run on last day of month" +
                " can't have Day of month, " +
                "days of week, days of month or days of week", x.getMessage());
        }

        job.setDayOfMonth(0);
        job.setDayOfWeek(0);
        job.setDaysInMonth(null);
        job.setDaysOfWeek(emptyArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Non Repeatable jobs or daily job or job to run on last day of month" +
                " can't have Day of month, " +
                "days of week, days of month or days of week", x.getMessage());
        }
    }

    @Test
    void validateWeeklyJob() {
        job.setFrequency(RUN_FREQUENCY.WEEKLY);
        job.setStartTime(LocalTime.now());
        job.setDayOfMonth(10);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Weekly jobs can't have Day of month " +
                ", days of month or days of week", x.getMessage());
        }
        int[] array = {9,2};
        int[] emptyArray = {};
        job.setDayOfMonth(0);
        job.setDaysInMonth(array);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Weekly jobs can't have Day of month " +
                ", days of month or days of week", x.getMessage());
        }
        job.setDaysInMonth(emptyArray);
        job.setDaysOfWeek(array);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Weekly jobs can't have Day of month " +
                ", days of month or days of week", x.getMessage());
        }


        job.setDaysOfWeek(emptyArray);
        job.setDaysInMonth(emptyArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Weekly jobs must have day of week between 1-7", x.getMessage());
        }
        job.setDayOfWeek(0);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Weekly jobs must have day of week between 1-7", x.getMessage());
        }
        job.setDayOfWeek(10);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Weekly jobs must have day of week between 1-7", x.getMessage());
        }
        job.setDaysOfWeek(null);
        job.setDaysInMonth(null);
        job.setDayOfWeek(5);
        jobService.validateJob(job);
    }

    @Test
    void validateN_WeeklyJob() {
        job.setFrequency(RUN_FREQUENCY.N_WEEKLY);
        job.setStartTime(LocalTime.now());
        job.setDayOfMonth(10);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals("N_Weekly jobs can't have Day of month " +
                ",Day of week or days of month", x.getMessage());
        }
        int[] array = {5,2};
        int[] emptyArray = {};
        job.setDayOfMonth(0);
        job.setDaysInMonth(array);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "N_Weekly jobs can't have Day of month " +
                ",Day of week or days of month", x.getMessage());
        }
        job.setDaysInMonth(emptyArray);
        job.setDayOfWeek(10);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "N_Weekly jobs can't have Day of month " +
                ",Day of week or days of month", x.getMessage());
        }

        job.setDaysInMonth(emptyArray);
        job.setDayOfWeek(0);
        job.setDaysOfWeek(emptyArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "N_Weekly jobs must have days of week",x.getMessage());
        }
        int[] invalidArray = {9, 5};
        job.setDayOfWeek(0);
        job.setDaysOfWeek(invalidArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "N_Weekly jobs must have days of week between 1-7", x.getMessage());
        }

        int[] invalidArray2 = {0, 5};
        job.setDayOfWeek(0);
        job.setDaysOfWeek(invalidArray2);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "N_Weekly jobs must have days of week between 1-7", x.getMessage());
        }
        job.setDaysInMonth(null);
        job.setDaysOfWeek(array);
        jobService.validateJob(job);
    }


    @Test
    void validateN_MonthlyJob() {
        job.setFrequency(RUN_FREQUENCY.N_MONTHLY);
        job.setStartTime(LocalTime.now());
        int[] array = {9,2};
        int[] emptyArray = {};
        job.setDaysOfWeek(array);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "N_Monthly jobs Running more than once can't have Day of Week " +
                ",Day of week or day of month", x.getMessage());
        }
        job.setDaysOfWeek(emptyArray);
        job.setDayOfMonth(10);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals("N_Monthly jobs Running more than once can't have Day of Week " +
                ",Day of week or day of month", x.getMessage());
        }
        job.setDayOfMonth(0);
        job.setDaysOfWeek(emptyArray);
        job.setDayOfWeek(10);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "N_Monthly jobs Running more than once can't have Day of Week " +
                ",Day of week or day of month", x.getMessage());
        }

        job.setDaysInMonth(emptyArray);
        job.setDayOfWeek(0);
        job.setDaysOfWeek(emptyArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "N_Monthly jobs must have days in Month",x.getMessage());
        }
        int[] invalidArray = {69, 5};
        job.setDayOfWeek(0);
        job.setDaysInMonth(invalidArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "N_Monthly jobs must have days of week between 1-31", x.getMessage());
        }

        int[] invalidArray2 = {0, 5};
        job.setDayOfWeek(0);
        job.setDaysInMonth(invalidArray2);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "N_Monthly jobs must have days of week between 1-31", x.getMessage());
        }
        job.setDaysOfWeek(null);
        job.setDaysInMonth(array);
        jobService.validateJob(job);
    }

    @Test
    void validateMonthlyJob() {
        job.setFrequency(RUN_FREQUENCY.MONTHLY);
        job.setStartTime(LocalTime.now());
        int[] array = {9,2};
        int[] emptyArray = {};
        job.setDaysOfWeek(array);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals("Monthly jobs can't have Day of Week " +
                ",Day of week or days of month", x.getMessage());
        }

        job.setDaysOfWeek(emptyArray);
        job.setDaysInMonth(array);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Monthly jobs can't have Day of Week " +
                ",Day of week or days of month", x.getMessage());
        }
        job.setDaysInMonth(emptyArray);
        job.setDayOfWeek(5);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals("Monthly jobs can't have Day of Week " +
                ",Day of week or days of month", x.getMessage());
        }
        job.setDaysInMonth(emptyArray);
        job.setDayOfWeek(0);
        job.setDaysOfWeek(emptyArray);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Monthly jobs must have day of Month between 1-31",x.getMessage());
        }
        job.setDayOfWeek(0);
        job.setDayOfMonth(69);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Monthly jobs must have day of Month between 1-31", x.getMessage());
        }

        job.setDayOfWeek(0);
        job.setDayOfMonth(0);
        try {
            jobService.validateJob(job);
        } catch (IllegalArgumentException x) {
            Assertions.assertEquals( "Monthly jobs must have day of Month between 1-31", x.getMessage());
        }
        job.setDaysInMonth(null);
        job.setDaysOfWeek(null);
        job.setDayOfMonth(10);
        jobService.validateJob(job);
    }
}