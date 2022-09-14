package com.dealsdelta.scheduleme.data.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/22
 */

public class JobAuditModelTest {

    @Test
    public void idTest() {
        String id = "id";
        JobAuditModel model = new JobAuditModel();
        model.setAuditId(id);
        assertEquals(id, model.getAuditId());
    }
}