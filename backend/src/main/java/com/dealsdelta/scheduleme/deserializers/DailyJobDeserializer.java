package com.dealsdelta.scheduleme.deserializers;


import com.dealsdelta.scheduleme.dtos.DailyJob;
import com.dealsdelta.scheduleme.dtos.IJob;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

import java.io.IOException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 03/07/22
 */

public class DailyJobDeserializer extends StdDeserializer<DailyJob> {

    public DailyJobDeserializer() {
        this(null);
    }

    protected DailyJobDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public DailyJob deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        DailyJob job = new DailyJob();
        job.setJobKind(IJob.JOB_KIND.valueOf(node.get("jobKind").asText()));
        if(node.has("status")) {
            job.setStatus(node.get("status").asText());
        }
        if(node.has("jobParams")) {
            job.setJobParams(parseIntoMap(node.get("jobParams")));
        }
        job.setName(node.get("name").asText());
        job.setUrgent(node.get("urgent").asBoolean());
        String startTime = node.get("startTime").asText();
        LocalTime localTime = LocalTime.parse(startTime);
        job.setStartTime(localTime);
        return job;
    }

    public Map<String, Object> parseIntoMap(JsonNode jsonNode) {
        Map<String, Object> data = new HashMap<>();
        Iterator<String> paramIterator = jsonNode.fieldNames();
        while (paramIterator.hasNext()) {
            String key = paramIterator.next();
            if(jsonNode.get(key).isPojo()) {
                data.put(key, parseIntoMap(jsonNode.get(key)));
            } else {
                data.put(key, jsonNode.get(key).asText());
            }
        }
        return data;
    }

}