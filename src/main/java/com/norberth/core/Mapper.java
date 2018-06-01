package com.norberth.core;

import com.norberth.core.database.SqlType;
import com.norberth.util.SortationType;

import javax.persistence.EntityManager;
import java.util.List;

public interface Mapper {
    /**
     * Returns the newly created TO from source object
     *
     * @param source
     * @return
     */
    Object getTo(Object source);

    /**
     * Returns the newly created list of TO objects created from sourceList
     *
     * @param sourceList
     * @return
     */
    List<Object> getToList(List sourceList);

    /**
     * Returns List of TO objects created from entities retrieved by query
     *
     * @param sqlType
     * @param sql
     * @return
     */
    List getToListFromSql(SqlType sqlType, String sql);

    Object getToFromSql();

    /**
     * Returns the sorted TO list from the input sourceList , sorted by the given attribute
     *
     * @param sourceList
     * @param attribute
     * @return
     */
    List<Object> getToListSortBy(List sourceList, String attribute);

    /**
     * Returns the sorted TO list from the input sourceList with the given sortation type ( DEFAULT : ASCENDING )
     *
     * @param sourceList
     * @param attribute
     * @param sortationType
     * @return
     */
    List<Object> getToListSortBy(List sourceList, String attribute, SortationType sortationType);

    /**
     * Returns the target class for mapper
     *
     * @return
     */
    Class getType();

    void setEm(EntityManager em);

    EntityManager getEm();
}
