package com.norberth.test;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.awt.*;
import java.util.List;

@Entity
@Table(name = "t_user")
@NamedQueries({@NamedQuery(name = User.ALL, query = "SELECT u FROM User u ")})
public class User extends BaseEntity {

    private static final String PREFIX = "io.rcycle.entity.User";
    public static final String ALL = PREFIX + ".all";

    private static final long serialVersionUID = 1L;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @XmlTransient
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    private List<Post> posts;

    @Column(name = "phone")
    private String phoneNumber;
    @Column(name = "is_phone_number_displayed", nullable = false)
    private Boolean isPhoneNumberDisplayed = Boolean.FALSE;
    @Column(name = "is_email_displayed", nullable = false)
    private Boolean isEmailDisplayed = Boolean.FALSE;
    @ManyToOne(fetch = FetchType.EAGER)
    private Image profileImage;
    @XmlTransient
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    private List<Image> uploadedImages;

    public User(Integer id ,String password, String email, String firstName, String lastName, List<Post> posts, String phoneNumber, Boolean isPhoneNumberDisplayed, Boolean isEmailDisplayed) {
        super(id);
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
        this.phoneNumber = phoneNumber;
        this.isPhoneNumberDisplayed = isPhoneNumberDisplayed;
        this.isEmailDisplayed = isEmailDisplayed;
    }

    public String toString() {
        return "User{" +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", posts=" + posts +
                ", profileImage=" + profileImage +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isPhoneNumberDisplayed=" + isPhoneNumberDisplayed +
                ", isEmailDisplayed=" + isEmailDisplayed +
                '}';
    }
}