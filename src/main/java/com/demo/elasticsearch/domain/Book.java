package com.demo.elasticsearch.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Book {

	private String name;
	private String message;
	private Date postDate;

}
