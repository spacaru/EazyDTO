package com.norberth.service;

import com.norberth.entity.TestEntity;
import com.norberth.entity.TestEntityTO;
import com.norberth.entity.TestObjectDataTypes;
import com.norberth.entity.TestObjectDataTypesTO;
import com.norberth.factory.GenericConverterFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConverterTest {

    private GenericConverterFactory genericConverterFactory;
    private static final String STRING_VALUE = "STRING_TEST";
    private static final Boolean BOOLEAN_VALUE = Boolean.TRUE;
    private static final Integer INTEGER_VALUE = Integer.valueOf(127);
    private static final Short SHORT_VALUE = Short.valueOf("15");
    private static final Double DOUBLE_VALUE = Double.valueOf(22.2222222);
    private static final Float FLOAT_VALUE = Float.valueOf(123.34457335f);

    @Before
    public void setUp() throws Exception {
        genericConverterFactory = GenericConverterFactory.getInstance();
        genericConverterFactory.setPackageName("com.norberth.entity");
        genericConverterFactory.setDebug(true);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createToForPrimitiveTypes() {
        int intValue = INTEGER_VALUE.intValue();
        double dblValue =
                DOUBLE_VALUE.doubleValue();
        float fltValue = FLOAT_VALUE.floatValue();
        TestEntity testEntity = new TestEntity();
        testEntity.setBoolTest(true);
        testEntity.setIntTest(intValue);
        testEntity.setDoubleTest(dblValue);
        testEntity.setFloatTest(fltValue);
        testEntity.setStringTest(STRING_VALUE);

        TestEntityTO
                createdDTO = (TestEntityTO) genericConverterFactory.getConverter(TestEntityTO.class).getTo(testEntity);
        Assert.assertEquals(createdDTO.isBoolTest(), BOOLEAN_VALUE);
        Assert.assertEquals(createdDTO.getIntTest(), intValue);
        Assert.assertEquals(createdDTO.getFloatTest(), fltValue, 0);
        Assert.assertEquals(createdDTO.getDoubleTest(), dblValue, 0);
        Assert.assertEquals(createdDTO.getStringTest(), STRING_VALUE);

    }

    @Test
    public void createToForObjectDataTypes() {
        TestObjectDataTypes testEntity = new TestObjectDataTypes();
        TestEntity testEntity1 = new TestEntity();
        testEntity1.setStringTest("str_test");
        testEntity.setaBoolean(BOOLEAN_VALUE);
        testEntity.setaDouble(DOUBLE_VALUE);
        testEntity.setAnInteger(INTEGER_VALUE);
        testEntity.setaShort(SHORT_VALUE);
        testEntity.setAfloat(FLOAT_VALUE);
        testEntity.setString(STRING_VALUE);
        testEntity.setTestEntity(testEntity1);
        TestObjectDataTypesTO createdDTO = (TestObjectDataTypesTO) genericConverterFactory.getConverter(TestObjectDataTypesTO.class).getTo(testEntity);
        Assert.assertEquals(createdDTO.getTestEntity().getStringTest(), "str_test");
        Assert.assertEquals(createdDTO.getaBoolean(), BOOLEAN_VALUE);
        Assert.assertEquals(createdDTO.getInteger(), INTEGER_VALUE);
        Assert.assertEquals(createdDTO.getaDouble(), DOUBLE_VALUE, 0);
        Assert.assertEquals(createdDTO.getaFloat(), FLOAT_VALUE, 0);
        Assert.assertEquals(createdDTO.getString(), STRING_VALUE);
        Assert.assertEquals(createdDTO.getaShort(), SHORT_VALUE);
    }

    @Test
    public void assertNoPackageErrorThrown() {
        GenericConverterFactory genericConverterFactory = GenericConverterFactory.getInstance();
//        reset package name
        genericConverterFactory.setPackageName(null);
        GenericConverter genericConverter = genericConverterFactory.getConverter(TestObjectDataTypesTO.class);
        Assert.assertEquals(genericConverter, null);

    }

}