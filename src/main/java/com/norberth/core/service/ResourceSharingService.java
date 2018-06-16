package com.norberth.core.service;

public class ResourceSharingService {

    private Boolean IS_INHERITED;

    public ResourceSharingService() {
        IS_INHERITED = new Boolean(false);
    }

    public Boolean getIS_INHERITED() {
        return IS_INHERITED;
    }

    public void setIS_INHERITED(Boolean IS_INHERITED) {
        this.IS_INHERITED = IS_INHERITED;
    }
}
