package com.reece.addressBook.controller;

import com.reece.addressBook.models.AddressBook;
import com.reece.addressBook.models.Contact;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by james_000 on 21/11/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressBookApplicationTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createAddressBook() throws Exception {
        AddressBookApplication app = new AddressBookApplication(jdbcTemplate);
        app.setUpDB();
        String testName = "test address book";

        AddressBook addressBook = app.createAddressBook(testName);
        assertNotEquals(addressBook.getId(), 0);
        assertThat(addressBook.getName(), is(testName));
    }

    @Test
    public void createContact() throws Exception {
        AddressBookApplication app = new AddressBookApplication(jdbcTemplate);
        app.setUpDB();
        String firstName = "Bill";
        String lastName = "Bob";
        String phoneNumber = "0412345678";

        Contact contact = app.createContact(firstName, lastName, phoneNumber);
        assertNotEquals(contact.getId(), 0);
        assertThat(contact.getFirstName(), is(firstName));
        assertThat(contact.getLastName(), is(lastName));
        assertThat(contact.getPhoneNumber(), is(phoneNumber));
    }

    @Test
    public void addContactToAddressBook() throws Exception {
        AddressBookApplication app = new AddressBookApplication(jdbcTemplate);
        app.setUpDB();

        String firstName = "Bill";
        String lastName = "Bob";
        String phoneNumber = "0412345678";
        Contact contact = app.createContact(firstName, lastName, phoneNumber);

        String testName = "test address book";
        AddressBook addressBook = app.createAddressBook(testName);

        boolean result = app.addContactToAddressBook(contact, addressBook);

        assertTrue(result);
    }

    @Test(expected = DuplicateKeyException.class)
    public void addContactToAddressBookTwice() throws Exception {
        AddressBookApplication app = new AddressBookApplication(jdbcTemplate);
        app.setUpDB();

        String firstName = "Bill";
        String lastName = "Bob";
        String phoneNumber = "0412345678";
        Contact contact = app.createContact(firstName, lastName, phoneNumber);

        String testName = "test address book";
        AddressBook addressBook = app.createAddressBook(testName);

        boolean result = app.addContactToAddressBook(contact, addressBook);
        result = app.addContactToAddressBook(contact, addressBook);

    }

    @Test
    public void deleteContactFromAddressBook() throws Exception {
        AddressBookApplication app = new AddressBookApplication(jdbcTemplate);
        app.setUpDB();

        String firstName = "Bill";
        String lastName = "Bob";
        String phoneNumber = "0412345678";
        Contact contact = app.createContact(firstName, lastName, phoneNumber);

        String testName = "test address book";
        AddressBook addressBook = app.createAddressBook(testName);
        app.addContactToAddressBook(contact, addressBook);
        boolean result = app.deleteContactFromAddressBook(contact, addressBook);

        assertTrue(result);
    }

    @Test
    public void deleteContact() throws Exception {
        AddressBookApplication app = new AddressBookApplication(jdbcTemplate);
        app.setUpDB();

        String firstName = "Bill";
        String lastName = "Bob";
        String phoneNumber = "0412345678";
        Contact contact = app.createContact(firstName, lastName, phoneNumber);

        String testName = "test address book";
        AddressBook addressBook = app.createAddressBook(testName);
        app.addContactToAddressBook(contact, addressBook);
        boolean result = app.deleteContact(contact);

        assertTrue(result);
    }

}