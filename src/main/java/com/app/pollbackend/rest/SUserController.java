package com.app.pollbackend.rest;

import com.app.pollbackend.config.security.CustomUserDetails;
import com.app.pollbackend.domain.CustomListCollection;
import com.app.pollbackend.domain.SUser;
import com.app.pollbackend.repository.AppDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;


@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Component
public class SUserController {
	@Autowired
	protected AppDao appDao;
	
	@GetMapping("/get/{id}")
	public SUser get(@PathVariable("id") Integer id) {
		return appDao.getUserDao().getOne(id);
	}
	
	@GetMapping("/list")
	public Collection<SUser> list() {
		return appDao.getUserDao().findAll().stream().collect(Collectors.toList());
	}
	
	@GetMapping("/listLazy/{lazyPage}/{lazyCount}")
	public CustomListCollection<SUser> listLazy(@PathVariable("lazyPage") Integer lazyPage, @PathVariable("lazyCount") Integer lazyCount, 
			@RequestParam(required=false, name="username") String username) throws Exception {
		CustomListCollection<SUser> c = new CustomListCollection<SUser>();
		Pageable pageable = PageRequest.of(lazyPage, lazyCount, Sort.by("id").ascending());
		c.setData(appDao.getUserDao().findUserByFilters(username, pageable).stream().collect(Collectors.toList()));
		c.setTotalCount(appDao.getUserDao().getTotalCount());
		return c;
	}
	
	@GetMapping("/listLazyRolesByUserId/{lazyPage}/{lazyCount}")
	public CustomListCollection<SUser> listLazyRolesByUserId(@PathVariable("lazyPage") Integer lazyPage, @PathVariable("lazyCount") Integer lazyCount, 
			@RequestParam(required=false, name="userId") String userId) throws Exception {
		CustomListCollection<SUser> c = new CustomListCollection<SUser>();
		Pageable pageable = PageRequest.of(lazyPage, lazyCount, Sort.by("id").ascending());
		c.setData(appDao.getUserDao().findUserByFilters(userId, pageable).stream().collect(Collectors.toList()));
		c.setTotalCount(appDao.getUserDao().getTotalCount());
		return c;
	}
	
	@PostMapping("/save")
	ResponseEntity<?> save(@RequestBody SUser model) throws Exception {
		SUser u = appDao.getUserDao().save(model);
		if(u != null) {
			return new ResponseEntity<SUser>(u, HttpStatus.CREATED);
		}else {
			return null;
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<SUser> deleteById(@PathVariable("id") Integer id) {
		try {
			appDao.getUserDao().deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<SUser> (HttpStatus.ACCEPTED);
	}

	@GetMapping("/currentUser")
	SUser currentUser() {
		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) oAuth2Authentication.getUserAuthentication();
		CustomUserDetails customUserDetails = (CustomUserDetails) usernamePasswordAuthenticationToken.getPrincipal();
		SUser suser= appDao.getUserDao().findUserByUserName(customUserDetails.getUsername());
		suser.setPassword("*************************");
		return suser;
	}
}
