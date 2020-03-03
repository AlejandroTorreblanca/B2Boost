package com.partner.app.model;

import java.util.Date;
import java.util.Locale;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This class represents a Partner.
 * 
 * @author Alejandro Torreblanca
 *
 */
public class Partner {
	private long id;
	@NotBlank
	private String companyName;
	@NotBlank
	private String ref;
	private Locale locale;
	private Date expires;
	
	/**
	 * Constructor of the class Partner.
	 * @param id ID.
	 * @param companyName Company's name.
	 * @param ref Reference number.
	 * @param locale Locale.
	 * @param expires Expires date.
	 */
	public Partner(@JsonProperty("id") long id, 
			@JsonProperty("name") String companyName,
			@JsonProperty("reference") String ref, 
			@JsonProperty("locale") Locale locale, 
			@JsonProperty("expirationTime") Date expires) {
		super();
		this.companyName = companyName;
		this.ref = ref;
		this.locale = locale;
		this.expires = expires;
		this.id=id;

	}
	
	
	/**
	 * Sets the ID of the partner
	 * @param id New ID
	 */
	public void setId(long id) {
		this.id = id;
	}



	/**
	 * Gets the id of the partner.
	 * @return partner's id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Gets the company's name of the partner.
	 * @return company's name.
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Gets the reference of the partner.
	 * @return partner's reference.
	 */
	public String getRef() {
		return ref;
	}

	/**
	 * Gets the partner's locale.
	 * @return partner's locale.
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Get the partner's expires time.
	 * @return partner's expires time.
	 */
	public Date getExpires() {
		return expires;
	}

}
