package com.philomath.samples.library.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.philomath.samples.library.util.LibraryConstants;
import com.philomath.samples.library.util.LibraryException;

@Component
public class LibraryService implements ILibraryService {
	
	public int addBook(Map<String, Object> book)	throws LibraryException	{
		return 1;
	}
	
	public List<String> searchBook(Map<String, Object> searchParams) throws LibraryException	{
		return null;
	}

	@Override
	public String getRole() throws LibraryException {
		ArrayList<? extends GrantedAuthority> roles = (ArrayList<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		
		// this is simplistic because we have only one role per user right now
		if (!roles.isEmpty() && roles.get(0).getAuthority().equals(LibraryConstants.ROLE_LIBRARIAN))
			return LibraryConstants.ROLE_LIBRARIAN;
		else
			return LibraryConstants.ROLE_PUBLIC;
		
	}
}
