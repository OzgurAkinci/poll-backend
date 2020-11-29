package com.app.pollbackend.rest;

import com.app.pollbackend.domain.CustomListCollection;
import com.app.pollbackend.domain.Question;
import com.app.pollbackend.repository.AppDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RequestMapping("/question")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Component
public class QuestionController {
	@Autowired
	protected AppDao appDao;
	
	@GetMapping("/get/{id}")
	public Question get(@PathVariable("id") Integer id) {
		return appDao.getQuestionDao().getOne(id);
	}
	
	@GetMapping("/list")
	public Collection<Question> list() {
		return appDao.getQuestionDao().findAll().stream().collect(Collectors.toList());
	}
	
	@GetMapping("/listLazy/{lazyPage}/{lazyCount}")
	public CustomListCollection<Question> listLazy(@PathVariable("lazyPage") Integer lazyPage, @PathVariable("lazyCount") Integer lazyCount,
												@RequestParam(required=false, name="questionTitle") String questionTitle) throws Exception {
		CustomListCollection<Question> c = new CustomListCollection<Question>();
		Pageable pageable = PageRequest.of(lazyPage, lazyCount, Sort.by("id").ascending());
		c.setData(appDao.getQuestionDao().findQuestionByFilters(questionTitle, pageable).stream().collect(Collectors.toList()));
		c.setTotalCount(appDao.getQuestionDao().getTotalCount());
		return c;
	}
	
	@PostMapping("/save")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	ResponseEntity<?> save(@RequestBody Question model) {
		Question u = appDao.getQuestionDao().save(model);
		if(u != null) {
			return new ResponseEntity<Question>(u, HttpStatus.CREATED);
		}else {
			return null;
		}
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Question> deleteById(@PathVariable("id") Integer id) {
		try {
			appDao.getQuestionDao().deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Question> (HttpStatus.ACCEPTED);
	}
}
