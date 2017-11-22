package com.reece.addressBook.controller;

import com.reece.addressBook.models.AddressBook;
import com.reece.addressBook.models.Contact;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.Appender;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressBookApplicationLogOutputTest {

    @Autowired
    private AddressBookApplication app;

    @Mock
    private Appender mockAppender;

    @Captor
    private ArgumentCaptor<LoggingEvent> captorLoggingEvent;

    @Before
    public void setup() {
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(mockAppender);
    }

    @After
    public void teardown() {
        final Logger logger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.detachAppender(mockAppender);
    }

    @Test
    public void printContacts() throws Exception {
        app.setUpDB();

        Contact contact1 = app.createContact("Bill", "Bob", "0412345678");
        Contact contact2 = app.createContact("Bill", "Bob2", "0412323678");
        Contact contact3 = app.createContact("Fred", "Lawn", "0412347654");

        app.printContacts();

        //5 log lines, 1 for db set up, 1 for ASTQueryTranslatorFactory, 3 for each contact
        verify(mockAppender,  times(5)).doAppend(captorLoggingEvent.capture());

        assertThat(countContacts(Stream.of(contact1, contact2, contact3).collect(Collectors.toList()),
                captorLoggingEvent.getAllValues()), is(3));
    }

    @Test
    public void printContactsForAddressBook() throws Exception {
        app.setUpDB();


        Contact contact1 = app.createContact("Bill", "Bob", "0412345678");
        Contact contact2 = app.createContact("Fred", "Lawn", "0412347654");

        AddressBook book1 = app.createAddressBook("book 1");
        AddressBook book2 = app.createAddressBook("book 2");
        app.addContactToAddressBook(contact1, book1);
        app.addContactToAddressBook(contact2, book1);
        app.addContactToAddressBook(contact1, book2);

        app.printContacts(book2);

        //2 log lines, 1 for db set up, 1 the contact
        verify(mockAppender,  times(2)).doAppend(captorLoggingEvent.capture());

        assertThat(countContacts(Stream.of(contact1).collect(Collectors.toList()),
                captorLoggingEvent.getAllValues()), is(1));
    }

    private int countContacts(List<Contact> contacts, List<LoggingEvent> events) {
        int contactsFound = 0;
        for (LoggingEvent event : events) {
            for (Contact contact : contacts) {
                if (event.getFormattedMessage().equals(contact.toString())) {
                    contactsFound++;
                }
            }
        }
        return contactsFound;
    }
}

