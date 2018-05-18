package com.norberth.test;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_tag")
@NamedQueries({@NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t ")})

public class Tag extends BaseEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts;

    public Tag() {
    }

    public Tag(Integer id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                '}';
    }
}
