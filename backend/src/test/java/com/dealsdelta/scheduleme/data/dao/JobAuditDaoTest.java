package com.dealsdelta.scheduleme.data.dao;

import com.dealsdelta.scheduleme.data.models.JobAuditModel;
import com.dealsdelta.scheduleme.data.repo.JobRepo;
import com.dealsdelta.scheduleme.dtos.JobRunStat;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 14/09/22
 */

public class JobAuditDaoTest {

    private JobAuditDao dao;
    private JobRepo<JobAuditModel> jobRepo;

    @Before
    public void setUp() throws Exception {
        dao = new JobAuditDao();
        jobRepo = mock(JobRepo.class);
        Whitebox.setInternalState(dao, "jobRepo", jobRepo);;
    }

    @Test
    public void getJobRunByDay() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date from = sdf.parse("2022-12-12");
        Date to = sdf.parse("2022-12-30");

        String query = "{ 'aggregate': 'jobAuditModel', 'pipeline': [\n" +
            "  {\n" +
            "    $match : { \"startTime\": { $gt: new ISODate(\"2022-12-12\"), $lte: new ISODate(\"2022-12-30\") }}\n" +
            "  },\n" +
            "  {\n" +
            "    $group : {\n" +
            "       _id : { $dateToString: { format: \"%Y-%m-%d\", date: \"$startTime\" } },\n" +
            "       count: { $sum: 1 }\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    $sort : { _id: 1 }\n" +
            "  }\n" +
            " ], 'cursor': { } }";
        List<Document> data = new ArrayList<>();
        int count = 10;
        for (int i = 0; i < count; i++) {
            Document d = mock(Document.class);
            when(d.get("_id")).thenReturn("2022-12-" + (i+12));
            when(d.get("count")).thenReturn(i);
            data.add(d);
        }
        when(jobRepo.runSelectNativeQuery(query)).thenReturn(data);
        List<JobRunStat> jobRunByDay = dao.getJobRunByDay(from, to);
        for (int i = 0; i < count; i++) {
            Date dt = sdf.parse("2022-12-" + (i+12));
            JobRunStat stat = jobRunByDay.get(i);
            assertEquals(sdf.format(dt), sdf.format(stat.getDate()));
            assertEquals(stat.getCount(), i);
        }
    }

    @Test
    public void getJobFailedByDay() throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date from = sdf.parse("2022-12-12");
        Date to = sdf.parse("2022-12-30");
        String query = "{ 'aggregate': 'jobAuditModel', 'pipeline': [\n" +
            "  {\n" +
            "    $match : { \"startTime\": { $gt: new ISODate(\"2022-12-12\"), $lte: new ISODate(\"2022-12-30\") }, \"status\": {$eq : \"FAILED\"}  }\n" +
            "  },\n" +
            "  {\n" +
            "    $group : {\n" +
            "       _id : { $dateToString: { format: \"%Y-%m-%d\", date: \"$startTime\" } },\n" +
            "       count: { $sum: 1 }\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    $sort : { _id: 1 }\n" +
            "  }\n" +
            " ], 'cursor': { } }";
        List<Document> data = new ArrayList<>();
        int count = 10;
        for (int i = 0; i < count; i++) {
            Document d = mock(Document.class);
            when(d.get("_id")).thenReturn("2022-12-" + (i+12));
            when(d.get("count")).thenReturn(i);
            data.add(d);
        }
        when(jobRepo.runSelectNativeQuery(query)).thenReturn(data);
        List<JobRunStat> jobRunByDay = dao.getJobFailedByDay(from, to);
        for (int i = 0; i < count; i++) {
            Date dt = sdf.parse("2022-12-" + (i+12));
            JobRunStat stat = jobRunByDay.get(i);
            assertEquals(sdf.format(dt), sdf.format(stat.getDate()));
            assertEquals(stat.getCount(), i);
        }
    }
}