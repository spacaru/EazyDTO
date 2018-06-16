package com.norberth.example.custom.mapper;

import com.norberth.event.CustomEvent;
import com.norberth.example.ParentEntity;
import com.norberth.example.dto.Parent2ChildDTO;
import com.norberth.util.ObjectComparator;
import com.norberth.util.SortingType;

public class CustomParent2ChildMapper implements CustomEvent<ParentEntity, Parent2ChildDTO> {
    @Override
    public Parent2ChildDTO postMap(ParentEntity sourceEntity, Parent2ChildDTO transferObject) {
//        here we can add custom behavior to map our class

//        for example we can sort lists easily by attribute
        System.out.println(transferObject.getChildren());
        transferObject.getChildren().sort(new ObjectComparator("childName", SortingType.ASCENDING));
        System.out.println(transferObject.getChildren());

//        we can see the difference
        return transferObject;
    }
}
