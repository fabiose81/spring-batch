package com.fabiose.utils.rest;

import java.io.IOException;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import com.fabiose.utils.domain.Entry; 

import static org.hamcrest.CoreMatchers.equalTo; 
import static org.junit.Assert.assertThat;

public class FileRestTest {
	
	private FileRest fileRest;
	private String url;
	private Entry entry;	
	private HttpHeaders requestHeaders;
	private HttpEntity<Entry> requestEntity; 
	private RestTemplate restTemplate;	
	private ResponseEntity<Entry> responseEntity;
		
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void init() {
		fileRest = new FileRest();
		
		entry = new Entry();
		entry.setHeader("name,project,start,end\n");
		entry.setFile("employee.csv");
		entry.setBody("SAM,001,2018-11-07,2019-05-07");	
			
		requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application","json"));
		
		requestEntity = new HttpEntity<Entry>(entry, requestHeaders);
		
		restTemplate = new RestTemplate(); 
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());	
	}
 
	@Test
	public void saveTest() throws IOException, Exception{
		url = "http://localhost:8089/file/save";
		responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Entry.class);
		assertThat(Integer.valueOf(responseEntity.getStatusCode().toString()), equalTo(HttpStatus.SC_OK)); 
	}
	
	@Test
	public void writeIOExceptionTest() throws IOException, Exception {	
		thrown.expect(IOException.class);
		fileRest.write("C:\\Users\\fabio.dossantosestre\\eclipse-workspace\\filesystem", entry.getBody());
	}
	
	@Test
	public void writeNullPointerExceptionTest() throws IOException, Exception {	
		thrown.expect(Exception.class);
		fileRest.write("C:\\Users\\fabio.dossantosestre\\eclipse-workspace\\filesystem\\employee.csv", null);
	}

	@Test
	public void deleteTest() throws IOException, Exception{
		url = "http://localhost:8089/file/delete";
		responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, Entry.class);
		assertThat(Integer.valueOf(responseEntity.getStatusCode().toString()), equalTo(HttpStatus.SC_OK)); 
	}

}
