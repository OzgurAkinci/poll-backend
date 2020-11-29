package com.app.pollbackend.rest;

import com.app.pollbackend.domain.Option;
import com.app.pollbackend.repository.AppDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RequestMapping("/option")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Component
public class OptionController {
	@Autowired
	protected AppDao appDao;
	
	@GetMapping("/get/{id}")
	public Option get(@PathVariable("id") Integer id) {
		return appDao.getOptionDao().getOne(id);
	}
	
	@GetMapping("/list")
	public Collection<Option> list() {
		return appDao.getOptionDao().findAll().stream().collect(Collectors.toList());
	}
	
	@PostMapping("/save")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	ResponseEntity<?> save(@RequestBody Option model) {
		Option u = appDao.getOptionDao().save(model);
		if(u != null) {
			return new ResponseEntity<Option>(u, HttpStatus.CREATED);
		}else {
			return null;
		}
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Option> deleteById(@PathVariable("id") Integer id) {
		try {
			appDao.getOptionDao().deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Option> (HttpStatus.ACCEPTED);
	}

	@GetMapping("/getCustom/{id}")
	public Option getCustom(@PathVariable("id") Integer id) throws Exception {
		Option option = appDao.getOptionDao().getOne(id);
		return option;
	}
}
