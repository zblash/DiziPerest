package com.diziperest.web.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "series")
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String photoUrl;

    @Transient
    private MultipartFile photo;

    @ManyToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(
            name = "series_hashtag",
            joinColumns =
            @JoinColumn(name = "series_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "hashtag_id", referencedColumnName = "id"))
    private List<Hashtag> hashtags;


    public void addHashtag(Hashtag hashtag){
        hashtags.add(hashtag);
    }
    public void removeHashtag(Hashtag hashtag){
        hashtags.remove(hashtag);
    }
}