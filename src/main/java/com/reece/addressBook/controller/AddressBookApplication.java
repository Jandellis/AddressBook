package com.reece.addressBook.controller;

import com.reece.addressBook.models.AddressBook;
import com.reece.addressBook.models.Contact;
import java.util.List;
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
    private ContactRepository contactRepository;
    private AddressBookRepository addressBookRepository;

    @Autowired
    public AddressBookApplication(JdbcTemplate jdbcTemplate, ContactRepository contactRepository, AddressBookRepository addressBookRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.contactRepository = contactRepository;
        this.addressBookRepository = addressBookRepository;
    }

    public void init() {
        setUpDB();
    }

    public void setUpDB() {
        log.info("Setting up database tables");

        jdbcTemplate.execute("DROP TABLE contact IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE contact(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255), phone_number VARCHAR(20), " +
                "PRIMARY KEY (id))");

        jdbcTemplate.execute("DROP TABLE address_book IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE address_book(" +
                "id SERIAL, name VARCHAR(255) UNIQUE, " +
                "PRIMARY KEY (id))");

        jdbcTemplate.execute("DROP TABLE address_book_contact IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE address_book_contact(" +
                "address_book_id int, contact_id int, " +
                "PRIMARY KEY (address_book_id, contact_id)," +
                "FOREIGN KEY(address_book_id) REFERENCES address_book(id) ON DELETE CASCADE," +
                "FOREIGN KEY(contact_id) REFERENCES contact(id) ON DELETE CASCADE)");
    }

    public AddressBook createAddressBook(String name) {
        AddressBook addressBook = new AddressBook(name);
        return addressBookRepository.save(addressBook);
    }

    public Contact createContact(String firstName, String lastName, String phoneNumber) {
        Contact contact = new Contact(firstName, lastName, phoneNumber);
        return contactRepository.save(contact);
    }

    public boolean addContactToAddressBook(Contact contact, AddressBook addressBook) {
        return jdbcTemplate.update("INSERT INTO address_book_contact(address_book_id, contact_id) VALUES(?,?)", addressBook.getId(), contact.getId()) > 0;

    }

    public boolean deleteContactFromAddressBook(Contact contact, AddressBook addressBook) {
        return jdbcTemplate.update("DELETE FROM address_book_contact WHERE address_book_id = ? AND  contact_id = ?", addressBook.getId(), contact.getId()) > 0;
    }

    public boolean deleteContact(Contact contact) {
        return jdbcTemplate.update("DELETE FROM contact WHERE id = ?", contact.getId()) > 0;
    }
}
