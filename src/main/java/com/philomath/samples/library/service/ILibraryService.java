package com.philomath.samples.library.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import com.philomath.samples.library.util.LibraryException;

@Component
public interface ILibraryService {
	
	public List<String> searchBook(Map<String, Object> searchParams) throws LibraryException;
	
	public int addBook(Map<String, Object> book)	throws LibraryException;
	
	public String getRole() throws LibraryException;

}
