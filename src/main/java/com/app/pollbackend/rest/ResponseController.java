package com.app.pollbackend.rest;

import com.app.pollbackend.domain.Response;
import com.app.pollbackend.repository.AppDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RequestMapping("/response")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Component
public class ResponseController {
	@Autowired
	protected AppDao appDao;
	
	@GetMapping("/get/{id}")
	public Response get(@PathVariable("id") Integer id) {
		return appDao.getResponseDao().getOne(id);
	}
	
	@GetMapping("/list")
	public Collection<Response> list() {
		return appDao.getResponseDao().findAll().stream().collect(Collectors.toList());
	}
	
	@PostMapping("/save")
	ResponseEntity<?> save(@RequestBody Response model) {
		Response u = appDao.getResponseDao().save(model);
		if(u != null) {
			return new ResponseEntity<Response>(u, HttpStatus.CREATED);
		}else {
			return null;
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> deleteById(@PathVariable("id") Integer id) {
		try {
			appDao.getResponseDao().deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Response> (HttpStatus.ACCEPTED);
	}
}
