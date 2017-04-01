package com.rpo.service;

import java.util.List;

import com.rpo.domain.Album;

public interface AlbumService {

	List<Album> initDataSet(List<Album> albums);

	List<Album> list();

	Album save(Album album);

	Album read(Long id);

	void delete(Long id);
}