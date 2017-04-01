package com.rpo.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Album implements Identifiable<Long>{
	@Id
	@GenericGenerator(
			name = "assigned-sequence",
			strategy = "com.rpo.domain.AssignedSequenceStyleGenerator",
			parameters = {@org.hibernate.annotations.Parameter(
					name = "sequence_name", 
					value = "album_post_sequence"
					), @org.hibernate.annotations.Parameter(
							name = "initial_value", 
							value = "200"
							)
			}
			)
	    @GeneratedValue(
	        generator = "assigned-sequence", 
	        strategy = GenerationType.SEQUENCE
	    )
    private Long id;
	
	@NotNull
    private String title;
	
	@NotNull
    private Long userId;

    @OneToMany(mappedBy = "album" )
    private List<Photo> photos;
    
    public Album(){};

    public Album(Long id, String title, Long userId){
    	setId(id);
        setTitle(title);
        setUserId(userId);
    }

    public Album(String title, Long userId){
        setTitle(title);
        setUserId(userId);
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
        if(photos != null){
        	for(Photo photo:photos)
        		photo.setAlbum(this);
        }
    }

    @Override
    public String toString(){
        return "Album-id:"+this.id+", title:"+this.title+", userId:"+this.userId;
    }

}
