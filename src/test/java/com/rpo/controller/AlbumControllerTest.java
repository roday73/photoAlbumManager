package com.rpo.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.rpo.domain.Album;
import com.rpo.domain.Photo;
import com.rpo.service.AlbumService;
import com.rpo.service.PhotoService;

@RunWith(SpringJUnit4ClassRunner.class)
public class AlbumControllerTest extends BaseControllerTest{
	
    @Mock
    AlbumService albumService;
    
    @Mock
    PhotoService photoService;
    
    @InjectMocks
    protected AlbumController albumController;
    
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
       
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(albumController)
                .build();
    }

    @Test
    public void getAlbumTest() throws Exception {    	
    	Album album = new Album();
    	album.setTitle(title1);
    	album.setUserId(userId1);
    	album.setId(albumId1);
    	when(albumService.read(albumId1)).thenReturn(album);       
        mockMvc.perform(get("/albums/{albumId}",albumId1).accept(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(jsonPath("$.title", is(title1)))
	        .andExpect(jsonPath("$.userId", is(userId1.intValue())))
	        .andExpect(jsonPath("$.id", is(albumId1.intValue())));

    }   
    
    @Test
    public void getAlbumListTest() throws Exception {
    	List<Album> albums = new ArrayList<Album>();
    	List<Photo> photos = new ArrayList<Photo>();
    	Photo photo = new Photo();
		photo.setId(photoId1);
    	photo.setUrl(photoUrl1);
    	photo.setTitle(photoTitle1);
    	photos.add(photo);
    	photo = new Photo();
		photo.setId(photoId2);
    	photo.setUrl(photoUrl2);
    	photo.setTitle(photoTitle2);
    	photos.add(photo);
    	
    	Album album = new Album();
    	album.setTitle(title1);
    	album.setUserId(userId1);
    	album.setId(albumId1);
    	album.setPhotos(photos);
    	albums.add(album);
    	
    	album = new Album();
    	album.setTitle(title2);
    	album.setUserId(userId2);
    	album.setId(albumId2);
    	photos = new ArrayList<Photo>();
    	photo = new Photo();
		photo.setId(photoId3);
    	photo.setUrl(photoUrl3);
    	photo.setTitle(photoTitle3);
    	photos.add(photo);
    	album.setPhotos(photos);
    	albums.add(album);
    	    	
    	when(albumService.list()).thenReturn(albums);       
        mockMvc.perform(get("/albums").accept(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(2)))
	        .andExpect(jsonPath("$[0].title", is(title1)))
	        .andExpect(jsonPath("$[0].userId", is(userId1.intValue())))
	        .andExpect(jsonPath("$[0].id", is(albumId1.intValue())))
	        .andExpect(jsonPath("$[0].photos[0].title", is(photoTitle1)))
	        .andExpect(jsonPath("$[0].photos[0].url", is(photoUrl1)))
	        .andExpect(jsonPath("$[0].photos[0].id", is(photoId1.intValue())))
	        .andExpect(jsonPath("$[0].photos[1].title", is(photoTitle2)))
	        .andExpect(jsonPath("$[0].photos[1].url", is(photoUrl2)))
	        .andExpect(jsonPath("$[0].photos[1].id", is(photoId2.intValue())))
	        .andExpect(jsonPath("$[1].title", is(title2)))
	        .andExpect(jsonPath("$[1].userId", is(userId2.intValue())))
	        .andExpect(jsonPath("$[1].id", is(albumId2.intValue())))	       
	        .andExpect(jsonPath("$[1].photos[0].title", is(photoTitle3)))
	        .andExpect(jsonPath("$[1].photos[0].url", is(photoUrl3)))
	        .andExpect(jsonPath("$[1].photos[0].id", is(photoId3.intValue())));

    }
    
    @Test
    public void createAlbumTest() throws Exception {    	
    	Album album = new Album();
    	album.setTitle(title1);
    	album.setUserId(userId1);
        when(albumService.save(album)).thenReturn(album);
        mockMvc.perform(
        		post("/albums")
        			.contentType(MediaType.APPLICATION_JSON_UTF8)
        			.content(asJsonString(album))        			
        			)
				.andExpect(status().isCreated());

    }   
    
    @Test
    public void updateAlbumTest() throws Exception {    	
    	Album album = new Album();
    	album.setId(albumId1);
    	album.setTitle(title1);
    	album.setUserId(userId1);
    	when(albumService.read(albumId1)).thenReturn(album);
        when(albumService.save(album)).thenReturn(album);
        
        // test case where album exists
        mockMvc.perform(
        		put("/albums/{albumId}",albumId1)
        			.contentType(MediaType.APPLICATION_JSON_UTF8)
        			.content(asJsonString(album))        			
        			)
				.andExpect(status().isOk());
        
        // test case where album does not exist
    	when(albumService.read(albumId1)).thenReturn(null);
        mockMvc.perform(
        		put("/albums/{albumId}",albumId1)
    			.contentType(MediaType.APPLICATION_JSON_UTF8)
    			.content(asJsonString(album))        			
    			)
				.andExpect(status().isNotFound());

    }    
    
    @Test
    public void deleteAlbumTest() throws Exception {    	
    	Album album = new Album();
    	album.setId(albumId1);
    	album.setTitle(title1);
    	album.setUserId(userId1);
    	
    	List<Photo> photos = new ArrayList<Photo>();
    	
    	// test with no child photos
    	when(albumService.read(albumId1)).thenReturn(album);
    	when(photoService.getAlbumPhotos(albumId1)).thenReturn(photos);
    	doNothing().when(albumService).delete(albumId1);
        mockMvc.perform(
        		delete("/albums/{albumId}",albumId1))
				.andExpect(status().isOk());
        
        // test for case where child photos exist, causing conflict
        photos.add(new Photo());
        when(photoService.getAlbumPhotos(albumId1)).thenReturn(photos);
    	doNothing().when(albumService).delete(albumId1);
        mockMvc.perform(
        		delete("/albums/{albumId}",albumId1))
				.andExpect(status().isConflict());

    }   
}
