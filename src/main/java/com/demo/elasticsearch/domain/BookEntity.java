package com.demo.elasticsearch.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Document(indexName="product", type="book")
public class BookEntity {

	@Id
	private Integer id;
	private String name;
	private String message;
	private Date postDate;

}
