package com.norberth.test;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_post")
@NamedQueries({@NamedQuery(name = Post.ALL, query = "SELECT p FROM Post p ")})

public class Post extends BaseEntity {

    private static final String PREFIX = "io.rcycle.entity.Post";

    public static final String ALL = PREFIX + ".all";

    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    //    @XmlTransient
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    //    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tags;
    @Column(name = "is_donation")
    private Boolean isDonation;
    @Column(name = "latitude")
    private Float latitude;
    @Column(name = "longitude")
    private Float longitude;
//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    private Image mainImage;
//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<Image> images;

    public Post() {
        super();
    }

    public Post(Integer id, List<Tag> tags, String title, String description, User user, Boolean isDonation, Float latitude, Float longitude) {
        super(id);
        this.tags = tags;
        this.title = title;
        this.description = description;
        this.user = user;
        this.isDonation = isDonation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static String getPREFIX() {
        return PREFIX;
    }

    public static String getALL() {
        return ALL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getDonation() {
        return isDonation;
    }

    public void setDonation(Boolean donation) {
        isDonation = donation;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Post(String title, String description, Boolean isDonation, Float latitude, Float longitude) {
        this.title = title;
        this.description = description;
        this.isDonation = isDonation;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Post{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isDonation=" + isDonation +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
