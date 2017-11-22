package com.reece.addressBook.controller;

import com.reece.addressBook.models.AddressBook;
import com.reece.addressBook.models.AddressBookContact;
import com.reece.addressBook.models.Contact;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AddressBookApplication {

  private static final Logger log = LoggerFactory.getLogger(AddressBookApplication.class);

  private JdbcTemplate jdbcTemplate;
  private ContactRepository contactRepository;
  private AddressBookRepository addressBookRepository;
  private AddressBookContactRepository addressBookContactRepository;

  @Autowired
  public AddressBookApplication(JdbcTemplate jdbcTemplate, ContactRepository contactRepository,
                                AddressBookRepository addressBookRepository,
                                AddressBookContactRepository addressBookContactRepository) {
    this.jdbcTemplate = jdbcTemplate;
    this.contactRepository = contactRepository;
    this.addressBookRepository = addressBookRepository;
    this.addressBookContactRepository = addressBookContactRepository;
  }

  public void init() {
    setUpDB();
  }

  /**
   * Creates all empty tables for the database
   */
  public void setUpDB() {
    log.info("Setting up database tables");

    jdbcTemplate.execute("DROP TABLE contact IF EXISTS");
    jdbcTemplate.execute("CREATE TABLE contact(" +
        "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255), phone_number VARCHAR(20), " +
        "PRIMARY KEY (id), " +
        "CONSTRAINT contact_unique UNIQUE(first_name, last_name, phone_number))");

    jdbcTemplate.execute("DROP TABLE address_book IF EXISTS");
    jdbcTemplate.execute("CREATE TABLE address_book(" +
        "id SERIAL, name VARCHAR(255) UNIQUE, " +
        "PRIMARY KEY (id))");

    jdbcTemplate.execute("DROP TABLE address_book_contact IF EXISTS");
    jdbcTemplate.execute("CREATE TABLE address_book_contact(" +
        "address_book_id INT, contact_id INT, " +
        "PRIMARY KEY (address_book_id, contact_id)," +
        "FOREIGN KEY(address_book_id) REFERENCES address_book(id) ON DELETE CASCADE," +
        "FOREIGN KEY(contact_id) REFERENCES contact(id) ON DELETE CASCADE)");
  }

  /**
   * Creates a new address book
   * @param name the name of the address book
   * @return the address book
   */
  public AddressBook createAddressBook(String name) {
    AddressBook addressBook = new AddressBook(name);
    return addressBookRepository.save(addressBook);
  }

  /**
   * Creates a new contact, if one exists with the same first name, last name and phone number it will return that one
   * @param firstName the first name of the contact
   * @param lastName the last name of the contact
   * @param phoneNumber the phone number of the contact
   * @return the contact
   */
  public Contact createContact(String firstName, String lastName, String phoneNumber) {
    Optional<Contact> existingContact = contactRepository.getContact(firstName, lastName, phoneNumber);

    if (existingContact.isPresent()) {
      return existingContact.get();
    }

    Contact contact = new Contact(firstName, lastName, phoneNumber);
    return contactRepository.save(contact);
  }

  /**
   * Adds a contact into an address book
   * @param contact the contact to add
   * @param addressBook the address book to add the contact to
   * @return
   */
  public AddressBookContact addContactToAddressBook(Contact contact, AddressBook addressBook) {
    AddressBookContact addressBookContact = new AddressBookContact(addressBook.getId(), contact.getId());
    return addressBookContactRepository.save(addressBookContact);
  }

  /**
   * Deletes a contact from an address book, but does not delete the contact
   * @param addressBookContact
   * @return
   */
  public boolean deleteContactFromAddressBook(AddressBookContact addressBookContact) {
    addressBookContactRepository.delete(addressBookContact);
    return true;
  }

  /**
   * Deletes a contact, if it in an address book, it will be removed from that too.
   * @param contact the contact to delete
   * @return
   */
  public boolean deleteContact(Contact contact) {
    contactRepository.delete(contact);
    return true;
  }

  /**
   * Deletes an address book
   * @param addressBook the address book to delete
   * @return
   */
  public boolean deleteAddressBook(AddressBook addressBook) {
    addressBookRepository.delete(addressBook);
    return true;
  }

  /**
   * Gets a contact by its id
   * @param id the id of the contact to find
   * @return the contact
   */
  public Contact getContact(Long id) {
    return contactRepository.findOne(id);
  }

  /**
   * Gets all of the contacts in an address book
   * @param addressBook the address book of the contacts to find
   * @return a list of contacts
   */
  public List<Contact> getAddressBookContacts(AddressBook addressBook) {
    return contactRepository.getAddressBookContacts(addressBook.getId());
  }

  /**
   * Gets all of the contacts
   * @return a list of all contacts
   */
  public List<Contact> getAllContacts() {
    return (List<Contact>) contactRepository.findAll();
  }

  /**
   * Prints all contacts of an address book out to the log
   * @param addressBook the address book of contacts to find
   */
  public void printContacts(AddressBook addressBook){
    contactRepository.getAddressBookContacts(addressBook.getId()).forEach(contact -> log.info(contact.toString()));
  }

  /**
   * Prints all contacts out to the log
   */
  public void printContacts(){
    getAllContacts().forEach(contact -> log.info(contact.toString()));
  }
}
