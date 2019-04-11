package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import models.Author;
import models.Book;
import utilities.DBUtils;

/**
 * The Logic Controller for the Authors
 *
 * @author <ENTER YOUR NAME HERE>
 */
@ApplicationScoped
public class AuthorController {

    List<Author> authors = new ArrayList<>;

    /**
     * A Constructor to build the basic controller and load data
     */
    public AuthorController() {
        // TODO: Refresh the List of Authors from the Database
        refreshFromDB();
    }

    /**
     * A method for refreshing the list of Authors from the Database
     */
    private void refreshFromDB() {
        try {
            Connection conn = DBUtils.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Author");
            authors.clear();
            while (rs.next()) {
                Author a = new Author(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("nationality"),
                        authors.getById(rs.getInt("id")));
                authors.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Persists a Single Author (new or existing) to the Database
     *
     * @param a the Author object
     * @return was the database change successful
     */
    private boolean persistToDB(Author a) {
        int results = 0;
        try {
            String sql = "";
            Connection conn = DBUtils.getConnection();
            if (a.getId() <= 0) {
                sql = "INSERT INTO authors (name, nationality) VALUES (?, ?)";
            } else {
                sql = "UPDATE authors SET name = ?, nationality = ? WHERE id = ?";
            }
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, a.getName());
            pstmt.setString(2, a.getNationality());
            if (a.getId() > 0) {
                pstmt.setInt(3, a.getId());
            }
            results = pstmt.executeUpdate();
            if (a.getId() <= 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                rs.next();
                int id = rs.getInt(1);
                a.setId(id);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(AuthorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results > 0;
    }

    /**
     * Removes a Single Author from the Database
     *
     * @param id the ID of the Author
     * @return was the database change successful
     */
    private boolean removeFromDB(int id) {
        try {
            String sql = "";
            Connection conn = DBUtils.getConnection();
            sql = "DELETE FROM authors WHERE id = ?";
            authors.clear();
            while (rs.next()) {
                Book b = new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("synopsis"),
                        authors.getById(rs.getInt("author_id")));
                authors.add(b);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retrieves a JSON representation of all Authors
     *
     * @return the JSON array of all Authors
     */
    public JsonArray getAll() {
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (Author a : authors) {
            arr.add(a.toJson());
        }
        return arr.build();
    }

    /**
     * Retrieves a single Author by its ID
     *
     * @param id the Author's ID
     * @return the Author object
     */
    public Author getById(int id) {
        // TODO: Retrieve an Author object from the list based on the ID
        for (Author a : authors) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    /**
     * Adds a new Author based on a JSON object
     *
     * @param json the JSON object of an Author
     * @return the JSON object of the author with its new ID
     */
    public JsonObject add(JsonObject json) {
        Author a = new Author(json);
        persistToDB(a);
        authors.add(a);
        return a.toJson();
    }

       
    


    /**
     * Edits an existing Author by its ID based on a JSON object
     *
     * @param id the ID of the existing Author
     * @param json the JSON object of the changed Author
     * @return the JSON object of the stored Author after the change
     */
    public JsonObject edit(int id, JsonObject json) {
        Author a = getById(id);
        if (a != null) {
            a.setName(json.getString("name"));
            a.setNationality(json.getString("nationality"));
            persistToDB(a);
            return a.toJson();
        } else {
            return null;
        }
    }

    /**
     * Removes an Author from the List and the Database based on its ID
     *
     * @param id the ID of the existing Author
     * @return whether or not the removal was successful
     */
    public boolean delete(int id) {
         Author a = getById(id);
        authors.remove(id);
        return removeFromDB(id);
    }

}
