package com.dealsdelta.scheduleme.data.repo;

import com.dealsdelta.scheduleme.data.models.LogModel;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 13/09/22
 */

public class MongoRepositoryTest {

    private MongoTemplate mockedMongoTemplate;
    private MongoRepository<DummyModel> repository;

    @Before
    public void setUp() throws Exception {
        mockedMongoTemplate = mock(MongoTemplate.class);
        repository = new DummyMongoRepository(mockedMongoTemplate);
    }

    @Test
    public void getAll() {
        List list = prepareDummyList(9);
        when(mockedMongoTemplate.find(any(), any())).thenReturn(list);
        List response = repository.getAll(1, DummyModel.class);
        assertNotNull("Response is null", response);
        assertEquals("Lists are not euqal", list, response);
    }

    @Test
    public void getById() {
        DummyModel model = new DummyModel("1");
        when(mockedMongoTemplate.findOne(any(), any())).thenReturn(model);
        DummyModel response = repository.getById("id", DummyModel.class);
        assertNotNull("Response is null", response);
        assertEquals("model's id is not matching", "1", response.getId());
    }

    @Test
    public void getByKey() {
        DummyModel model = new DummyModel("1");
        when(mockedMongoTemplate.findOne(any(), any())).thenReturn(model);
        DummyModel response = repository.getByKey("id", "1", DummyModel.class);
        assertNotNull("Response is null", response);
        assertEquals("model's id is not matching", "1", response.getId());
    }

    @Test
    public void create() {
        DummyModel model = new DummyModel("1");
        when(mockedMongoTemplate.insert(any(DummyModel.class))).thenReturn(model);
        DummyModel response = repository.create(model);
        assertNotNull("Response is null", response);
        assertEquals("model's id is not matching", "1", response.getId());
    }

    @Test
    public void update() {
        DummyModel model = new DummyModel("1");
        when(mockedMongoTemplate.save(any(DummyModel.class))).thenReturn(model);
        DummyModel response = repository.update(model);
        assertNotNull("Response is null", response);
        assertEquals("model's id is not matching", "1", response.getId());
    }

    @Test
    public void remove() {
        DummyModel model = new DummyModel("1");
        when(mockedMongoTemplate.remove(model)).thenReturn(null);
        repository.remove(model);
        assertNotNull("Response is null", model);
        assertEquals("model's id is not matching", "1", model.getId());
    }

