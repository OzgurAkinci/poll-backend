package com.app.pollbackend.rest;

import com.app.pollbackend.config.security.CustomUserDetails;
import com.app.pollbackend.domain.Option;
import com.app.pollbackend.domain.Poll;
import com.app.pollbackend.domain.Question;
import com.app.pollbackend.domain.SUser;
import com.app.pollbackend.dto.JoinResultOption;
import com.app.pollbackend.dto.JoinResultQuestion;
import com.app.pollbackend.repository.AppDao;
import org.springframework.beans.factory.annotation.Autowired;
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

}
