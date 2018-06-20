package com.norberth.core.service;

public class ResourceSharingService {

    private Boolean isInherited;

    public ResourceSharingService() {
        isInherited = new Boolean(false);
    }

    public Boolean getIsInherited() {
        return isInherited;
    }

    public void setIsInherited(Boolean isInherited) {
        this.isInherited = isInherited;
    }
}
