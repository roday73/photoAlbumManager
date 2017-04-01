package com.rpo.service;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.rpo.domain.Album;
import com.rpo.domain.Photo;

@RunWith(SpringRunner.class)
public class PhotoServiceTest extends BaseServiceTest{
	
	private final String photoTitle = "photoTitle";
	private final String photoTitle2 = "photoTitle2";
	private final String photoUrl = "photoUrl";
	private final String photoUrl2 = "photoUr2l";
	private final String photoThumbnailUrl = "photoThumbnailUrl";
	private final String albumTitle = "myAlbum";
	private final String albumTitle2 = "myAlbum2";
	private final Long userId = 100L;
	
	@Autowired
	PhotoService photoService;
	
	@Autowired
	AlbumService albumService;
	
	@Test
	public void testPhotoCRUD(){
		
		// Init albums
		Album album = new Album();
		album.setTitle(albumTitle);
		album.setUserId(userId);		
		Album album1 = albumService.save(album);		
		album = new Album();
		album.setTitle(albumTitle2);
		album.setUserId(userId);		
		Album album2 = albumService.save(album);
		
		// Test crud methods
		Photo createdPhoto = photoCreate(album1);
		photoRead(createdPhoto);
		photoReadAll(1);
		photoUpdate(createdPhoto);
		createdPhoto = photoCreate(album1);
		photoReadAll(2);
		testAlbumPhotos(album1.getId(), 2);
		photoDelete(createdPhoto);
		photoReadAll(1);		
		photoCreate(album2);
		photoCreate(album2);
		photoCreate(album2);
		photoCreate(album2);
		testAlbumPhotos(album1.getId(), 1);
		testAlbumPhotos(album2.getId(), 4);
	}
		
	private Photo photoCreate(Album album){
		
		Photo photo = new Photo();
		photo.setTitle(photoTitle);
		photo.setUrl(photoUrl);
		photo.setThumbnailUrl(photoThumbnailUrl);
		photo.setAlbum(album);
		
		Photo createdPhoto = photoService.save(photo);
		assertThat(createdPhoto.getTitle(),is(photoTitle));
		assertThat(createdPhoto.getUrl(),is(photoUrl));
		assertThat(createdPhoto.getThumbnailUrl(),is(photoThumbnailUrl));
		assertThat(createdPhoto.getId(),is(notNullValue()));
		assertThat(createdPhoto.getAlbum().getId(), is(album.getId()));
		
		return createdPhoto;
	}
	
	private void photoRead(Photo createdPhoto)
	{
		Long createdPhotoId = createdPhoto.getId();
		Photo photo = photoService.read(createdPhotoId);
		assertThat(photo.getTitle(),is(createdPhoto.getTitle()));
		assertThat(photo.getUrl(),is(createdPhoto.getUrl()));
		assertThat(photo.getThumbnailUrl(),is(createdPhoto.getThumbnailUrl()));
		assertThat(photo.getId(),is(createdPhotoId));
	}
	
	private void photoReadAll(int totalCnt)
	{
		List<Photo> photos = (List<Photo>) photoService.list();
		assertThat(photos.size(), is(totalCnt));
	}
	
	private void photoUpdate(Photo createdPhoto)
	{
		Long createdPhotoId = createdPhoto.getId();
		Photo photo = photoService.read(createdPhotoId);
		
		photo.setTitle(photoTitle2);
		photo.setUrl(photoUrl2);
		Photo updatedPhoto = photoService.save(photo);
		
		assertThat(updatedPhoto.getTitle(),is(photoTitle2));
		assertThat(updatedPhoto.getUrl(),is(photoUrl2));
		assertThat(updatedPhoto.getThumbnailUrl(),is(createdPhoto.getThumbnailUrl()));
		assertThat(updatedPhoto.getId(),is(createdPhotoId));
	}
	
	private void photoDelete(Photo createdPhoto)
	{
		photoService.delete(createdPhoto.getId());
		Photo photo = photoService.read(createdPhoto.getId());
		assertThat(photo, is(nullValue()));		
	}
	
	private void testAlbumPhotos(Long albumId, int photoCnt){
		List<Photo> albumPhotos = photoService.getAlbumPhotos(albumId);
		assertThat(albumPhotos.size(), is(photoCnt));
	}
}
