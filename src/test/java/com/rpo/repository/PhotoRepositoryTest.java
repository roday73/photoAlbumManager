package com.rpo.repository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.rpo.domain.Photo;

@RunWith(SpringRunner.class)
public class PhotoRepositoryTest extends BaseRepositoryTest{
	
	private final String photoTitle = "photoTitle";
	private final String photoTitle2 = "photoTitle2";
	private final String photoUrl = "photoUrl";
	private final String photoUrl2 = "photoUr2l";
	private final String photoThumbnailUrl = "photoThumbnailUrl";
	
	@Test
	public void testPhotoCRUD(){
		Photo createdPhoto = photoCreate();
		photoRead(createdPhoto);
		photoReadAll(1);
		photoUpdate(createdPhoto);
		createdPhoto = photoCreate();
		photoReadAll(2);
		photoDelete(createdPhoto);
		photoReadAll(1);	
	}
	
	private Photo photoCreate(){
		
		Photo photo = new Photo();
		photo.setTitle(photoTitle);
		photo.setUrl(photoUrl);
		photo.setThumbnailUrl(photoThumbnailUrl);
		
		Photo createdPhoto = photoRepository.save(photo);
		assertThat(createdPhoto.getTitle(),is(photoTitle));
		assertThat(createdPhoto.getUrl(),is(photoUrl));
		assertThat(createdPhoto.getThumbnailUrl(),is(photoThumbnailUrl));
		assertThat(createdPhoto.getId(),is(notNullValue()));
		
		return createdPhoto;
	}
	
	private void photoRead(Photo createdPhoto)
	{
		Long createdPhotoId = createdPhoto.getId();
		Photo photo = photoRepository.findOne(createdPhotoId);
		assertThat(photo.getTitle(),is(createdPhoto.getTitle()));
		assertThat(photo.getUrl(),is(createdPhoto.getUrl()));
		assertThat(photo.getThumbnailUrl(),is(createdPhoto.getThumbnailUrl()));
		assertThat(photo.getId(),is(createdPhotoId));
	}
	
	private void photoReadAll(int totalCnt)
	{
		List<Photo> photos = (List<Photo>) photoRepository.findAll();
		assertThat(photos.size(), is(totalCnt));
	}
	
	private void photoUpdate(Photo createdPhoto)
	{
		Long createdPhotoId = createdPhoto.getId();
		Photo photo = photoRepository.findOne(createdPhotoId);
		
		photo.setTitle(photoTitle2);
		photo.setUrl(photoUrl2);
		Photo updatedPhoto = photoRepository.save(photo);
		
		assertThat(updatedPhoto.getTitle(),is(photoTitle2));
		assertThat(updatedPhoto.getUrl(),is(photoUrl2));
		assertThat(updatedPhoto.getThumbnailUrl(),is(createdPhoto.getThumbnailUrl()));
		assertThat(updatedPhoto.getId(),is(createdPhotoId));
	}
	
	private void photoDelete(Photo createdPhoto)
	{
		photoRepository.delete(createdPhoto);
		Photo photo = photoRepository.findOne(createdPhoto.getId());
		assertThat(photo, is(nullValue()));		
	}
	
}
