package com.fabiose.utils.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import com.fabiose.utils.domain.Entry; 

public class FileRestTest {
	
	private FileRest fileRest;
	private HttpPost post;
	private HttpClient client;
	private String url;
	private Entry entry;
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	@Before
	public void init() {
		fileRest = new FileRest();
		url = "http://localhost:8089/file";
		post = new HttpPost(url);	
		client = HttpClientBuilder.create().build();
		
	/*	entry = new Entry();
		entry.setFile("employee.csv");
		entry.setBody("SAM,001,2018-11-07,2019-05-07");*/
	}

	@Test
	public void serviceNotFoundTest()throws IOException, Exception {	
		HttpResponse response = client.execute(post);
		
		assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_NOT_FOUND)); 
	}
	
	@Test
	public void writeIOExceptionTest() throws IOException, Exception {	
		thrown.expect(IOException.class);
		fileRest.write("C:\\Users\\fabio.dossantosestre\\eclipse-workspace\\filesystem", "SAM,001,2018-11-07,2019-05-07");
	}
	
	@Test
	public void writeNullPointerExceptionTest() throws IOException, Exception {	
		thrown.expect(Exception.class);
		fileRest.write("C:\\Users\\fabio.dossantosestre\\eclipse-workspace\\filesystem\\employee.csv", null);
	}
}
