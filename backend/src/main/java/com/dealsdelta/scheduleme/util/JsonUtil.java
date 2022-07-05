package com.dealsdelta.scheduleme.util;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 27/12/21
 */

public class JsonUtil {
    public static String getString(Object object) {
        Gson gson = new GsonBuilder().setDateFormat("MMM dd, yyyy hh:mm:ss").create();
        return gson.toJson(object);
    }
}