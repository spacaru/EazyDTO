package com.norberth.test;

import com.norberth.annotation.MapListAttribute;
import com.norberth.annotation.MapObject;
import com.norberth.annotation.MapObjectAttribute;

import java.util.List;

@MapObject(sourceClass = Post.class)
public class PostTO extends BaseEntityTO {


    @MapObjectAttribute(sourceField = "title")
    private String title;
    @MapObjectAttribute(sourceField = "description")
    private String description;
    @MapListAttribute(sourceField = "tags.name", inheritedField = false)
    private List<Integer> tags;
    @MapObjectAttribute(sourceField = "user.id", inheritedField = true)
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
                "id=" + super.id +
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
