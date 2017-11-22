package com.reece.addressBook.controller;

import com.reece.addressBook.models.AddressBook;
import org.springframework.data.repository.CrudRepository;

/**
 * A CRUD repository for database actions on an address book
 */
public interface AddressBookRepository extends CrudRepository<AddressBook, Long> {

}
