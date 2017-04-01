package com.rpo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rpo.domain.Photo;
import com.rpo.repository.PhotoRepository;

@Service
public class PhotoServiceImpl implements PhotoService {

	@Autowired
	private PhotoRepository photoRepository;

	@Override
	public List<Photo> initDataSet(List<Photo> photos) {
		return (List<Photo>) photoRepository.save(photos);
	}
	
	@Override
	public List<Photo> list(){
		return (List<Photo>) photoRepository.findAll();
	}
	
	@Override
	public Photo save(Photo photo){
		return photoRepository.save(photo);
	}
	
	@Override
	public Photo read(Long id){
		return photoRepository.findOne(id);
	}
	
	@Override
	public void delete(Long id){
		photoRepository.delete(id);
	}
	
	@Override
	public List<Photo> getAlbumPhotos(Long albumId){
		return photoRepository.findAllByAlbumIdOrderByTitleAsc(albumId);
	}
}
