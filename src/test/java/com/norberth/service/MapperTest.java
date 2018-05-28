package com.norberth.service;

import com.norberth.core.DTOMapper;
import com.norberth.entity.Entity;
import com.norberth.entity.EntityDTO;
import com.norberth.entity.TestObjectDataTypes;
import com.norberth.entity.TestObjectDataTypesTO;
import com.norberth.factory.MapperFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MapperTest {

    private MapperFactory genericMapperFactory;
    private static final String STRING_VALUE = "STRING_TEST";
    private static final Boolean BOOLEAN_VALUE = Boolean.TRUE;
    private static final Integer INTEGER_VALUE = Integer.valueOf(127);
    private static final Short SHORT_VALUE = Short.valueOf("15");
    private static final Double DOUBLE_VALUE = Double.valueOf(22.2222222);
    private static final Float FLOAT_VALUE = Float.valueOf(123.34457335f);

    @Before
    public void setUp() throws Exception {
        genericMapperFactory = MapperFactory.getInstance();
        genericMapperFactory.setPackageName("com.norberth.entity");
        genericMapperFactory.setDebug(true);
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
        Entity entity = new Entity();
        entity.setBoolTest(true);
        entity.setIntTest(intValue);
        entity.setDoubleTest(dblValue);
        entity.setFloatTest(fltValue);
        entity.setStringTest(STRING_VALUE);

        EntityDTO
                createdDTO = (EntityDTO) genericMapperFactory.getConverter(EntityDTO.class).getTo(entity);
        Assert.assertEquals(createdDTO.isBoolTest(), BOOLEAN_VALUE);
        Assert.assertEquals(createdDTO.getIntTest(), intValue);
        Assert.assertEquals(createdDTO.getFloatTest(), fltValue, 0);
        Assert.assertEquals(createdDTO.getDoubleTest(), dblValue, 0);
        Assert.assertEquals(createdDTO.getStringTest(), STRING_VALUE);

    }

    @Test
    public void createToForObjectDataTypes() {
        TestObjectDataTypes testEntity = new TestObjectDataTypes();
        Entity entity1 = new Entity();
        entity1.setStringTest("str_test");
        testEntity.setaBoolean(BOOLEAN_VALUE);
        testEntity.setaDouble(DOUBLE_VALUE);
        testEntity.setAnInteger(INTEGER_VALUE);
        testEntity.setaShort(SHORT_VALUE);
        testEntity.setAfloat(FLOAT_VALUE);
        testEntity.setString(STRING_VALUE);
        testEntity.setEntity(entity1);
        TestObjectDataTypesTO createdDTO = (TestObjectDataTypesTO) genericMapperFactory.getConverter(TestObjectDataTypesTO.class).getTo(testEntity);
        Assert.assertEquals(createdDTO.getEntity().getStringTest(), "str_test");
        Assert.assertEquals(createdDTO.getaBoolean(), BOOLEAN_VALUE);
        Assert.assertEquals(createdDTO.getInteger(), INTEGER_VALUE);
        Assert.assertEquals(createdDTO.getaDouble(), DOUBLE_VALUE, 0);
        Assert.assertEquals(createdDTO.getaFloat(), FLOAT_VALUE, 0);
        Assert.assertEquals(createdDTO.getString(), STRING_VALUE);
        Assert.assertEquals(createdDTO.getaShort(), SHORT_VALUE);
    }

    @Test
    public void assertNoPackageErrorThrown() {
        MapperFactory genericMapperFactory = MapperFactory.getInstance();
//        reset package name
        genericMapperFactory.setPackageName(null);
        DTOMapper objectConverter = genericMapperFactory.getConverter(TestObjectDataTypesTO.class);
        Assert.assertEquals(objectConverter, null);

    }

}