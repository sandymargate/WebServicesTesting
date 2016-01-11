package or.webservices.getcurrencybycountry.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class PostRequestTest {

	HttpClient client = HttpClientBuilder.create().build();
	//DefaultHttpClient client = new DefaultHttpClient();
	Properties getCurrencyProperties = new Properties();
	String url = null;
	String country = null;

	@BeforeClass
	public void loadProperties() throws IOException {
		String path = (System.getProperty("user.dir") + "/src/test/resources/GetCurrenciesByCountry.properties");
		InputStream filePath = new FileInputStream(path);
		getCurrencyProperties.load(filePath);
	}

	@BeforeMethod
	public void settingURL() {
		url = getCurrencyProperties.getProperty("url");
		country = getCurrencyProperties.getProperty("countryName");
	}

	@Test(description = "Testing post requests")
	public void testPost() throws ClientProtocolException, IOException, ParserConfigurationException, UnsupportedOperationException, SAXException {
		HttpPost post = new HttpPost(url);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("CountryName", country));
		post.setEntity(new UrlEncodedFormEntity(params));

		//Setting response object
		HttpResponse response = client.execute(post);
		
		//Verifying headers
		Header[] headers = response.getAllHeaders();
		for (Header head : headers) {
			System.out.println(head.getName() + "---" + head.getValue());
		}
		System.out.println("================================================");

		// Testing response
		// parsing xml
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(response.getEntity().getContent());

		// Printing root
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("string");
		Node aNode = nList.item(0);
		Element element = (Element) aNode;
		System.out.println(element.getFirstChild());

	}

}

