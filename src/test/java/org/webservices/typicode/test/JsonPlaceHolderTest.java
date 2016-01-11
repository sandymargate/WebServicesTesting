package org.webservices.typicode.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JsonPlaceHolderTest {

	String url = "http://jsonplaceholder.typicode.com/posts/2";

	HttpClient client = HttpClientBuilder.create().build();

	@Test(description = "Checks response code")
	public void testResponseCode() throws ClientProtocolException, IOException {

		// Creating object and passing the url
		HttpUriRequest request = new HttpGet(url);

		// Send the response or execute the request
		HttpResponse response = client.execute(request);
		Assert.assertEquals(response.getStatusLine().getStatusCode(),
				HttpStatus.SC_OK, "The request is invalid");
	}

	@Test
	public void testResponse() throws ClientProtocolException, IOException {
		// Creating httpClient object and passing the url
		HttpUriRequest request = new HttpGet(url);

		// Getting http response
		HttpResponse response = client.execute(request);
		System.out.println("Hello");
	}

	@Test(description = "Checks response headers")
	public void testHeaders() throws ClientProtocolException, IOException {

		// Creating httpCLient object and passing the url
		HttpUriRequest request = new HttpGet(url);

		// Getting http response
		HttpResponse response = client.execute(request);
		Header[] headers = response.getAllHeaders();
		HashMap<String, String> responseHead = new HashMap<String, String>();
		for (Header responseHeaders : headers) {
			System.out.println("Response header Name:"
					+ responseHeaders.getName() + "---"
					+ responseHeaders.getValue());
			responseHead.put(responseHeaders.getName(),
					responseHeaders.getValue());
		}

		Assert.assertTrue(response.containsHeader("Content-Type"),
				"Content -type is not matched");
		Assert.assertTrue(
				responseHead.get("Content-Type").contains("application/json"),
				"The JSON response is not matched");
	}

	@SuppressWarnings("deprecation")
	@Test(description = "Checks parameter", enabled = false)
	public void testGetParameters() throws ClientProtocolException, IOException {

		// Creatin httpRequest and passin the url
		HttpUriRequest request = new HttpGet(url);

		// Getting http response
		HttpResponse response = client.execute(request);

		// Checking parameters
		Assert.assertFalse(response.getParams().isParameterTrue("name"),
				"The parameters are true");

	}
}
