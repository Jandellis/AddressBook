package com.reece.addressBook.controller;

import com.reece.addressBook.models.AddressBook;
import com.reece.addressBook.models.AddressBookContact;
import com.reece.addressBook.models.Contact;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        List<Contact> contacts = app.getAddressBookContacts(addressBook);
        assertThat(contacts.size(), is(0));
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

        Contact afterDelete = app.getContact(contact.getId());
        assertThat(afterDelete, is(contact));
    }

    @Test
    public void addContactToMultipleAddressBooks() throws Exception {
        app.setUpDB();

        Contact contact = app.createContact("Bill", "Bob", "0412345678");
        Contact contact2 = app.createContact("Fred", "Lawn", "0412347654");

        AddressBook book1 = app.createAddressBook("book 1");
        AddressBook book2 = app.createAddressBook("book 2");
        app.addContactToAddressBook(contact, book1);
        app.addContactToAddressBook(contact2, book1);
        app.addContactToAddressBook(contact, book2);
        List<Contact> contacts = app.getAddressBookContacts(book1);
        assertThat(contacts.size(), is(2));
        List<Contact> contacts2 = app.getAddressBookContacts(book2);
        assertThat(contacts2.size(), is(1));

    }

    @Test
    public void getAllContacts() throws Exception {
        app.setUpDB();

        Contact contact = app.createContact("Bill", "Bob", "0412345678");
        Contact contact2 = app.createContact("Fred", "Lawn", "0412347654");

        List<Contact> contacts = app.getAllContacts();
        assertThat(contacts.size(), is(2));

    }


    @Test
    public void createSameContact() throws Exception {
        app.setUpDB();

        Contact contact = app.createContact("Bill", "Bob", "0412345678");
        Contact contact2 = app.createContact("Bill", "Bob", "0412345678");

        assertThat(contact, is(contact2));

    }

    @Test
    public void getAllUniqueContacts() throws Exception {
        app.setUpDB();

        Contact contact = app.createContact("Bill", "Bob", "0412345678");
        Contact contact2 = app.createContact("Bill", "Bob", "0412345678");
        Contact contact3 = app.createContact("Fred", "Lawn", "0412347654");

        List<Contact> contacts = app.getAllContacts();
        assertThat(contacts.size(), is(2));

    }

    @Test
    public void printContacts() throws Exception {
        app.setUpDB();

        Contact contact = app.createContact("Bill", "Bob", "0412345678");
        Contact contact2 = app.createContact("Bill", "Bob", "0412345678");
        Contact contact3 = app.createContact("Fred", "Lawn", "0412347654");

        app.printContacts();

    }


    @Test
    public void printContactsForAddressBook() throws Exception {
        app.setUpDB();


        Contact contact = app.createContact("Bill", "Bob", "0412345678");
        Contact contact2 = app.createContact("Fred", "Lawn", "0412347654");

        AddressBook book1 = app.createAddressBook("book 1");
        AddressBook book2 = app.createAddressBook("book 2");
        app.addContactToAddressBook(contact, book1);
        app.addContactToAddressBook(contact2, book1);
        app.addContactToAddressBook(contact, book2);

        app.printContacts(book2);

    }
}