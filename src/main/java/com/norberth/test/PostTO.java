package com.norberth.test;

import com.norberth.annotation.MapList;
import com.norberth.annotation.MapObject;
import com.norberth.annotation.MapAttribute;

import java.util.List;

@MapObject(sourceClass = Post.class)
public class PostTO extends BaseEntityTO {

    @MapAttribute(sourceField = "idd", inheritedField = false)
    private Integer id;
    @MapAttribute(sourceField = "title")
    private String title;
    @MapAttribute(sourceField = "description")
    private String description;
    @MapList(sourceField = "tags.name", inheritedField = false)
    private List<Integer> tags;
    @MapAttribute(sourceField = "user.id", inheritedField = true)
    private Integer userId;
    private Boolean isDonation;
    //    private ImageTO mainImage;
//    private List<ImageTO> images;
    private Float latitude;
    private Float longitude;

//    private List<String> getTagsAsStringList(List<Tag> tags) {
//        List<String> result = new ArrayList<>();
//        for (Tag tag : tags) {
//            result.add(tag.getName());
//        }
//        return result;
//    }


    @Override
    public String toString() {
        return "PostTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", userId=" + userId +
                ", isDonation=" + isDonation +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
