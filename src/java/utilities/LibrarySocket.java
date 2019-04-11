//package utilities;
//
//import controllers.AuthorController;
//import controllers.BookController;
//import java.io.IOException;
//import java.io.StringReader;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.enterprise.context.ApplicationScoped;
//import javax.inject.Inject;
//import javax.json.Json;
//import javax.json.JsonObject;
//import javax.json.JsonValue;
//import javax.websocket.OnMessage;
//import javax.websocket.Session;
//import javax.websocket.server.ServerEndpoint;
//
///**
// * The WebSocket Endpoint for the Library System
// *
// * @author <ENTER YOUR NAME HERE>
// */
//@ServerEndpoint("/library")
//@ApplicationScoped
//public class LibrarySocket {
//
//    @Inject
//    AuthorController authors;
//
//    @Inject
//    BookController books;
//
//    @OnMessage
//    public void onMessage(String message, Session session) {
//        JsonObject json = Json.createReader(new StringReader(message)).readObject();
//        JsonValue result = null;
//        boolean wasABook = false;
//
//        /* TODO:
//         *  - Determine if the incoming message is for a "get", "post", "put" or "delete"
//         *  - Determine if the incoming message is for "books" or "authors"
//         *  - Build responses for the following messages:
//         *    * { "get" : "books" } --> JSON Array of All Books
//         *    * { "get" : "authors" } --> JSON Array of All Authors
//         *    * { "get" : "books", "id" : 1 } --> JSON Object of a single Book
//         *    * { "get" : "authors", "id" : 1 } --> JSON Object of a single Author
//         *    * { "post" : "books", "data" : {...} } --> JSON Object of the added Book
//         *    * { "post" : "authors", "data" : {...} } --> JSON Object of the added Author
//         *    * { "put" : "books", "data" : {...} } --> JSON Object of the edited Book
//         *    * { "put" : "authors", "data" : {...} } --> JSON Object of the edited Author
//         *    * { "delete" : "books", "id" : 1 } --> JSON Object { "ok" : true } if successful, or { "ok" : false }
//         *    * { "delete" : "authors", "id" : 1 } --> JSON Object { "ok" : true } if successful, or { "ok" : false }
//         */
//        try {
//            if (wasABook) {
//                session.getBasicRemote().sendText(Json.createObjectBuilder().add("books", result).build().toString());
//            } else {
//                session.getBasicRemote().sendText(Json.createObjectBuilder().add("authors", result).build().toString());
//            }
//        } catch (IOException ex) {
//            Logger.getLogger(LibrarySocket.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//}
