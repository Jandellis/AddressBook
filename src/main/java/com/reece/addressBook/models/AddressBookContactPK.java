package com.reece.addressBook.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * The Primary Key for the Address Book Contact
 */
public class AddressBookContactPK implements Serializable {

  protected Long addressBookId;
  protected Long contactId;

  public AddressBookContactPK() {
  }

  public AddressBookContactPK(Long addressBookId, Long contactId) {
    this.addressBookId = addressBookId;
    this.contactId = contactId;
  }

  public Long getAddressBookId() {
    return addressBookId;
  }

  public void setAddressBookId(Long addressBookId) {
    this.addressBookId = addressBookId;
  }

  public Long getContactId() {
    return contactId;
  }

  public void setContactId(Long contactId) {
    this.contactId = contactId;
  }
}
