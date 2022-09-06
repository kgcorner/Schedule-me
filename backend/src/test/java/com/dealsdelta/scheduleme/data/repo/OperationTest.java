package com.dealsdelta.scheduleme.data.repo;

import com.dealsdelta.scheduleme.data.models.JobModel;
import org.junit.Test;

import javax.persistence.ParameterMode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 04/09/22
 */

public class OperationTest {
    @Test
    public void testOperationBasicConstructor() {
        String value = "Test";
        String column = "TestColumn";
        Operation operation = new Operation(value, Operation.TYPES.STRING,
            column, Operation.OPERATORS.EQ);
        assertEquals(value, operation.getValue());
        assertEquals(column, operation.getName());
        assertEquals(Operation.TYPES.STRING, operation.getType());
        assertEquals(Operation.OPERATORS.EQ, operation.getOperator());
    }

    @Test
    public void testOperationConstructorWithDynamicParam() {
        String value = "Test";
        String column = "TestColumn";
        String dynamicParam = "Dynamic Param";
        Operation operation = new Operation(value, Operation.TYPES.STRING, column, ParameterMode.IN,
             dynamicParam);
        assertEquals(value, operation.getValue());
        assertEquals(column, operation.getName());
        assertEquals(Operation.TYPES.STRING, operation.getType());
        assertEquals(dynamicParam, operation.getDynamicParamName());
        assertEquals(ParameterMode.IN, operation.getMode());
    }

    @Test
    public void testOperationConstructorWithParamMode() {
        String value = "Test";
        String column = "TestColumn";
        String dynamicParam = "Dynamic Param";
        Operation operation = new Operation(value, Operation.TYPES.STRING, column, ParameterMode.IN);
        assertEquals(value, operation.getValue());
        assertEquals(column, operation.getName());
        assertEquals(Operation.TYPES.STRING, operation.getType());
        assertEquals(ParameterMode.IN, operation.getMode());
    }

    @Test
    public void testOperationConstructorWithClassType() {
        String value = "Test";
        String column = "TestColumn";
        String dynamicParam = "Dynamic Param";
        Operation operation = new Operation(value, JobModel.class, column, Operation.OPERATORS.EQ);
        assertEquals(value, operation.getValue());
        assertEquals(column, operation.getName());
        assertEquals(JobModel.class, operation.getOperandType());
        assertEquals(Operation.OPERATORS.EQ, operation.getOperator());
    }

    @Test
    public void testOperationConstructorWithClassTypeAndDynamicParam() {
        String value = "Test";
        String column = "TestColumn";
        String dynamicParam = "Dynamic Param";
        Operation operation = new Operation(value, JobModel.class, column, Operation.OPERATORS.EQ, dynamicParam);
        assertEquals(value, operation.getValue());
        assertEquals(column, operation.getName());
        assertEquals(JobModel.class, operation.getOperandType());
        assertEquals(Operation.OPERATORS.EQ, operation.getOperator());
        assertEquals(dynamicParam, operation.getDynamicParamName());
    }

    @Test
    public void testOperatorType() {
        Operation operation = new Operation("value", Operation.TYPES.STRING, "column", ParameterMode.IN);
        assertEquals(String.class, operation.getOperandType());
        operation.setType(Operation.TYPES.BOOLEAN);
        assertEquals(Boolean.class, operation.getOperandType());
        operation.setType(Operation.TYPES.NUMBER);
        assertEquals(Integer.class, operation.getOperandType());
    }

    @Test
    public void testSetters() {
        String value = "val";
        String name = "name";
        String dynamicParam = "dynamicParam";
        Operation operation = new Operation("value", Operation.TYPES.STRING, "column", ParameterMode.IN);
        operation.setValue(value);
        assertEquals(value, operation.getValue());
        operation.setName(name);
        assertEquals(name, operation.getName());
        operation.setMode(ParameterMode.IN);
        assertEquals(ParameterMode.IN, operation.getMode());
        operation.setOrWithPrevious(true);
        assertTrue(operation.isOrWithPrevious());
        operation.setDynamicParamName(dynamicParam);
        assertEquals(dynamicParam, operation.getDynamicParamName());
    }
}