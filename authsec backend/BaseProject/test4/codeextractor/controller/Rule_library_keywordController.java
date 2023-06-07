package com.realnet.codeextractor.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.realnet.codeextractor.entity.Rule_library_keyword;
import com.realnet.codeextractor.service.Rule_library_keywordService;


@RequestMapping(value = "/code_extractor/rule_keyword")
@RestController
public class Rule_library_keywordController {

	@Autowired
	private Rule_library_keywordService Service;

	@PostMapping("/Rule_library_keyword")
	public Rule_library_keyword Savedata(@RequestBody Rule_library_keyword data) {
		Rule_library_keyword save = Service.Savedata(data);
		return save;
	}

	@GetMapping("/Rule_library_keyword")
	public List<Rule_library_keyword> getdetails() {
		List<Rule_library_keyword> get = Service.getdetails();
		return get;
	}

	@GetMapping("/Rule_library_keyword/{id}")
	public Rule_library_keyword getdetailsbyId(@PathVariable Integer id) {
		Rule_library_keyword get = Service.getdetailsbyId(id);
		return get;
	}

	@DeleteMapping("/Rule_library_keyword/{id}")
	public void delete_by_id(@PathVariable Integer id) {
		Service.delete_by_id(id);

	}

	@PutMapping("/Rule_library_keyword/{id}")
	public Rule_library_keyword update(@RequestBody Rule_library_keyword data, @PathVariable Integer id) {
		Rule_library_keyword update = Service.update(data, id);
		return update;
	}
}