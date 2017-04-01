package com.rpo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rpo.domain.Album;
import com.rpo.repository.AlbumRepository;

@Service
public class AlbumServiceImpl implements AlbumService {
	
	@Autowired
	private AlbumRepository albumRepository;
	
	@Override
	public List<Album> initDataSet(List<Album> albums){
		return (List<Album>) albumRepository.save(albums);		
	}
	
	@Override
	public List<Album> list(){
		return (List<Album>) albumRepository.findAll();
	}

	@Override
	public Album save(Album album) {
		return albumRepository.save(album);
	}
	
	@Override
	public Album read(Long id){
		return albumRepository.findOne(id);
	}
	
	@Override
	public void delete(Long id){
		albumRepository.delete(id);
	}

}
