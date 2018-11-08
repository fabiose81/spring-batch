package com.fabiose.utils.rest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
 
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fabiose.utils.domain.Entry;

@RestController
@RequestMapping("/file")
public class FileRest {
	
	@ConfigurationProperties(prefix = "app", ignoreInvalidFields = false)
	@RequestMapping(value="save", method = RequestMethod.POST)
	public void save(@Value("${app.path}") final String path, @RequestBody final Entry entry) throws IOException, Exception{
		
		String pathResource = path.concat("\\").concat(entry.getFile()); 
			
		if (!Files.exists(Paths.get(pathResource), new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
			Files.createFile(Paths.get(pathResource));
			write(pathResource,entry.getHeader());
		}
			
		write(pathResource,entry.getBody());	
	}
	
	private void write(final String pathResource, final String body) throws IOException, Exception{
		Files.write(
			      Paths.get(pathResource), 
			      body.getBytes(), 
			      StandardOpenOption.APPEND);
	}

	
	@ConfigurationProperties(prefix = "app", ignoreInvalidFields = false)
	@RequestMapping(value="delete", method = RequestMethod.POST)
	public void delete(@Value("${app.path}") final String path, @RequestBody final Entry entry) throws IOException, Exception{
				
		String pathResource = path.concat("\\").concat(entry.getFile()); 
			
		if (Files.exists(Paths.get(pathResource), new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) 
			Files.delete(Paths.get(pathResource));
					
	}
}
