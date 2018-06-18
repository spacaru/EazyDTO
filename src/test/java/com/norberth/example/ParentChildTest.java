package com.norberth.example;

import com.norberth.example.dto.Parent2ChildDTO;
import com.norberth.factory.MapperFactory;
import com.norberth.factory.MapperFactoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

public class ParentChildTest {
    private MapperFactory mapperFactory;

    @Before
    public void setUp() {
        mapperFactory = MapperFactoryImpl.withDebugEnabled(true).scanPackage("com.norberth");

    }

    @Test
    public void testParentChild() {
        ParentEntity parentEntity = new ParentEntity();
        parentEntity.setParentName("parent-name");
        parentEntity.setId(1);
        ChildEntity firstChild = new ChildEntity();
        ChildEntity secondChild = new ChildEntity();
        ChildEntity thirdChild = new ChildEntity();
        ChildEntity fourthChild = new ChildEntity();
        ChildEntity fifthChild = new ChildEntity();
        firstChild.setChildName("a-born");
        secondChild.setChildName("b-born");
        thirdChild.setChildName("d-born");
        fourthChild.setChildName("z-born");
        fifthChild.setChildName("c-born");
        Set s = new HashSet<ChildEntity>();
        s.add(fifthChild);
        s.add(thirdChild);
        s.add(fourthChild);
        s.add(firstChild);
        parentEntity.setChildren(s);

        Parent2ChildDTO parent2ChildDTO = (Parent2ChildDTO) mapperFactory.getMapper(Parent2ChildDTO.class).getTo(parentEntity);
        Assert.notNull(parent2ChildDTO);
        System.out.println(parent2ChildDTO);
//        it is possible to map only certain values from object as we see in parent2childto -> children are mapped from ParentEntity's children
    }
}
