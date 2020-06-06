package com.contact.db;

import com.contact.controller.IRepository;
import com.contact.model.Contact;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SQLRepository<T, I extends Number> implements IRepository<T, I> {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public SQLRepository(String source) {
        try {
            connection = DriverManager.getConnection(source);
            statement = connection.createStatement();
            statement.execute("SELECT * FROM contacts");
        } catch (SQLException ex) {
            createTable();
        }
    }

    public SQLRepository() {

    }

    @Override
    public T get(I index) {
        T item = null;
        String query = String.format("SELECT * FROM contacts WHERE id = %d",
                index.intValue());
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                item = (T) new Contact(resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("address"),
                        resultSet.getString("webAddress"),
                        resultSet.getString("notes"));
            }
        } catch (SQLException ex) {
            Logger
                    .getLogger(SQLRepository.class.getName())
                    .log(Level.SEVERE, null, ex);
            return item;
        }
        return item;
    }

    @Override
    public List<T> getAll() {
        List<T> list = new ArrayList<>();
        String query = "SELECT * FROM contacts";
        try {
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Contact c = new Contact(resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("email"),
                        resultSet.getString("phoneNumber"),
                        resultSet.getString("address"),
                        resultSet.getString("webAddress"),
                        resultSet.getString("notes"));
                list.add((T) c);
            }
        } catch (SQLException ex) {
            Logger
                    .getLogger(SQLRepository.class.getName())
                    .log(Level.SEVERE, null, ex);
            return list;
        }
        return list;
    }

    @Override
    public boolean add(T obj) {
        boolean result = false;
        Contact contact = (Contact) obj;
        String query = String.format("INSERT INTO contacts"
                        + " VALUES(NULL, '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhoneNumber(),
                contact.getAddress(),
                contact.getWebAddress(),
                contact.getNotes());
        try {
            result = statement.execute(query);
        } catch (SQLException ex) {
            Logger
                    .getLogger(SQLRepository.class.getName())
                    .log(Level.SEVERE, null, ex);
            return result;
        }
        return result;
    }

    @Override
    public T update(I index, T obj) {
        T updatedItem = get(index);
        Contact contact = (Contact) obj;
        String query = String.format("UPDATE contacts SET "
                        + "firstName = '%s', "
                        + "lastName = '%s', "
                        + "email = '%s', "
                        + "phoneNumber = '%s', "
                        + "address = '%s', "
                        + "webAddress = '%s', "
                        + "notes = '%s' "
                        + "WHERE id = %d",
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmail(),
                contact.getPhoneNumber(),
                contact.getAddress(),
                contact.getWebAddress(),
                contact.getNotes(),
                index.intValue());

        try {
            updatedItem = statement.execute(query) ? updatedItem : obj;
        } catch (SQLException ex) {
            Logger
                    .getLogger(SQLRepository.class.getName())
                    .log(Level.SEVERE, null, ex);
            return updatedItem;
        }
        return updatedItem;
    }

    @Override
    public T remove(I index) {
        String query = String.format("DELETE FROM contacts WHERE id = %d",
                index.intValue());
        T deletedItem = get(index);
        try {
            statement.execute(query);
        } catch (SQLException ex) {
            Logger
                    .getLogger(SQLRepository.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return deletedItem;
    }

    @Override
    public boolean save() {
        throw new UnsupportedOperationException("Unsupported.");
    }

    private void createTable() {
        String sql = "CREATE TABLE contacts ("
                + "  id integer primary key,"
                + "  firstName VARCHAR,"
                + "  lastName VARCHAR,"
                + "  email VARCHAR,"
                + "  phoneNumber VARCHAR,"
                + "  address VARCHAR,"
                + "  webAddress VARCHAR,"
                + "  notes VARCHAR)";
        try {
            statement.execute(sql);
        } catch (SQLException ex) {
            Logger
                    .getLogger(SQLRepository.class.getName())
                    .log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }

}
