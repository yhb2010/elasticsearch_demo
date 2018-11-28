package com.demo.elasticsearch;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.elasticsearch.dao.BookDao;
import com.demo.elasticsearch.domain.Book;
import com.demo.elasticsearch.domain.BookEntity;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ElasticsController {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private BookDao bookDao;

	@GetMapping("/es/book/{id}")
	public Book getbook(@PathVariable int id) throws JsonParseException, IOException{
		Map paras = new HashMap();
		paras.put("id", id);
		String str = restTemplate.getForObject("http://127.0.0.103:9200/product/book/{id}", String.class, paras);
		ObjectMapper mapper = new ObjectMapper();
		JsonFactory factory = mapper.getFactory();
		JsonParser parser = factory.createParser(str);
		JsonNode root = mapper.readTree(parser);
		JsonNode sourceNode = root.get("_source");
		return mapper.convertValue(sourceNode, Book.class);
	}

	@GetMapping("/es/book/msg")
	public List<BookEntity> getbookByMsg() throws JsonParseException, IOException{
		return bookDao.getByMessage("java");
	}

	@GetMapping("/es/book2/{id}")
	public BookEntity getbook2(@PathVariable int id) throws JsonParseException, IOException{
		Optional<BookEntity> op = bookDao.findById(id);
		if(op.isPresent()){
			return op.get();
		}
		return null;
	}

	@GetMapping("/es/book/page/{page}")
	public List<BookEntity> search(@PathVariable int page){
		int size = 2;
		PageRequest request = PageRequest.of(page, size);
		Page<BookEntity> pages = bookDao.getByMessage("java", request);
		long count = pages.getTotalElements();
		return pages.getContent();
	}

}
