package com.example.demo.api;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.Foo;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UploadApi {
	
	private final Logger logger = LoggerFactory.getLogger(UploadApi.class);
	
	@GetMapping(path="/")
	public ResponseEntity<String> home() {
		return new ResponseEntity<String>("Hello", HttpStatus.OK);
	}
	
	// 3.1.1 Single file upload
	/**
	 * 
	 * extraField ---> {"a":"alpha","b":"bravo"}
	 * 
	 * @param extraField
	 * @param uploadfile
	 * @return
	 * @throws Exception
	 */
    @PostMapping("/singleUpload")
    public ResponseEntity<?> uploadFile(
    		@RequestParam("extraField") String extraField,
    		@RequestParam("file") MultipartFile uploadfile) throws Exception {

        logger.debug("Single file upload!");
        
        Foo foo = new ObjectMapper().readValue(extraField, Foo.class);
        logger.info("!!!!!! extraField: {} !!!!!", foo.toString());

        if (uploadfile.isEmpty()) {
            return new ResponseEntity<>("please select a file!", HttpStatus.OK);
        }

        return new ResponseEntity<>("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

    }
	
    /* !!! NOT WORKING 
	@PostMapping(path="/mixedUpload", consumes={"multipart/mixed"})
	public ResponseEntity<?> create(
			@RequestPart("foo") Foo foo,
	        @RequestPart("image") MultipartFile image) {
		
		logger.debug("Single file upload!");
	    
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	*/

}
