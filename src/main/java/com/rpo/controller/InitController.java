package com.rpo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.rpo.domain.Album;
import com.rpo.domain.AlbumDTO;
import com.rpo.domain.Photo;
import com.rpo.domain.PhotoDTO;
import com.rpo.service.AlbumService;
import com.rpo.service.AlbumServiceImpl;
import com.rpo.service.PhotoService;

@RestController
public class InitController {
	private static final Log LOG = LogFactory.getLog(InitController.class);
	
	@Autowired
	AlbumService albumService;
	
	@Autowired
	PhotoService photoService;

	/**
	 * Loads data retrieved from external REST call into db
	 */
	@RequestMapping(method = RequestMethod.GET, value ="/init")
	public void initData(){
    	RestTemplate restTemplate = new RestTemplate();
    	
    	// Retrieve albums in JSON format and convert to album DTO array
    	AlbumDTO[] albums = restTemplate.getForObject("https://jsonplaceholder.typicode.com/albums", AlbumDTO[].class);
    	
    	// Retrieve photos in JSON format and convert to photo DTO array
    	PhotoDTO[] photos = restTemplate.getForObject("https://jsonplaceholder.typicode.com/photos", PhotoDTO[].class);

    	// Initialize the objects in the db
    	initData(albums,photos);
	}
	
	private void initData(AlbumDTO[] albumsDTOs,PhotoDTO[] photoDTOs) {
		List<Album> albums = new ArrayList<Album>();
		List<Photo> photos = new ArrayList<Photo>();
		
		// Loop through album DTO list, init album objects and add to list
		for(AlbumDTO albumDTO:albumsDTOs){
			Album album = new Album();
			album.setId(albumDTO.getId());
			album.setTitle(albumDTO.getTitle());
			album.setUserId(albumDTO.getUserId());
			albums.add(album);
		}
		
		// Init albums in db and retrieve created object list
		List<Album> initAlbums = albumService.initDataSet(albums);
    	LOG.info(initAlbums.size()+" album records of ("+albums.size()+") initialized");
		
		// Create albumId-album map
		Map<Long, Album> albumMap = initAlbums.stream()
				.collect(Collectors.toMap(Album::getId, a -> a));
		
		// Loop through photo DTO list, init photo objects and add to list
		for(PhotoDTO photoDTO:photoDTOs){
			Photo photo = new Photo();
			photo.setId(photoDTO.getId());
			photo.setTitle(photoDTO.getTitle());
			photo.setUrl(photoDTO.getUrl());
			photo.setThumbnailUrl(photoDTO.getThumbnailUrl());
			photo.setAlbum(albumMap.get(photoDTO.getAlbumId())); // grab album from map
			photos.add(photo);
		}
		
		// Init photos in db
		List<Photo> initPhotos = photoService.initDataSet(photos);
    	LOG.info(initPhotos.size()+" photo records of ("+photos.size()+") initialized");
	}
	
	@RequestMapping("/")
	public String hello(){
    	return "Photo Album Manager running";
	}
}
