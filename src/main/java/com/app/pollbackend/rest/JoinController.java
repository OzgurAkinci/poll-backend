package com.app.pollbackend.rest;

import com.app.pollbackend.config.security.CustomUserDetails;
import com.app.pollbackend.domain.*;
import com.app.pollbackend.dto.JoinResultOption;
import com.app.pollbackend.dto.JoinResultQuestion;
import com.app.pollbackend.repository.AppDao;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/join")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Component
public class JoinController {
	@Autowired
	protected AppDao appDao;

	@GetMapping("/get/{pollId}")
	public List<JoinResultQuestion> get(@PathVariable("pollId") Integer pollId) throws Exception {
		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) oAuth2Authentication.getUserAuthentication();
		CustomUserDetails customUserDetails = (CustomUserDetails) usernamePasswordAuthenticationToken.getPrincipal();
		SUser suser= appDao.getUserDao().findUserByUserName(customUserDetails.getUsername());

		List<JoinResultQuestion> jrqs = new ArrayList<>();
		List<Question> questions = appDao.getQuestionDao().findByPollId(pollId);
		for (Question question: questions) {
			JoinResultQuestion jrq = new JoinResultQuestion();
			jrq.setQuestionId(question.getId());
			jrq.setQuestionTitle(question.getQuestionTitle());
			jrq.setQuestionType(question.getQuestionType());
			List<JoinResultOption> jros = new ArrayList<>();
			for (Option option: question.getOptions()) {
				JoinResultOption jro = new JoinResultOption();
				jro.setOptionId(option.getId());
				jro.setOptionTitle(option.getOptionTitle());
				jro.setSelected(appDao.getResponseDao().getCountByOptionIdAndUserIdAndQuestionId(suser.getId(), question.getId(), option.getId().toString()) > 0);
				jros.add(jro);
			}
			jrq.setOptions(jros);
			jrqs.add(jrq);
		}
		return jrqs;
	}

	@PostMapping("/save")
	ResponseEntity<?> save(@RequestBody List<JoinResultQuestion> jrqs) {
		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) oAuth2Authentication.getUserAuthentication();
		CustomUserDetails customUserDetails = (CustomUserDetails) usernamePasswordAuthenticationToken.getPrincipal();
		SUser suser= appDao.getUserDao().findUserByUserName(customUserDetails.getUsername());

		for(int i=0; i<jrqs.size(); i++) {
			JoinResultQuestion jrq = jrqs.get(i);
			List<JoinResultOption> jros = jrq.getOptions();
			JSONArray selectedOptions = new JSONArray();
			for(int j=0; j<jros.size(); j++) {
				JoinResultOption jro = jros.get(j);
				if(jro.getSelected()) {
					selectedOptions.add(jro.getOptionId());
				}
			}
			Response response = appDao.getResponseDao().findByQuestionIdAndUserId(jrq.getQuestionId(), suser.getId());
			if(response != null){
				response.setResponse(selectedOptions.toJSONString());
				appDao.getResponseDao().save(response);
			}else {
				Response newResponse = new Response();
				newResponse.setUser(suser);
				newResponse.setQuestion(appDao.getQuestionDao().findById(jrq.getQuestionId()).get());
				newResponse.setResponse(selectedOptions.toJSONString());
				appDao.getResponseDao().save(newResponse);
			}
		}
		return new ResponseEntity<>(jrqs, HttpStatus.CREATED);
	}

}
