package org.webservices.typicode.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class JsonPlaceHolderResponseTest {

	HttpClient client = HttpClientBuilder.create().build();

	String url = "http://jsonplaceholder.typicode.com/posts/2";

	@Test(description = "testin response")
	public void testResponse() throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		/*
		 * BufferedReader br = new BufferedReader(new
		 * InputStreamReader(response.getEntity().getContent())); String li="";
		 * while((li=br.readLine())!=null) { System.out.println(li); }
		 */

		String s = EntityUtils.toString(response.getEntity());
		JSONObject js = new JSONObject(s);
		Iterator<String> key = js.keys();
		while (key.hasNext()) {
			String k = key.next();
			System.out.println(k + " ---- " + js.get(k));
		}
	}

	@Test(description = "Testing request headers")
	public void testRequestHeaders() throws ClientProtocolException,
			IOException {
		HttpUriRequest request = new HttpGet(url);

		// HttpResponse response = client.execute(request);
		System.out.println(request.getAllHeaders());
		Header[] headers = request.getAllHeaders();
		for (Header head : headers) {
			System.out.println(head.toString());
		}
	}

	@Test
	public void testPost() throws ClientProtocolException, IOException,
			ParserConfigurationException, UnsupportedOperationException,
			SAXException {
		String postURL = "http://www.webservicex.net/globalweather.asmx/GetCitiesByCountry";
		HttpPost post = new HttpPost(postURL);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("CountryName", "india"));
		post.setEntity(new UrlEncodedFormEntity(params));

		HttpResponse response = client.execute(post);
		System.out.println("Response Code : "
				+ response.getStatusLine().getStatusCode());

		/*
		 * DocumentBuilderFactory docBuilderFactory =
		 * DocumentBuilderFactory.newInstance(); DocumentBuilder docBuilder =
		 * docBuilderFactory.newDocumentBuilder(); Document doc =
		 * docBuilder.parse(response.getEntity().getContent()); NodeList
		 * nameNodesList = doc.getElementsByTagName("Table"); for(int i=0; i<
		 * nameNodesList.getLength(); i++) {
		 * System.out.println(nameNodesList.item(i).getTextContent()); }
		 */
		BufferedReader br = new BufferedReader(new InputStreamReader(response
				.getEntity().getContent()));
		String li = "";
		while ((li = br.readLine()) != null) {
			System.out.println(li);
		}
	}

}
