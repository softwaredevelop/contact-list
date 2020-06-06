package com.contact.db;

import com.contact.model.Contact;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SQLRepositoryTest {

    SQLRepository s=new SQLRepository("jdbc:sqlite:contacts.db");

    @BeforeEach
    void setUp() {
    }

    @Test
    void get() {
        s.get(1);
        Assertions.assertEquals(String.valueOf(s.get(1)), "software develop", "Equal");
    }

    @Test
    void getAll() {
        s.getAll();
        Assertions.assertEquals(String.valueOf(s.getAll()), "[software develop]", "Equal");
    }

    @Test
    void add() {
        Contact c=new Contact();
        c.setLastName("testname1");
        c.setAddress("address1");
        c.setEmail("mail@mail");
        c.setNotes("note1");
        s.add(c);
    }

    @Test
    void update() {
    }

    @Test
    void remove() {

    }

    @Test
    void save() {

    }
}