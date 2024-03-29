package com.dealsdelta.scheduleme.data.dao;


import com.dealsdelta.scheduleme.data.models.JobAuditModel;
import com.dealsdelta.scheduleme.data.models.JobAuditModel;
import com.dealsdelta.scheduleme.data.repo.JobRepo;
import com.dealsdelta.scheduleme.data.repo.Operation;
import com.dealsdelta.scheduleme.dtos.JobRunStat;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 06/07/22
 */
@Repository
@Transactional
public class JobAuditDao extends GenericDao<JobAuditModel> {


    public List<JobRunStat> getJobRunByDay(Date beforeDate, Date nowDate) {
        String query = "[\n" +
            "  {\n" +
            "    $match : { \"startTime\": { $gt: new ISODate(\"{1}\"), $lte: new ISODate(\"{2}\") }}\n" +
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
            " ]";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String before = sdf.format(beforeDate);
        String now = sdf.format(nowDate);
        query = query.replace("{1}", before).replace("{2}", now);
        String aggregateTemplate = "{ 'aggregate': 'jobAuditModel', 'pipeline': %s, 'cursor': { } }";
        aggregateTemplate = String.format(aggregateTemplate, query);
        Object data = jobRepo.runSelectNativeQuery(aggregateTemplate);
        List<Document> records = (List<Document>) data;
        List<JobRunStat> result = new ArrayList<>();
        for(Document record : records) {
            try {
                JobRunStat stat = new JobRunStat();
                Date dt = sdf.parse(record.get("_id").toString());
                stat.setDate(dt);
                stat.setCount(Long.parseLong(record.get("count").toString()));
                result.add(stat);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public List<JobRunStat> getJobFailedByDay(Date beforeDate, Date nowDate) {
        String query = "[\n" +
            "  {\n" +
            "    $match : { \"startTime\": { $gt: new ISODate(\"{1}\"), $lte: new ISODate(\"{2}\") }, \"status\": {$eq : \"FAILED\"}  }\n" +
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
            " ]";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String before = sdf.format(beforeDate);
        String now = sdf.format(nowDate);
        query = query.replace("{1}", before).replace("{2}", now);
        String aggregateTemplate = "{ 'aggregate': 'jobAuditModel', 'pipeline': %s, 'cursor': { } }";
        aggregateTemplate = String.format(aggregateTemplate, query);
        Object data = jobRepo.runSelectNativeQuery(aggregateTemplate);
        List<Document> records = (List<Document>) data;
        List<JobRunStat> result = new ArrayList<>();
        for(Document record : records) {
            try {
                JobRunStat stat = new JobRunStat();
                Date dt = sdf.parse(record.get("_id").toString());
                stat.setDate(dt);
                stat.setCount(Long.parseLong(record.get("count").toString()));
                result.add(stat);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        return result;
    }
}