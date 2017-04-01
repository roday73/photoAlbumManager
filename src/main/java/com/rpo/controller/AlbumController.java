package com.rpo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.rpo.domain.Album;
import com.rpo.exception.ConstraintViolationException;
import com.rpo.exception.ObjectNotFoundException;
import com.rpo.service.AlbumService;
import com.rpo.service.PhotoService;


@RestController
@RequestMapping("/albums")
public class AlbumController implements BaseController{

	@Autowired
	AlbumService albumService;
	
	@Autowired
	PhotoService photoService;

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Album> getAllAlbums(){
		return albumService.list();
	}
	
	@RequestMapping(method = RequestMethod.POST )
	@ResponseStatus(value=HttpStatus.CREATED)
	public Album create(@Valid @RequestBody Album album){
		Album newAlbum = new Album();
		newAlbum.setTitle(album.getTitle());
		newAlbum.setUserId(album.getUserId());
		return albumService.save(newAlbum);
	}
	
	@RequestMapping(value = "/{albumId}", method = RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	public Album getAlbum(@PathVariable(value="albumId") long albumId) {
		Album existingAlbum = albumService.read(albumId);
		if(existingAlbum != null)
			return existingAlbum;
		else
			throw new ObjectNotFoundException(String.format("album id %s was not found",albumId));
	}

	@RequestMapping(value = "/{albumId}", method = RequestMethod.PUT)
	@ResponseStatus(value=HttpStatus.OK)
	public Album update(@PathVariable(value="albumId") long albumId, @Valid @RequestBody Album album) {
		Album existingAlbum = albumService.read(albumId);
		if (existingAlbum != null) {
			existingAlbum.setTitle(album.getTitle());
			existingAlbum.setUserId(album.getUserId());
			return albumService.save(existingAlbum);
		}
		else
			throw new ObjectNotFoundException(String.format("album id %s was not found",albumId));
	}
	
	@RequestMapping(value = "/{albumId}", method = RequestMethod.DELETE)
	@ResponseStatus(value=HttpStatus.OK)
	public void delete(@PathVariable(value="albumId") long albumId) {
		int numPhotoRecs = photoService.getAlbumPhotos(albumId).size();
		
		// Only proceed if no associated photo records exist
		if(numPhotoRecs == 0){
			try{
				albumService.delete(albumId);
			}
			catch(EmptyResultDataAccessException e){
				throw new ObjectNotFoundException(String.format("album id %s was not found",albumId));
			}
		}
		else
			throw new ConstraintViolationException(String.format("Can't delete album id %s due to %s associated photo records existing",albumId,numPhotoRecs));
		
	}
}
