package models;

import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author <ENTER YOUR NAME HERE>
 */
public class Book {

    private int id;
    private String title;
    private String synopsis;
    private Author author;

    /**
     * Create a Blank Book
     */
    public Book() {
    }

    /**
     * Create a Book based on Parameters
     *
     * @param id the Book's ID
     * @param title the Book's Title
     * @param synopsis the Book's Synopsis
     * @param author the Book's Author
     */
    public Book(int id, String title, String synopsis, Author author) {
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.author = author;
    }

    /**
     * Create a Book based on a JSON Object
     *
     * @param json the JSON Object of the Book
     */
    public Book(JsonObject json) {
        this.id = json.getInt("id", 0);
        this.title = json.getString("title", "");
        this.synopsis = json.getString("synopsis", "");
        this.author = new Author(json.getJsonObject("author"));
    }

    /**
     * Get the Book's ID
     *
     * @return the Book's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Set the Book's ID
     *
     * @param id the Book's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the Book's Title
     *
     * @return the Book's Title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the Book's Title
     *
     * @param title the Book's Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the Book's Synopsis
     *
     * @return the Book's Synopsis
     */
    public String getSynopsis() {
        return synopsis;
    }

    /**
     * Set the Book's Synopsis
     *
     * @param synopsis the Book's Synopsis
     */
    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    /**
     * Get the Book's Author
     *
     * @return the Book's Author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Set the Book's Author
     *
     * @param author the Book's Author
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Retrieve the Book as a JSON Object
     *
     * @return the JSON Object
     */
    public JsonObject toJson() {
        return Json.createObjectBuilder().
                add("id", id).
                add("title", title).
                add("synopsis", synopsis).
                add("author", author.toJson()).
                build();
    }
}
