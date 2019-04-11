package utilities;

import controllers.BookController;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import models.Book;

/**
 * The RESTful Service to Access Books
 *
 * @author <ENTER YOUR NAME HERE>
 */
@RequestScoped
@Path("/books")
public class BookREST {

    @Inject
    BookController books;

    /**
     * Endpoint to Retrieve all Books via the GET method
     *
     * @return the HTTP Response
     */
    @GET
    @Produces("application/json")
    public Response getAll() {
        return Response.ok(books.getAll()).build();
    }

    /**
     * Endpoint to Retrieve a single Book via the GET method
     *
     * @param id the Book's ID
     * @return the HTTP Response
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") int id) {
        Book result = books.getById(id);
        if (result != null) {
            return Response.ok(result.toJson()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Endpoint to add a single Book via the POST method
     *
     * @param json the JSON Object of the Book
     * @return the HTTP Response
     */
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public Response add(JsonObject json) {
        return Response.ok(books.add(json)).build();
    }

    /**
     * Endpoint to update a single existing Book via the PUT method
     *
     * @param id the ID of the existing Book
     * @param json the JSON object of the changed Book
     * @return the HTTP Response
     */
    @PUT
    @Path("{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response edit(@PathParam("id") int id, JsonObject json) {
        JsonObject result = books.edit(id, json);
        if (result != null) {
            return Response.ok(result).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Endpoint to delete a single existing Book via the DELETE method
     *
     * @param id the ID of the existing book
     * @return the HTTP Response
     */
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        if (books.delete(id)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
