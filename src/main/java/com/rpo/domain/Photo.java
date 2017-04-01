package com.rpo.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Photo implements Identifiable<Long>{

    @Id
    @GenericGenerator(
    		name = "assigned-sequence",
    		strategy = "com.rpo.domain.AssignedSequenceStyleGenerator",
    		parameters = {@org.hibernate.annotations.Parameter(
    				name = "sequence_name", 
    				value = "photo_post_sequence"
    				), @org.hibernate.annotations.Parameter(
    						name = "initial_value", 
    						value = "6000"
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
    private String url;
    
    @NotNull
    private String thumbnailUrl;

    @JsonIgnore
    @ManyToOne()
    private Album album;
    
    public Photo(){};

    public Photo(Long id, String title, String url, String thumbNailUrl){
    	setId(id);
    	setTitle(title);
    	setUrl(url);
    	setThumbnailUrl(thumbNailUrl);
    }

    public Photo(String title, String url, String thumbNailUrl){
    	setTitle(title);
    	setUrl(url);
    	setThumbnailUrl(thumbNailUrl);
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbNailUrl) {
		this.thumbnailUrl = thumbNailUrl;
	}

	public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    @Override
    public String toString(){
        return "Photo-id:"+this.id+", title:"+this.title+", album: "+this.album+", url:("+this.url+")"+", thumbNailUrl:("+this.thumbnailUrl+")";
    }

}
