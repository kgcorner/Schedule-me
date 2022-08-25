package com.dealsdelta.scheduleme.data.dao;

import com.dealsdelta.scheduleme.data.models.JobModel;
import com.dealsdelta.scheduleme.data.repo.JobRepo;
import com.dealsdelta.scheduleme.data.repo.Operation;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 25/08/22
 */

class GenericDaoTest {
    private GenericDao<JobModel> jobModelDao;
    private JobRepo<JobModel> jobRepo;
    private static final String RANDOM_JOB_ID = "asjkdalsjdkjaskldjajskljda";
    private static final String RANDOM_JOB_NAME = "NAME";
    @BeforeEach
    void setUp() {
        jobModelDao = new GenericDao<>();
        jobRepo = PowerMockito.mock(JobRepo.class);
        Whitebox.setInternalState(jobModelDao, "jobRepo", jobRepo);
    }

    @Test
    void create() {
        JobModel model = new JobModel();
        JobModel createdModel = new JobModel();
        createdModel.setJobId(RANDOM_JOB_ID);
        when(jobRepo.create(model)).thenReturn(createdModel);
        JobModel result = jobModelDao.create(model);
        assertEquals(RANDOM_JOB_ID, result.getJobId());
    }

    @Test
    void update() {
        JobModel model = new JobModel();
        JobModel createdModel = new JobModel();
        createdModel.setJobId(RANDOM_JOB_ID);
        when(jobRepo.update(model)).thenReturn(createdModel);
        JobModel result = jobModelDao.update(model);
        assertEquals(RANDOM_JOB_ID, result.getJobId());
    }

    @Test
    void delete() {
        JobModel model = new JobModel();
        doNothing().when(jobRepo).remove(model);
        jobModelDao.delete(model);
    }

    @Test
    void getAll() {
        List<JobModel> models = new ArrayList<>();
        models.add(new JobModel());
        models.add(new JobModel());
        when(jobRepo.getAll(JobModel.class)).thenReturn(models);
        List<JobModel> result = jobModelDao.getAll(JobModel.class);
        assertEquals(models, result);
    }

    @Test
    void get() {
        JobModel model = new JobModel();
        model.setJobId(RANDOM_JOB_ID);
        when(jobRepo.getById(RANDOM_JOB_ID, JobModel.class)).thenReturn(model);
        JobModel result = jobModelDao.get(model.getJobId(), JobModel.class);
        assertEquals(RANDOM_JOB_ID, result.getJobId());
    }

    @Test
    void getByKey() {
        JobModel model = new JobModel();
        model.setJobName(RANDOM_JOB_NAME);
        when(jobRepo.getByKey("name", RANDOM_JOB_NAME, JobModel.class)).thenReturn(model);
        JobModel result = jobModelDao.getByKey("name", model.getJobName(), JobModel.class);
        assertEquals(RANDOM_JOB_NAME, result.getJobName());
    }

    @Test
    void getAllByKey() {
        List<JobModel> models = new ArrayList<>();
        models.add(new JobModel());
        models.add(new JobModel());
        List<Operation> operations = new ArrayList<>();
        Operation op = new Operation(RANDOM_JOB_NAME, Operation.TYPES.STRING, "name", Operation.OPERATORS.EQ);
        operations.add(op);
        when(jobRepo.getAll(operations, 0, Integer.MAX_VALUE, JobModel.class)).thenReturn(models);
        List<JobModel> result = jobModelDao.getAllByKey(operations, JobModel.class);
        assertEquals(models, result);
    }

    @Test
    void updateMany() {
        List<Operation> operations = new ArrayList<>();
        Operation op = new Operation(RANDOM_JOB_NAME, Operation.TYPES.STRING, "name", Operation.OPERATORS.EQ);
        operations.add(op);
        doNothing().when(jobRepo).updateMany(operations, "name", "new Name", JobModel.class);
        jobModelDao.updateMany(operations, "name","new name", JobModel.class);
    }

    @Test
    void getCount() {
        long totalCount = 10;
        when(jobRepo.getCount(JobModel.class)).thenReturn(totalCount);
        long result = jobModelDao.getCount(JobModel.class);
        assertEquals(totalCount, result);
    }
}