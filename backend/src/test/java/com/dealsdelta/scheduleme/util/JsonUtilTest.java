package com.dealsdelta.scheduleme.util;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/22
 */

public class JsonUtilTest {

    @Test
    public void getString() {
        JsonUtil jsonUtil = new JsonUtil();
        assertNotNull(jsonUtil);
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        String jsonStr = new Gson().toJson(map);
        assertEquals(jsonStr, JsonUtil.getString(map));
    }
}