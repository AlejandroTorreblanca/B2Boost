package com.partner.app.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.Date;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.partner.app.model.Partner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class Test1 {

	// bind the above RANDOM_PORT
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	/**
	 * Tries to get all the partners, we should receive a 200 status with the correct body.
	 * @throws Exception RestClientException 
	 */
	@Test
	public void testGetAllPartners() throws Exception {
		String url = "http://localhost:" + port + "/api/partners";
		ResponseEntity<String> response = restTemplate
				.getForEntity(new URL(url).toString(), String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertThat(response.getBody()).isNotNull().contains("Partner1").contains("Partner2").contains("Partner3").contains("xxxxx2").contains("en_gb").contains("2019-03-03");
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(response.getBody());
		JsonNode id = root.path("id");
		JsonNode ref = root.path("reference");
		JsonNode companyName = root.path("name");
		JsonNode local = root.path("locale");
		JsonNode expires = root.path("expirationTime");
		assertThat(id.asText()).isNotNull();
		assertThat(ref.asText()).isNotNull();
		assertThat(companyName.asText()).isNotNull();
		assertThat(local.asText()).isNotNull();
		assertThat(expires.asText()).isNotNull();
	}
	
	/**
	 * Checks if the response is a JSON.
	 * @throws Exception RestClientException 
	 */
	@Test
	public void testJSONResponse() throws Exception {
		String url = "http://localhost:" + port + "/api/partners";
		HttpHeaders httpHeaders = restTemplate.headForHeaders(url);
		assertTrue(httpHeaders.getContentType().includes(MediaType.APPLICATION_JSON));
	}
	
	/**
	 * Tries to get a partner which exists, we should receive a 200 status with the correct body.
	 * @throws Exception RestClientException 
	 */
	@Test
	public void testGetPartner() throws Exception {
		String url = "http://localhost:" + port + "/api/partners";
		ResponseEntity<String> response = restTemplate
				.getForEntity(new URL(url + "/00002").toString(), String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertThat(response.getBody()).isNotNull().contains("Partner2").contains("xxxxx2").contains("en_gb").contains("2019-03-03");
		
	}
	
	/**
	 * Tries to get a partner which does not exists, we should receive a 404 status.
	 * @throws Exception RestClientException 
	 */
	@Test
	public void testGetNotFound() throws Exception {
		String url = "http://localhost:" + port + "/api/partners";
		ResponseEntity<String> response = restTemplate
				.getForEntity(url + "/00014", String.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	/**
	 * Tries to post a new partner, we should receive a 201 status and check if the data has been saved correctly.
	 * @throws Exception RestClientException
	 */
	@Test
	public void testPostPartner() throws Exception {
		String url = "http://localhost:" + port + "/api/partners";
		Partner partner = new Partner(18, "Bells & Whistles", "xxxxxxx", Locale.ENGLISH, new Date());
		HttpEntity<Partner> request = new HttpEntity<>(partner);
		ResponseEntity<Partner> response = restTemplate
				  .exchange(url, HttpMethod.POST, request, Partner.class);
				  
		assertEquals(response.getStatusCode(), HttpStatus.CREATED);		  
		Partner p = response.getBody();
		assertThat(p).isNotNull();
		assertEquals(partner.getId(), p.getId());
		assertEquals(partner.getCompanyName(), p.getCompanyName());
		assertEquals(partner.getRef(), p.getRef());
		assertEquals(partner.getLocale(), p.getLocale());
		assertEquals(partner.getExpires(), p.getExpires());
	}
	
	/**
	 * Tries to update a partner, we should receive a 200 status and check if the data has been saved correctly.
	 * @throws Exception
	 */
	@Test
	public void testUpdatePartnerNotFound() throws Exception {
		String url = "http://localhost:" + port + "/api/partners";
		Partner partner = new Partner(7, "Bells & Whistles", "xxxxxxx", Locale.ENGLISH, new Date());
		HttpEntity<Partner> request = new HttpEntity<>(partner);
		restTemplate.exchange(url, HttpMethod.POST, request, Partner.class);
		
		partner.setCompanyName("New name");
		String resourceUrl = url + "/" + partner.getId();
		HttpEntity<Partner> requestUpdate = new HttpEntity<>(partner);
		ResponseEntity<Partner> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Partner.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		Partner p = response.getBody();
		assertThat(p).isNotNull();
		assertEquals(partner.getCompanyName(), p.getCompanyName());
	}
	
	/**
	 * Tries to update a partner, we should receive a 200 status and check if the data has been saved correctly.
	 * @throws Exception
	 */
	@Test
	public void testUpdatePartner() throws Exception {
		String url = "http://localhost:" + port + "/api/partners";
		Partner partner = new Partner(7, "Bells & Whistles", "xxxxxxx", Locale.ENGLISH, new Date());
		HttpEntity<Partner> request = new HttpEntity<>(partner);
		restTemplate.exchange(url, HttpMethod.POST, request, Partner.class);
		
		partner.setCompanyName("New name");
		String resourceUrl = url + "/00008";
		HttpEntity<Partner> requestUpdate = new HttpEntity<>(partner);
		ResponseEntity<Partner> response = restTemplate.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Partner.class);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	/**
	 * Tries to delete a partner.
	 * @throws Exception
	 */
	@Test
	public void testDeletePartner() throws Exception {
		String url = "http://localhost:" + port + "/api/partners/00002";
		restTemplate.delete(url);		  
	}
	

}
