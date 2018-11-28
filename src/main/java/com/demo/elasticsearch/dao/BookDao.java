package com.demo.elasticsearch.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.demo.elasticsearch.domain.BookEntity;

public interface BookDao extends CrudRepository<BookEntity, Integer> {

	//这是标准的spring data命名，意思是elastic data会查询message字段
	public List<BookEntity> getByMessage(String key);

	public Page<BookEntity> getByMessage(String key, Pageable pageable);

}
