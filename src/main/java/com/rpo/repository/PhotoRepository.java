package com.rpo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rpo.domain.Photo;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, Long>{
	
	List<Photo> findAllByAlbumIdOrderByTitleAsc(Long id);
}
