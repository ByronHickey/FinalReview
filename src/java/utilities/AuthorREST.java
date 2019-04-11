package utilities;

import controllers.AuthorController;
import javax.ejb.Stateless;
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
import static javax.ws.rs.client.Entity.json;
import javax.ws.rs.core.Response;
import models.Author;

/**
 * The RESTful Service to Access Authors
 *
 * @author <ENTER YOUR NAME HERE>
 */
@Path("/authors")
@Stateless
public class AuthorREST {

    @Inject
    AuthorController authors;

    /**
     * Endpoint to Retrieve all Authors via the GET method
     *
     * @return the HTTP Response
     */
    @GET
    @Produces("application/json")
    public Response getAll() {
        return Response.ok(authors.getAll()).build();
    }

    /**
     * Endpoint to Retrieve a single Author via the GET method
     *
     * @param id the Author's ID
     * @return the HTTP Response
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Response getById(@PathParam("id") int id) {
        Author result = authors.getById(id);
        if (result != null) {
            return Response.ok(result.toJson()).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    // TODO: Build an Endpoint to Add an Author with a JSON object via the POST method
    @POST
    @Consumes("application/json")
    public void add(JsonObject author){
        authors.add(author);
        
    }
            
    // TODO: Build an Endpoint to Edit an Author by their ID with a JSON object via the PUT method
    
    @PUT
    @Path("{id")
    @Consumes("application/json")
    public void edit(@PathParam("id") int id, JsonObject json) {
        authors.edit(id,json);
    }
    // TODO: Build an Endpoint to Delete an Author by their ID via the DELETE method
    @DELETE
    @Path("{id")
    @Consumes("application/json")
    public void delete(@PathParam("id") int id) {
        authors.delete(id);
    }
}
