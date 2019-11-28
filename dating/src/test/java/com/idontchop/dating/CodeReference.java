package com.idontchop.dating;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Class file to store code to reference.
 * 
 * Not for testing or production.
 * 
 * @author Nathaniel J Dunn <idontchop.com>
 *
 */
public class CodeReference {

	@RequestMapping ( "/response-test")
	public ResponseEntity<String> responseTest ( @RequestParam(name = "type", required = false) String type ) {
		if ( type == null ) {
			return new ResponseEntity<>( "none", HttpStatus.BAD_REQUEST );
		} else {
			return new ResponseEntity<>("Succces - " + type, HttpStatus.OK);
		}
	}
	
}
