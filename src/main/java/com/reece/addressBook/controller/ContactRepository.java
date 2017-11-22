package com.reece.addressBook.controller;

import com.reece.addressBook.models.Contact;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * A CRUD repository for database actions on a contact
 */
public interface ContactRepository extends CrudRepository<Contact, Long>{

  @Query(value = "SELECT id, first_name, last_name, phone_number FROM contact c, address_book_contact abc WHERE c.id = abc.contact_id AND abc.address_book_id = :addressBook", nativeQuery = true)
  List<Contact> getAddressBookContacts(@Param("addressBook") Long addressBook);

  @Query(value = "SELECT id, first_name, last_name, phone_number "
      + "FROM contact c "
      + "WHERE first_name = :firstName AND last_name = :lastName AND phone_number = :phoneNumber", nativeQuery = true)
  Optional<Contact> getContact(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("phoneNumber")String phoneNumber);
}
