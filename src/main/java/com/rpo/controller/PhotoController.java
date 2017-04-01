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
import com.rpo.domain.Photo;
import com.rpo.exception.ObjectNotFoundException;
import com.rpo.service.AlbumService;
import com.rpo.service.PhotoService;


@RestController
@RequestMapping("/photos")
public class PhotoController implements BaseController{

	@Autowired
	PhotoService photoService;
	
	@Autowired
	AlbumService albumService;
	

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Photo> getAllPhotos(){
		return photoService.list();
	}
	
	@RequestMapping(value = "/album/{albumId}", method = RequestMethod.POST)
	@ResponseStatus(value=HttpStatus.CREATED)
	public Photo create(@PathVariable(value="albumId") long albumId, @Valid @RequestBody Photo photo){
		Album album = albumService.read(albumId);
		if(album != null){
			Photo newPhoto = new Photo();
			newPhoto.setAlbum(album);
			newPhoto.setTitle(photo.getTitle());
			newPhoto.setUrl(photo.getUrl());
			newPhoto.setThumbnailUrl(photo.getThumbnailUrl());
			return photoService.save(newPhoto);
		}
		else
			throw new ObjectNotFoundException(String.format("photo could not be created for album id %s, as it was not found",albumId));	
	}
	
	@RequestMapping(value = "/{photoId}", method = RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	public Photo getPhoto(@PathVariable(value="photoId") long photoId){
		Photo existingPhoto = photoService.read(photoId);
		if(existingPhoto != null)
			return existingPhoto;
		else
			throw new ObjectNotFoundException(String.format("photo id %s was not found",photoId));
	}
	
	@RequestMapping(value = "/album/{albumId}", method = RequestMethod.GET)
	@ResponseStatus(value=HttpStatus.OK)
	public Iterable<Photo> getAlbumPhotos(@PathVariable(value="albumId") long albumId){
		Album existingAlbum = albumService.read(albumId);
		if (existingAlbum != null) {
			return photoService.getAlbumPhotos(albumId);
		}
		else
			throw new ObjectNotFoundException(String.format("album id %s was not found",albumId));
	}

	@RequestMapping(value = "/{photoId}", method = RequestMethod.PUT)
	@ResponseStatus(value=HttpStatus.OK)
	public Photo update(@PathVariable(value="photoId") long photoId, @Valid @RequestBody Photo photo) {
		Photo existingPhoto = photoService.read(photoId);
		if (existingPhoto != null) {
			existingPhoto.setTitle(photo.getTitle());
			existingPhoto.setUrl(photo.getUrl());
			existingPhoto.setThumbnailUrl(photo.getThumbnailUrl());
			return photoService.save(existingPhoto);
		}
		else
			throw new ObjectNotFoundException(String.format("photo id %s was not found",photoId));
	}
	
	@RequestMapping(value = "/{photoId}", method = RequestMethod.DELETE)
	@ResponseStatus(value=HttpStatus.OK)
	public void delete(@PathVariable(value="photoId") long photoId) {
		try{
			photoService.delete(photoId);		
		}
		catch(EmptyResultDataAccessException e){
			throw new ObjectNotFoundException(String.format("photo id %s was not found",photoId));
		}
	}
}
