package com.norberth.service;

import java.util.List;

public interface Converter {
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
     * Returns the sorted TO list from the input sourceList , sorted by the given attribute
     *
     * @param sourceList
     * @param attribute
     * @return
     */
    List<Object> getToListSortBy(List sourceList, String attribute);
}
