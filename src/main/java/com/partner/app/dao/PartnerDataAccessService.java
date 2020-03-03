	package com.partner.app.dao;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.partner.app.exceptions.BadRequestError;
import com.partner.app.exceptions.InternalErrorException;
import com.partner.app.exceptions.PartnerNotFoundException;
import com.partner.app.model.Partner;

/**
 * Implements the interface PartnerDao, defines all the functions which interact with the database.
 * @author Alejandro Torreblanca
 *
 */
@Repository("fakeDao")
public class PartnerDataAccessService implements PartnerDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int insertPartner(Partner partner) throws InternalErrorException, BadRequestError {
		String query = "SELECT * FROM PARTNER WHERE ID = ?";
		try {
			jdbcTemplate.queryForObject(query, new Object[] { partner.getId() }, new PartnerRowMapper());
			throw new BadRequestError("Partner already exists.");
		} catch (EmptyResultDataAccessException e1) {
			try {
				jdbcTemplate.update("INSERT INTO PARTNER VALUES (?, ?, ?, ?, ?)", partner.getId(), partner.getCompanyName(),
						partner.getRef(), partner.getLocale().toLanguageTag(), partner.getExpires());
				return 1;
			} catch (DataAccessException e2) {
				throw new InternalErrorException(e2.getMessage());
			}
		}
	}

	@Override
	public List<Partner> selectAllPartners(int from, int size) throws InternalErrorException {
		String query = "SELECT * FROM PARTNER";
		try {
			List<Partner> list = jdbcTemplate.query(query, new PartnerRowMapper());
			List<Partner> aux = new LinkedList<Partner>();
			for (int i = from; i<list.size() && i < from+size ; i++) {
				aux.add(list.get(i));
			}
			return aux;
		} catch (DataAccessException e) {
			throw new InternalErrorException(e.getMessage());

		}
	}
	
	@Override
	public int getNewID() throws InternalErrorException {
		String query = "SELECT MAX(ID) FROM PARTNER";
		try {
			int i = jdbcTemplate.queryForObject(query, Integer.class);
			return i + 1;
		} catch (DataAccessException e) {
			throw new InternalErrorException(e.getMessage());
		}
	}

	@Override
	public Partner selectPartner(long id) throws PartnerNotFoundException, InternalErrorException {
		String query = "SELECT * FROM PARTNER WHERE ID = ?";
		try {
			Partner p = jdbcTemplate.queryForObject(query, new Object[] { id }, new PartnerRowMapper());
			return p;
		} catch (EmptyResultDataAccessException e) {
			throw new PartnerNotFoundException();
		} catch (DataAccessException e) {
			throw new InternalErrorException(e.getMessage());
		}

	}

	@Override
	public int deletePartner(String id) throws PartnerNotFoundException, InternalErrorException {
		String query = "DELETE FROM PARTNER WHERE ID = ?";
		try {
			int row = jdbcTemplate.update(query, id);
			if(row==0)
				throw new PartnerNotFoundException();
			return row;
		} catch (DataAccessException e) {
			throw new InternalErrorException(e.getMessage());
		}
		
	}

	@Override
	public int updatePartner(String id, Partner partner) throws PartnerNotFoundException, InternalErrorException {
		String query = "UPDATE PARTNER SET companyName=?, ref=?, locale=?, expires=? WHERE id = ?";
		try {
			int row = jdbcTemplate.update(query, partner.getCompanyName(), partner.getRef(),
					partner.getLocale().toLanguageTag(), partner.getExpires(), partner.getId());
			if(row==0)
				throw new PartnerNotFoundException();
			return row;
		} catch (DataAccessException e) {
			throw new InternalErrorException(e.getMessage());
		}
		
	}

}
