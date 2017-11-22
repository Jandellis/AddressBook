package com.reece.addressBook.controller;

import com.reece.addressBook.models.AddressBook;
import com.reece.addressBook.models.AddressBookContact;
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
    AddressBookApplication app;


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void createAddressBook() throws Exception {
        app.setUpDB();
        String testName = "test address book";

        AddressBook addressBook = app.createAddressBook(testName);
        assertNotEquals(addressBook.getId(), 0);
        assertThat(addressBook.getName(), is(testName));
    }

    @Test
    public void createContact() throws Exception {
        app.setUpDB();
        String firstName = "Bill";
        String lastName = "Bob";
        String phoneNumber = "0412345678";

        Contact contact = app.createContact(firstName, lastName, phoneNumber);
        assertNotEquals(contact.getId(), new Long(0));
        assertThat(contact.getFirstName(), is(firstName));
        assertThat(contact.getLastName(), is(lastName));
        assertThat(contact.getPhoneNumber(), is(phoneNumber));
    }

    @Test
    public void addContactToAddressBook() throws Exception {
        app.setUpDB();

        String firstName = "Bill";
        String lastName = "Bob";
        String phoneNumber = "0412345678";
        Contact contact = app.createContact(firstName, lastName, phoneNumber);

        String testName = "test address book";
        AddressBook addressBook = app.createAddressBook(testName);

        AddressBookContact result = app.addContactToAddressBook(contact, addressBook);

        assertThat(result.getAddressBookId(), is(addressBook.getId()));
        assertThat(result.getContactId(), is(contact.getId()));
    }

    @Test
    public void deleteContactFromAddressBook() throws Exception {
        app.setUpDB();

        String firstName = "Bill";
        String lastName = "Bob";
        String phoneNumber = "0412345678";
        Contact contact = app.createContact(firstName, lastName, phoneNumber);

        String testName = "test address book";
        AddressBook addressBook = app.createAddressBook(testName);
        AddressBookContact addressBookContact = app.addContactToAddressBook(contact, addressBook);
        boolean result = app.deleteContactFromAddressBook(addressBookContact);

        assertTrue(result);
    }

    @Test
    public void deleteContact() throws Exception {
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


    @Test
    public void deleteAddressBookButNotContacts() throws Exception {
        app.setUpDB();

        String firstName = "Bill";
        String lastName = "Bob";
        String phoneNumber = "0412345678";
        Contact contact = app.createContact(firstName, lastName, phoneNumber);

        String testName = "test address book";
        AddressBook addressBook = app.createAddressBook(testName);
        app.addContactToAddressBook(contact, addressBook);
        boolean result = app.deleteAddressBook(addressBook);

        assertTrue(result);

        Contact afterDelete = app.getConatct(contact.getId());
        assertThat(afterDelete, is(contact));
    }

    @Test
    public void addContactToMultipleAddressBooks() throws Exception {
        app.setUpDB();

        Contact contact = app.createContact("Bill", "Bob", "0412345678");

        AddressBook book1 = app.createAddressBook("book 1");
        AddressBook book2 = app.createAddressBook("book 2");
        app.addContactToAddressBook(contact, book1);
        app.addContactToAddressBook(contact, book2);

    }
}