package com.norberth.test;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
// @EntityListeners(UpdateModifiedAttributesListener.class)
public class BaseEntity {
    /**
     *
     */
    private static final long serialVersionUID = -8589542302979084534L;
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    private Date created;

    public BaseEntity() {
    }

    public BaseEntity(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return id == null;
    }


    public Integer getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
