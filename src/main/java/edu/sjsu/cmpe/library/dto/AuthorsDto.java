/**
 * 
 */
package edu.sjsu.cmpe.library.dto;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Author;


/**
 * @author Vijaylakshmi
 *
 */
@JsonPropertyOrder (alphabetic = true)
public class AuthorsDto extends LinksDto{
private ArrayList<Author> authors = new ArrayList<Author>();
	
	public AuthorsDto(ArrayList<Author> authors){
		this.authors = authors;
	}
	
	public void addAuthors (ArrayList<Author> authors){
		authors.addAll(authors);
	}
	

	public void setAuthors(ArrayList<Author> authors){
		this.authors = authors;
	}
	
	public ArrayList<Author> getAuthors(){
		return authors;
	}


}
