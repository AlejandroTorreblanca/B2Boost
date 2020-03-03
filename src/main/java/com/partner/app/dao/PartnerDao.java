package com.partner.app.dao;

import java.util.List;

import com.partner.app.exceptions.BadRequestError;
import com.partner.app.exceptions.InternalErrorException;
import com.partner.app.exceptions.PartnerNotFoundException;
import com.partner.app.model.Partner;

/**
 * Interface for the functions used to interact with the database.
 * 
 * @author Alejandro Torreblanca
 *
 */
public interface PartnerDao {
	
	/**
	 * Gets a new ID for a partner.
	 * @return New ID.
	 * @throws InternalErrorException Error with the database query.
	 */
	int getNewID() throws InternalErrorException;
	
	/**
	 * Insert a partner in the database.
	 * @param Partner Partner which is going to be added to the database.
	 * @return Returns the number 1 if the insertion is correct.
	 * @throws InternalErrorException Error with the database insertion.
	 * @throws BadRequestError The partner already exists.
	 */
	int insertPartner(Partner partner) throws InternalErrorException, BadRequestError;
	
	/**
	 * Gets all the partners from the database.
	 * @param from Offset in the resultset to paginate to.
	 * @param size Window pagination size.
	 * @return List of partners.
	 * @throws InternalErrorException Error with the database query.
	 */
	List<Partner> selectAllPartners(int from, int size) throws InternalErrorException;
	
	/**
	 * Gets the partner with the selected ID.
	 * @param id ID of the partner.
	 * @return Partner with the selected ID.
	 * @throws PartnerNotFoundException There is no partner with the ID.
	 * @throws InternalErrorException Error with the database query.
	 */
	Partner selectPartner(long id) throws PartnerNotFoundException, InternalErrorException ;
	
	/**
	 * Removes a partner from the database.
	 * @param id ID of the partner.
	 * @return returns the number 1 if the elimination is correct.
	 * @throws PartnerNotFoundException There is no partner with the ID.
	 * @throws InternalErrorException Error with the database update.
	 */
	int deletePartner(String id) throws PartnerNotFoundException, InternalErrorException;
	
	/**
	 * Updates the information of a partner.
	 * @param id ID of the partner.
	 * @param partner New data of the partner.
	 * @return returns the number 1 if the update is correct.
	 * @throws PartnerNotFoundException There is no partner with the ID.
	 * @throws InternalErrorException Error with the database update.
	 */
	int updatePartner(String id, Partner partner) throws PartnerNotFoundException, InternalErrorException;
	
}
