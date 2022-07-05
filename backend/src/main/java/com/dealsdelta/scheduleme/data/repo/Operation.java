package com.dealsdelta.scheduleme.data.repo;


import javax.persistence.ParameterMode;

/**
 * Description : <Write class Description>
 * Author: kumar
 * Created on : 09/05/21
 */

public class Operation {
    public enum TYPES {
        BOOLEAN,
        STRING,
        NUMBER
    }

    private Class<?> classType;

    private ParameterMode mode;
    private boolean orWithPrevious = false;

    public enum OPERATORS {
        GE,
        EQ,
        NE,
        LE,
        GT,
        LT,
        LIKE,
        IS_NOT_NULL,
        IS_NULL;
    }

    private Object value;
    private TYPES type;
    private String name;
    private OPERATORS operator;
    private String dynamicParamName;

    /**
     * Creates {@link Operation} object using given parameters. Use this if you expect to use callable
     * @param value Value of the operand
     * @param type {@link TYPES} of the operand or field in object
     * @param name name of the field in this object which will be passed to procedure
     * @param mode {@link ParameterMode} mode of the parameter
     */
    public Operation(Object value, TYPES type, String name, ParameterMode mode) {
        super();
        this.mode = mode;
        this.value = value;
        this.type = type;
        this.name = name;
    }

    /**
     * Creates {@link Operation} object using given parameters. Use this if you expect to use callable
     * @param value Value of the operand
     * @param type {@link TYPES} of the operand or field in object
     * @param name name of the field in this object which will be passed to procedure
     * @param mode {@link ParameterMode} mode of the parameter
     */
    public Operation(Object value, TYPES type, String name, ParameterMode mode, String dynamicParamName) {
        super();
        this.mode = mode;
        this.value = value;
        this.type = type;
        this.name = name;
        this.dynamicParamName = dynamicParamName;
    }

    /**
     * Use this operand if you want to use criteria api
     * @param value Value of the operand
     * @param type {@link TYPES} of the object ot be populated
     * @param name name of the field in this object which will be used for comparision
     * @param operator {@link Operation} which will be applied on this operand
     */
    public Operation(Object value, TYPES type, String name, OPERATORS operator) {
        super();
        this.value = value;
        this.type = type;
        this.name = name;
        this.operator = operator;
    }

    /**
     * Use this operand if you expect to use criteria api
     * @param value Value of the operand
     * @param type {@link Class} of the object ot be populated
     * @param name name of the field in this object which will be used for comparision
     * @param operator {@link Operation} which will be applied on this operand
     */
    public Operation(Object value, Class<?> type, String name, OPERATORS operator, String dynamicParamName) {
        super();
        this.value = value;
        this.classType = type;
        this.name = name;
        this.operator = operator;
        this.dynamicParamName = dynamicParamName;
    }

    /**
     * Use this operand if you expect to use criteria api
     * @param value Value of the operand
     * @param type {@link Class} of the object ot be populated
     * @param name name of the field in this object which will be used for comparision
     * @param operator {@link Operation} which will be applied on this operand
     */
    public Operation(Object value, Class<?> type, String name, OPERATORS operator) {
        super();
        this.value = value;
        this.classType = type;
        this.name = name;
        this.operator = operator;
    }



    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public TYPES getType() {
        return type;
    }

    public void setType(TYPES type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getOperandType() {
        Class tmpType = null;
        if(this.type != null) {
            switch (this.type) {
                case BOOLEAN:
                    tmpType = Boolean.class;
                    break;
                case STRING:
                    tmpType = String.class;
                    break;
                case NUMBER:
                    tmpType = Integer.class;
                    break;
            }
        }
        else {
            tmpType = classType;
        }
        return tmpType;
    }

    public ParameterMode getMode() {
        return mode;
    }

    public void setMode(ParameterMode mode) {
        this.mode = mode;
    }

    public OPERATORS getOperator() {
        return operator;
    }

    public boolean isOrWithPrevious() {
        return orWithPrevious;
    }

    public void setOrWithPrevious(boolean orWithPrevious) {
        this.orWithPrevious = orWithPrevious;
    }

    public String getDynamicParamName() {
        return dynamicParamName;
    }

    public void setDynamicParamName(String dynamicParamName) {
        this.dynamicParamName = dynamicParamName;
    }
}