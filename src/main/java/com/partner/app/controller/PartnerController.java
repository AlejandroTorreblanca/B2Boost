package com.partner.app.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.partner.app.exceptions.BadRequestError;
import com.partner.app.exceptions.InternalErrorException;
import com.partner.app.exceptions.PartnerNotFoundException;
import com.partner.app.model.Partner;
import com.partner.app.service.PartnerService;

/**
 * Controller of the API.
 * 
 * @author Alejandro Torreblanca
 *
 */
@RequestMapping("api/partners")
@RestController
public class PartnerController {
	
	public final PartnerService partnerService;

	/**
	 * Constructor of the class PartnerController
	 * @param partnerService
	 */
	@Autowired
	public PartnerController(PartnerService partnerService) {
		super();
		this.partnerService = partnerService;
	}
	
	/**
	 * Adds a partner to the data base, the information of the partner is in the body of the HTTP message. 
	 * Response to a HTTP message with POST and url api/partners.
	 * @param partner Partner 
	 * @return HTTP message reply.
	 */
	@PostMapping(produces = "application/json")
	public ResponseEntity<Partner> addPartner(@Valid @NonNull @RequestBody Partner partner) {
		try {
			if(partner.getId()==0) 
				partner.setId(partnerService.getNewID());
			
			partnerService.addPartner(partner);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(partner.getId())
                    .toUri();
			return ResponseEntity.created(location).body(partner);
			
		} catch (InternalErrorException e) {
			throw new ResponseStatusException(
			          HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		} catch (BadRequestError e) {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, e.getMessage(), e);
		}
	}
	
	/**
	 * Gets {size} partners starting with {from}.
	 * Response to a HTTP message with GET and url api/partners/{from}/{size}.
	 * @param from Offset in the resultset to paginate to
	 * @param size Window pagination size
	 * @return List of partners
	 */
	@GetMapping(path = "{from}/{size}")
	public List<Partner> getAllPartners(@PathVariable("from") String from, @PathVariable("size") String size){
		try {
			int f=Integer.parseInt(from);
			int s=Integer.parseInt(size);
			if(f<0 || s<0)
				throw new ResponseStatusException(
				          HttpStatus.BAD_REQUEST, "Numbers 'from' and 'size' must be integers greater than 0.", new Exception());
			return partnerService.getAllPartners(f, s);
		} catch (InternalErrorException e) {
			throw new ResponseStatusException(
			          HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		} catch (NumberFormatException  e) {
			throw new ResponseStatusException(
			          HttpStatus.BAD_REQUEST, "Numbers 'from' and 'size' must be integers.", e);
		}
	}
	
	/**
	 * Gets all the partners.
	 * Response to a HTTP message with GET and url api/partners.
	 * @return List of partners.
	 */
	@GetMapping
	public List<Partner> getAllPartnersDefault(){
		return getAllPartners("0", "10");
	}
	
	/**
	 * Gets the partner with id {id}
	 * Response to a HTTP message with GET and url api/partners/{id}.
	 * @param id ID of the partner.
	 * @return Partner with the specified ID.
	 */
	@GetMapping(path = "{id}")
	public Partner getPartner(@PathVariable("id") long id) {
		try {
			Partner p= partnerService.getPartner(id);
			return p;
		} catch (PartnerNotFoundException e) {
			String error="Partner with ID="+id+" not found in the data base.";
			throw new ResponseStatusException(
			          HttpStatus.NOT_FOUND, error, e);
		} catch (InternalErrorException e) {
			throw new ResponseStatusException(
			          HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
		
	}
	
	/**
	 * Removes the partner with the specified ID.
	 * Response to a HTTP message with DELETE and url api/partners/{id}.
	 * @param id ID of the partner.
	 */
	@DeleteMapping(path = "{id}")
	public void deletePartner(@PathVariable("id") String id) {
		try {
			partnerService.deletePartner(id);
		} catch (PartnerNotFoundException e) {
			String error="Partner with ID="+id+" not found in the data base.";
			throw new ResponseStatusException(
			          HttpStatus.NOT_FOUND, error, e);
		} catch (InternalErrorException e) {
			throw new ResponseStatusException(
			          HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	/**
	 * Updates the information of a partner.
	 * Response to a HTTP message with PUT and url api/partners/{id}.
	 * @param id ID of the partner.
	 * @param partner New information of the partner.
	 */
	@PutMapping(path = "{id}")
	public Partner updatePartner(@PathVariable("id") long id, @Valid @NonNull @RequestBody Partner partner) {
		try {
			partner.setId(id);
			partnerService.updatePartner(partner);
			return partner;
		} catch (PartnerNotFoundException e) {
			String error="Partner with ID="+id+" not found in the data base.";
			throw new ResponseStatusException(
			          HttpStatus.NOT_FOUND, error, e);
		} catch (InternalErrorException e) {
			throw new ResponseStatusException(
			          HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}	
}
