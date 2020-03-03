package com.partner.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.partner.app.dao.PartnerDao;
import com.partner.app.exceptions.BadRequestError;
import com.partner.app.exceptions.InternalErrorException;
import com.partner.app.exceptions.PartnerNotFoundException;
import com.partner.app.model.Partner;

/**
 * Service layer of the API.
 * 
 * @author Alejandro Torreblanca
 *
 */
@Service
public class PartnerService {
	
	private final PartnerDao partnerDao;
	
	/**
	 * Constructor of the class PartnerService.
	 * @param partnerDao Interface to interact with the database.
	 */
	@Autowired
	public PartnerService(@Qualifier("fakeDao") PartnerDao partnerDao) {
		super();
		this.partnerDao = partnerDao;
	}
	
	/**
	 * Gets a new ID for a partner.
	 * @return New ID.
	 * @throws InternalErrorException Error with the database query.
	 */
	public int getNewID() throws InternalErrorException {
		return partnerDao.getNewID();
	}

	/**
	 * Adds a partner to the database.
	 * @param partner Partner which is going to be added.
	 * @return Returns the number 1 if the insertion is correct.
	 * @throws InternalErrorException Error with the database insertion.
	 * @throws BadRequestError The partner already exists.
	 */
	public int addPartner(Partner partner) throws InternalErrorException, BadRequestError {
		return partnerDao.insertPartner(partner);
	}
	
	/**
	 * Gets all the partners from the database.
	 * @param from Offset in the resultset to paginate to.
	 * @param size Window pagination size.
	 * @return List of partners.
	 * @throws InternalErrorException Error with the database query.
	 */
	public List<Partner> getAllPartners(int from, int size) throws InternalErrorException{
		return partnerDao.selectAllPartners(from,size);
	}
	
	/**
	 * Gets the partner with the selected ID.
	 * @param id ID of the partner.
	 * @return Partner with the selected ID.
	 * @throws PartnerNotFoundException There is no partner with the ID.
	 * @throws InternalErrorException Error with the database query.
	 */
	public Partner getPartner(long id) throws PartnerNotFoundException, InternalErrorException {
		return partnerDao.selectPartner(id);
	}
	
	/**
	 * Removes a partner from the database.
	 * @param id ID of the partner.
	 * @return returns the number 1 if the elimination is correct.
	 * @throws PartnerNotFoundException There is no partner with the ID.
	 * @throws InternalErrorException Error with the database update.
	 */
	public int deletePartner(String id) throws PartnerNotFoundException, InternalErrorException {
		return partnerDao.deletePartner(id);
	}
	
	/**
	 * Updates the information of a partner.
	 * @param partner New data of the partner.
	 * @return returns the number 1 if the update is correct.
	 * @throws PartnerNotFoundException There is no partner with the ID.
	 * @throws InternalErrorException Error with the database update.
	 */
	public int updatePartner(Partner partner) throws PartnerNotFoundException, InternalErrorException {
		return partnerDao.updatePartner( partner);
	}
}
