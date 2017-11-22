package com.reece.addressBook.controller;

import com.reece.addressBook.models.AddressBookContact;
import com.reece.addressBook.models.AddressBookContactPK;
import org.springframework.data.repository.CrudRepository;

/**
 * A CRUD repository for database actions on an address book contact
 */
public interface AddressBookContactRepository extends CrudRepository<AddressBookContact, AddressBookContactPK> {


}
