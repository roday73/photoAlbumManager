package com.rpo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rpo.domain.Album;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {

}
