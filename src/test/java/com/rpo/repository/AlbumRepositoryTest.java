package com.rpo.repository;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.rpo.domain.Album;

@RunWith(SpringRunner.class)
public class AlbumRepositoryTest extends BaseRepositoryTest{
	
	private final String albumTitle = "myAlbum";
	private final String albumTitle2 = "myAlbum2";

	private final Long userId = 100L;

	@Test
	public void testAlbumCRUD(){
		Album createdAlbum = albumCreate();
		albumRead(createdAlbum);
		albumReadAll(1);
		albumUpdate(createdAlbum);
		createdAlbum = albumCreate();
		albumReadAll(2);
		albumDelete(createdAlbum);
		albumReadAll(1);	
	}
	
	private Album albumCreate(){
		
		Album album = new Album();
		album.setTitle(albumTitle);
		album.setUserId(userId);
		
		Album createdAlbum = albumRepository.save(album);
		assertThat(createdAlbum.getTitle(),is(albumTitle));
		assertThat(createdAlbum.getUserId(),is(userId));
		assertThat(createdAlbum.getId(),is(notNullValue()));
		
		return createdAlbum;
	}
	
	private void albumRead(Album createdAlbum)
	{
		Long createdAlbumId = createdAlbum.getId();
		Album album = albumRepository.findOne(createdAlbumId);
		assertThat(album.getTitle(),is(createdAlbum.getTitle()));
		assertThat(album.getUserId(),is(createdAlbum.getUserId()));
		assertThat(album.getId(),is(createdAlbumId));
	}
	
	private void albumReadAll(int totalCnt)
	{
		List<Album> albums = (List<Album>) albumRepository.findAll();
		assertThat(albums.size(), is(totalCnt));
	}
	
	private void albumUpdate(Album createdAlbum)
	{
		Long createdAlbumId = createdAlbum.getId();
		Album album = albumRepository.findOne(createdAlbumId);
		
		album.setTitle(albumTitle2);
		Album updatedAlbum = albumRepository.save(album);
		
		assertThat(updatedAlbum.getTitle(),is(albumTitle2));
		assertThat(updatedAlbum.getUserId(),is(createdAlbum.getUserId()));
		assertThat(updatedAlbum.getId(),is(createdAlbumId));
	}
	
	private void albumDelete(Album createdAlbum)
	{
		albumRepository.delete(createdAlbum);
		Album album = albumRepository.findOne(createdAlbum.getId());
		assertThat(album, is(nullValue()));
	}
	
}
