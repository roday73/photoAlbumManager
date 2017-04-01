package com.rpo.service;

import java.util.List;

import com.rpo.domain.Photo;

public interface PhotoService {

	List<Photo> initDataSet(List<Photo> photos);

	List<Photo> list();

	Photo save(Photo photo);

	Photo read(Long id);

	void delete(Long id);

	List<Photo> getAlbumPhotos(Long albumId);

}