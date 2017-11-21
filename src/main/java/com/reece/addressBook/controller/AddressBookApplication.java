package com.reece.addressBook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by james_000 on 21/11/2017.
 */
public class AddressBookApplication {

    private static final Logger log = LoggerFactory.getLogger(AddressBookApplication.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AddressBookApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void init() {

    }

    public void setUpDB() {
        log.info("Setting up database tables");

        jdbcTemplate.execute("DROP TABLE contacts IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE contacts(" +
                "id int, first_name VARCHAR(255), last_name VARCHAR(255), phone_number VARCHAR(20), " +
                "PRIMARY KEY (id))");

        jdbcTemplate.execute("DROP TABLE address_book IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE address_book(" +
                "id int, name VARCHAR(255), " +
                "PRIMARY KEY (id))");

        jdbcTemplate.execute("DROP TABLE address_book_contacts IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE address_book_contacts(" +
                "address_book_id int, contact_id int, " +
                "PRIMARY KEY (address_book_id, contact_id)," +
                "FOREIGN KEY(address_book_id) REFERENCES address_book(id))," +
                "FOREIGN KEY(contact_id) REFERENCES contacts(id))");
    }

}
