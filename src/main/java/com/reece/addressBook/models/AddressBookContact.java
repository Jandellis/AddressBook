package com.reece.addressBook.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(AddressBookContactPK.class)
public class AddressBookContact {

  @Id
  private Long addressBookId;
  @Id
  private Long contactId;

  public AddressBookContact() {
  }

  public AddressBookContact(Long addressBookId, Long contactId) {
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
