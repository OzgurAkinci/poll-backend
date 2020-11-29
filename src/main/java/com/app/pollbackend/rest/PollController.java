package com.app.pollbackend.rest;

import com.app.pollbackend.domain.CustomListCollection;
import com.app.pollbackend.domain.Option;
import com.app.pollbackend.domain.Poll;
import com.app.pollbackend.domain.Question;
import com.app.pollbackend.dto.PollResponseDetail;
import com.app.pollbackend.dto.PollResult;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/poll")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Component
public class PollController {
	@Autowired
	protected AppDao appDao;
	
	@GetMapping("/get/{id}")
	public Poll get(@PathVariable("id") Integer id) {
		return appDao.getPollDao().getOne(id);
	}

	@GetMapping("/list")
	public Collection<Poll> list() {
		return new ArrayList<>(appDao.getPollDao().findAll());
	}
	
	@GetMapping("/listLazy/{lazyPage}/{lazyCount}")
	public CustomListCollection<Poll> listLazy(@PathVariable("lazyPage") Integer lazyPage, @PathVariable("lazyCount") Integer lazyCount,
											   @RequestParam(required=false, name="pollTitle") String pollTitle) throws Exception {
		CustomListCollection<Poll> c = new CustomListCollection<Poll>();
		Pageable pageable = PageRequest.of(lazyPage, lazyCount, Sort.by("id").ascending());
		c.setData(appDao.getPollDao().findPollByFilters(pollTitle, pageable).stream().collect(Collectors.toList()));
		c.setTotalCount(appDao.getPollDao().getTotalCount());
		return c;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/save")
	ResponseEntity<?> save(@RequestBody Poll model) throws Exception {
		Poll u = appDao.getPollDao().save(model);
		if(u != null) {
			return new ResponseEntity<Poll>(u, HttpStatus.CREATED);
		}else {
			return null;
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Poll> deleteById(@PathVariable("id") Integer id) {
		try {
			appDao.getPollDao().deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Poll> (HttpStatus.ACCEPTED);
	}

	@GetMapping("/getPollResult/{id}")
	public List<PollResult> getPollResult(@PathVariable("id") Integer id) throws Exception {
		List<PollResult> pollResults = new ArrayList<>();
		List<Question> questions = appDao.getQuestionDao().findByPollId(id);
		for (Question q: questions) {
			PollResult pollResult = new PollResult();
			pollResult.setQuestion(q.getQuestionTitle());
			List<Option> options = new ArrayList<>(q.getOptions());
			List<PollResponseDetail> pollResponseDetails = new ArrayList<>();
			for(Option opt: options) {
				PollResponseDetail responseDetail = new PollResponseDetail();
				responseDetail.setOptionNo(opt.getId().toString());
				responseDetail.setOption(opt.getOptionTitle());
				responseDetail.setResponseCount(appDao.getResponseDao().getCountByOptionId(opt.getId().toString()));
				pollResponseDetails.add(responseDetail);
			}
			pollResult.setResponse(pollResponseDetails);
			pollResults.add(pollResult);
		}
		return pollResults;
	}

}
