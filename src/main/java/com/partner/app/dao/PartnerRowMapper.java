package com.partner.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import org.springframework.jdbc.core.RowMapper;

import com.partner.app.model.Partner;

/**
 * This class is used in the class PartnerDataAccessService to access to the data of the database using querys.
 * 
 * @author Alejandro Torreblanca
 *
 */
public class PartnerRowMapper implements RowMapper<Partner> {
    @Override
    public Partner mapRow(ResultSet rs, int rowNum) throws SQLException {
    	Partner partner = new Partner(rs.getLong("id"), rs.getString("companyName"), rs.getString("ref"), new Locale(rs.getString("locale")), rs.getDate("expires"));
 
        return partner;
    }
}
