package com.rpo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rpo.domain.Album;
import com.rpo.domain.Photo;
import com.rpo.service.AlbumService;
import com.rpo.service.PhotoService;

@RunWith(SpringJUnit4ClassRunner.class)
public class PhotoControllerTest extends BaseControllerTest{  
	
    @Mock
    AlbumService albumService;
    
    @Mock
    PhotoService photoService;
    
    @InjectMocks
    protected PhotoController photoController;
    
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
        
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(photoController)
                .build();
    }

    @Test
    public void getPhotoTest() throws Exception {    	
    	Photo photo = new Photo();
    	photo.setTitle(photoTitle1);
    	photo.setUrl(photoUrl1);
    	photo.setThumbnailUrl(photoThumbnailUrl1);
    	photo.setId(photoId1);
    	when(photoService.read(photoId1)).thenReturn(photo);       
        mockMvc.perform(get("/photos/{photoId}",photoId1).accept(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(jsonPath("$.title", is(photoTitle1)))
	        .andExpect(jsonPath("$.url", is(photoUrl1)))
	        .andExpect(jsonPath("$.thumbnailUrl", is(photoThumbnailUrl1)))
	        .andExpect(jsonPath("$.id", is(photoId1.intValue())));

    }  
    
    @Test
    public void getAllPhotosTest() throws Exception { 
    	List<Photo> photos = new ArrayList<Photo>();
    	
    	Photo photo = new Photo();
    	photo.setTitle(photoTitle1);
    	photo.setUrl(photoUrl1);
    	photo.setThumbnailUrl(photoThumbnailUrl1);
    	photo.setId(photoId1);
    	photos.add(photo);
    	photo = new Photo();
    	photo.setTitle(photoTitle2);
    	photo.setUrl(photoUrl2);
    	photo.setThumbnailUrl(photoThumbnailUrl2);
    	photo.setId(photoId2);
    	photos.add(photo);
    	
    	when(photoService.list()).thenReturn(photos);       
        mockMvc.perform(get("/photos").accept(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(2)))
	        .andExpect(jsonPath("$[0].title", is(photoTitle1)))
	        .andExpect(jsonPath("$[0].id", is(photoId1.intValue())))
	        .andExpect(jsonPath("$[0].url", is(photoUrl1)))
	        .andExpect(jsonPath("$[0].thumbnailUrl", is(photoThumbnailUrl1)))
	        .andExpect(jsonPath("$[1].title", is(photoTitle2)))
	        .andExpect(jsonPath("$[1].id", is(photoId2.intValue())))
	        .andExpect(jsonPath("$[1].url", is(photoUrl2)))
	        .andExpect(jsonPath("$[1].thumbnailUrl", is(photoThumbnailUrl2)));

    }   
    
    @Test
    public void getAlbumPhotosTest() throws Exception { 
    	List<Photo> photos = new ArrayList<Photo>();
    	
    	Photo photo = new Photo();
    	photo.setTitle(photoTitle1);
    	photo.setUrl(photoUrl1);
    	photo.setThumbnailUrl(photoThumbnailUrl1);
    	photo.setId(photoId1);
    	photos.add(photo);
    	photo = new Photo();
    	photo.setTitle(photoTitle2);
    	photo.setUrl(photoUrl2);
    	photo.setThumbnailUrl(photoThumbnailUrl2);
    	photo.setId(photoId2);
    	photos.add(photo);
    	photo = new Photo();
    	photo.setTitle(photoTitle3);
    	photo.setUrl(photoUrl3);
    	photo.setThumbnailUrl(photoThumbnailUrl3);
    	photo.setId(photoId3);
    	photos.add(photo);
    	
    	Album album = new Album();
    	album.setTitle(title1);
    	album.setUserId(userId1);
    	album.setId(albumId1);
    	album.setPhotos(photos);
    	
    	when(photoService.getAlbumPhotos(albumId1)).thenReturn(photos);       

    	// test case where album exists
    	when(albumService.read(albumId1)).thenReturn(album);     
        mockMvc.perform(get("/photos/album/{albumId}",albumId1).accept(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(3)))
	        .andExpect(jsonPath("$[0].title", is(photoTitle1)))
	        .andExpect(jsonPath("$[0].id", is(photoId1.intValue())))
	        .andExpect(jsonPath("$[0].url", is(photoUrl1)))
	        .andExpect(jsonPath("$[0].thumbnailUrl", is(photoThumbnailUrl1)))
	        .andExpect(jsonPath("$[1].title", is(photoTitle2)))
	        .andExpect(jsonPath("$[1].id", is(photoId2.intValue())))
	        .andExpect(jsonPath("$[1].url", is(photoUrl2)))
	        .andExpect(jsonPath("$[1].thumbnailUrl", is(photoThumbnailUrl2)))
	        .andExpect(jsonPath("$[2].title", is(photoTitle3)))
	        .andExpect(jsonPath("$[2].id", is(photoId3.intValue())))
	        .andExpect(jsonPath("$[2].url", is(photoUrl3)))
	        .andExpect(jsonPath("$[2].thumbnailUrl", is(photoThumbnailUrl3)));      

    	// test case where album does not exist
    	when(albumService.read(albumId1)).thenReturn(null);     
        mockMvc.perform(get("/photos/album/{albumId}",albumId1).accept(MediaType.APPLICATION_JSON_UTF8))
        	.andExpect(status().isNotFound());

    }   
    
    @Test
    public void createPhotoTest() throws Exception {    	
    	Photo photo = new Photo();
    	photo.setTitle(photoTitle1);
    	photo.setUrl(photoUrl1);
    	photo.setThumbnailUrl(photoThumbnailUrl1);
    	
    	Album album = new Album();
    	album.setTitle(title1);
    	album.setUserId(userId1);
    	album.setId(albumId1);
    	
    	// test case where album exists
        when(photoService.save(photo)).thenReturn(photo);
        when(albumService.read(albumId1)).thenReturn(album);
        mockMvc.perform(
        		post("/photos/album/{albumId}",albumId1)
        			.contentType(MediaType.APPLICATION_JSON_UTF8)
        			.content(asJsonString(photo))        			
        			)
				.andExpect(status().isCreated());
        	
    	// test case where album does not exist
        when(photoService.save(photo)).thenReturn(photo);
        when(albumService.read(albumId1)).thenReturn(null);
        mockMvc.perform(
        		post("/photos/album/{albumId}",albumId1)
        			.contentType(MediaType.APPLICATION_JSON_UTF8)
        			.content(asJsonString(photo))        			
        			)
				.andExpect(status().isNotFound());

    }   
    
    @Test
    public void updatePhotoTest() throws Exception {    	
    	Photo photo = new Photo();
    	photo.setTitle(photoTitle1);
    	photo.setUrl(photoUrl1);
    	photo.setThumbnailUrl(photoThumbnailUrl1);
    	photo.setId(photoId1);
        when(photoService.save(photo)).thenReturn(photo);
        
        // test case where photo exists
    	when(photoService.read(photoId1)).thenReturn(photo);
        mockMvc.perform(
        		put("/photos/{photoId}",photoId1)
        			.contentType(MediaType.APPLICATION_JSON_UTF8)
        			.content(asJsonString(photo))        			
        			)
				.andExpect(status().isOk());
        
        // test case where photo does not exist
    	when(photoService.read(photoId1)).thenReturn(null);
        mockMvc.perform(
        		put("/photos/{photoId}",photoId1)
        			.contentType(MediaType.APPLICATION_JSON_UTF8)
        			.content(asJsonString(photo))        			
        			)
				.andExpect(status().isNotFound());

    }      
    
    @Test
    public void deletePhotoTest() throws Exception {    	
    	Photo photo = new Photo();
    	photo.setTitle(photoTitle1);
    	photo.setUrl(photoUrl1);
    	photo.setThumbnailUrl(photoThumbnailUrl1);
    	photo.setId(photoId1);
    	
        // test case where photo exists
    	doNothing().when(photoService).delete(photoId1);
        mockMvc.perform(
        		delete("/photos/{photoId}",photoId1))
				.andExpect(status().isOk());
    	
        // test case where photo does not exist
    	doThrow(EmptyResultDataAccessException.class).when(photoService).delete(photoId1);
        mockMvc.perform(
        		delete("/photos/{photoId}",photoId1))
				.andExpect(status().isNotFound());

    }   
}
