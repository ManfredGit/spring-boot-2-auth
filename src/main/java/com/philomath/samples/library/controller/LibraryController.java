package com.philomath.samples.library.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.philomath.samples.library.service.ILibraryService;
import com.philomath.samples.library.util.LibraryException;


@RestController
public class LibraryController {

	@Autowired
	private ILibraryService libraryService;
	
	@RequestMapping(path = "/add", method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addBook(@RequestBody Map<String, Object> input) throws LibraryException	{
		
		int bookId = libraryService.addBook(input);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("status", "success");
		retMap.put("id", bookId);
		
		
		ResponseEntity<Map<String, Object>> retValue = new ResponseEntity<Map<String,Object>>(retMap, HttpStatus.OK);
		return retValue;
	}

	@RequestMapping(path = "/role", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getRole() throws LibraryException {
		// dummy method
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		retMap.put("role", libraryService.getRole());
		
		ResponseEntity<Map<String, Object>> retValue = new ResponseEntity<Map<String,Object>>(retMap, HttpStatus.OK);
		return retValue;
	}
	
	@RequestMapping(path = "/search", method = RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> search(@RequestBody Map<String, Object> input) throws LibraryException {

		//assuming we are searching by book name
		String searchString = (String) (input.get("name"));
		
		// for now we dont support searches so we always return book not found
		List<String> bookIsbns = libraryService.searchBook(input);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("results", bookIsbns);
		
		ResponseEntity<Map<String, Object>> retValue = new ResponseEntity<Map<String,Object>>(retMap, HttpStatus.OK);
		return retValue;
	}
	
}
