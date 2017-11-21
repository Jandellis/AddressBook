package com.reece.addressBook.models;

/**
 * Created by james_000 on 21/11/2017.
 */
public class AddressBook {
    private String name;
    private long id;

    public AddressBook(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