    @Test
    public void getAllWithCondition() {
        List<Operation> operations = new ArrayList<>();
        Operation operation1 = new Operation("Value", Operation.TYPES.STRING,
            "column1", Operation.OPERATORS.EQ);
        operation1.setOrWithPrevious(true);
        Operation operation2 = new Operation("Value", Operation.TYPES.STRING,
            "column2", Operation.OPERATORS.GE);
        Operation operation3 = new Operation("Value", Operation.TYPES.STRING,
            "column2", Operation.OPERATORS.LE);
        operation3.setOrWithPrevious(true);
        Operation operation4 = new Operation("Value", Operation.TYPES.STRING,
            "column2", Operation.OPERATORS.GT);
        operation4.setOrWithPrevious(true);
        Operation operation5 = new Operation("Value", Operation.TYPES.STRING,
            "column2", Operation.OPERATORS.LT);
        operation5.setOrWithPrevious(true);
        Operation operation6 = new Operation("Value", Operation.TYPES.STRING,
            "column2", Operation.OPERATORS.IS_NULL);
        operation6.setOrWithPrevious(true);
        operation2.setOrWithPrevious(true);
        operations.add(operation1);
        operations.add(operation2);
        operations.add(operation3);
        operations.add(operation4);
        operations.add(operation5);
        operations.add(operation6);
        operations.add(new Operation("Value", Operation.TYPES.STRING,
            "column1", Operation.OPERATORS.EQ));
        operations.add(new Operation("Value", Operation.TYPES.STRING,
            "column1", Operation.OPERATORS.GE));
        operations.add(new Operation("Value", Operation.TYPES.STRING,
            "column1", Operation.OPERATORS.LE));
        operations.add(new Operation("Value", Operation.TYPES.STRING,
            "column1", Operation.OPERATORS.GT));
        operations.add(new Operation("Value", Operation.TYPES.STRING,
            "column1", Operation.OPERATORS.LT));
        int startPage = 0;
        int itemsPerPage = 10;
        repository.getAll(operations, startPage, itemsPerPage, DummyModel.class);
        verify(mockedMongoTemplate).find(any(Query.class),any(Class.class));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllWithConditionWithIsNotNullOperator() {
        List<Operation> operations = new ArrayList<>();
        Operation operation1 = new Operation("Value", Operation.TYPES.STRING,
            "column1", Operation.OPERATORS.EQ);
        Operation operation2 = new Operation("Value", Operation.TYPES.STRING,
            "column2", Operation.OPERATORS.IS_NOT_NULL);
        operation2.setOrWithPrevious(true);
        operations.add(operation1);
        operations.add(operation2);
        int startPage = 0;
        int itemsPerPage = 10;
        repository.getAll(operations, startPage, itemsPerPage, DummyModel.class);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getAllWithConditionWithIsLikeOperator() {
        List<Operation> operations = new ArrayList<>();
        Operation operation1 = new Operation("Value", Operation.TYPES.STRING,
            "column1", Operation.OPERATORS.EQ);
        Operation operation2 = new Operation("Value", Operation.TYPES.STRING,
            "column2", Operation.OPERATORS.LIKE);
        operation2.setOrWithPrevious(true);
        operations.add(operation1);
        operations.add(operation2);
        int startPage = 0;
        int itemsPerPage = 10;
        repository.getAll(operations, startPage, itemsPerPage, DummyModel.class);
    }

    @Test
    public  void getAbsoluteAll() {
        repository.getAll(DummyModel.class);
        verify(mockedMongoTemplate).findAll(DummyModel.class);
    }

    @Test
    public void updateMany() {
        List<Operation> operations = new ArrayList<>();
        Operation operation1 = new Operation("Value", Operation.TYPES.STRING,
            "column1", Operation.OPERATORS.EQ);
        operation1.setOrWithPrevious(true);
        Operation operation2 = new Operation("Value", Operation.TYPES.STRING,
            "column2", Operation.OPERATORS.GE);
        Operation operation3 = new Operation("Value", Operation.TYPES.STRING,
            "column2", Operation.OPERATORS.LE);
        operation3.setOrWithPrevious(true);
        Operation operation4 = new Operation("Value", Operation.TYPES.STRING,
            "column2", Operation.OPERATORS.GT);
        operation4.setOrWithPrevious(true);
        Operation operation5 = new Operation("Value", Operation.TYPES.STRING,
            "column2", Operation.OPERATORS.LT);
        operation5.setOrWithPrevious(true);
        Operation operation6 = new Operation("Value", Operation.TYPES.STRING,
            "column2", Operation.OPERATORS.IS_NULL);
        operation6.setOrWithPrevious(true);
        operation2.setOrWithPrevious(true);
        operations.add(operation1);
        operations.add(operation2);
        operations.add(operation3);
        operations.add(operation4);
        operations.add(operation5);
        operations.add(operation6);
        operations.add(new Operation("Value", Operation.TYPES.STRING,
            "column1", Operation.OPERATORS.EQ));
        operations.add(new Operation("Value", Operation.TYPES.STRING,
            "column1", Operation.OPERATORS.GE));
        operations.add(new Operation("Value", Operation.TYPES.STRING,
            "column1", Operation.OPERATORS.LE));
        operations.add(new Operation("Value", Operation.TYPES.STRING,
            "column1", Operation.OPERATORS.GT));
        operations.add(new Operation("Value", Operation.TYPES.STRING,
            "column1", Operation.OPERATORS.LT));
        String key = "key";
        String value = "value";
        repository.updateMany(operations, key, value, DummyModel.class);
        verify(mockedMongoTemplate).updateMulti(any(Query.class),any(UpdateDefinition.class), any(Class.class));
    }

    @Test
    public void testRunNativeQuery() {
        String collectionName = "collection";
        String queryStr = "Long query";
        int limit = 10;
        int offset = 100;
        String command = "{\"find\":\"%s\",\"filter\":%s,\"limit\" : %s,\"skip\" : %s}";
        command = String.format(command, collectionName, queryStr, limit, offset);
        repository.runSelectNativeQuery(queryStr, collectionName, limit, offset);
        verify(mockedMongoTemplate).executeCommand(command);
    }

    @Test
    public void testRunSelectNativeQuery() {
        String queryStr = "{\"name=\" : \"test\"}";
        String collectionName = "collection";
        int limit = 10;
        int offset = 100;
        String command = "{\"find\":\"%s\",\"filter\":%s,\"limit\" : %s,\"skip\" : %s}";
        command = String.format(command, collectionName, queryStr, limit, offset);
        Document mockedDoc = mock(Document.class);
        when(mockedMongoTemplate.executeCommand(any(Document.class))).thenReturn(mockedDoc);
        when(mockedDoc.get("cursor")).thenReturn(mockedDoc);
        repository.runSelectNativeQuery(command);

        verify(mockedDoc).get("firstBatch");
    }

    @Test
    public void testCount() {
        repository.getCount(DummyModel.class);
        verify(mockedMongoTemplate).count(any(Query.class), any(Class.class));
    }

    private List<DummyModel> prepareDummyList(int i) {
        List<DummyModel> list = new ArrayList<>();
        for (int j = 0; j < i; j++) {
            DummyModel model = new DummyModel(j+"");
            list.add(model);
        }
        return list;
    }
}

class DummyMongoRepository extends MongoRepository<DummyModel> {
    public DummyMongoRepository(MongoTemplate template){
        this.template = template;
    }
}

class DummyModel implements Serializable {
    private String id;
    public DummyModel(){}
    public DummyModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}