package com.reece.addressBook.controller;

import com.reece.addressBook.models.AddressBook;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

        AddressBook addressBook = app.createAddressBook("test address book");
        assertNotEquals(addressBook.getId(), 0);
    }

}