/*=================================================================
 * Warning. This file is fine. You should not need to change this *
 * file at all to complete this project. If you think you do, ask *
 * the instructor about what you're planning to do.               *
 =================================================================*/

var authors;

$(document).ready(function () {
    var hostname = location.host + "/" + location.pathname.split('/')[1];
    var libraryWsUri = "ws://" + hostname + "/library";
    var lws = new WebSocket(libraryWsUri);

    lws.onmessage = function (evt) {
        console.log(JSON.stringify($.parseJSON(evt.data)));
        if (typeof evt.data !== 'undefined') {
            var data = $.parseJSON(evt.data);
            if (typeof data.authors !== 'undefined') {
                if (Array.isArray(data.authors)) {
                    authors = data.authors;
                    var result = '<tbody>';
                    var resultOptions = '';
                    result += "<tr><th>ID</th><th>Name</th><th>Nationality</th></tr>";
                    for (var i = 0; i < data.authors.length; i++) {
                        result += '<tr><td>' + data.authors[i].id + '</td><td>' + data.authors[i].name + '</td><td>' + data.authors[i].nationality + '</td></tr>';
                        resultOptions += '<option value="' + data.authors[i].id + '">' + data.authors[i].name + '</option>';
                    }
                    result += '</tbody>';
                    $('#authors').html(result);
                    $('#bookAuthor').html(resultOptions);
                } else if (typeof data.authors.id !== 'undefined') {
                    $('#authorName').val(data.authors.name);
                    $('#authorNationality').val(data.authors.nationality);
                }
            }
            if (typeof data.books !== 'undefined') {
                if (Array.isArray(data.books)) {
                    var result = '<tbody>';
                    result += "<tr><th>ID</th><th>Title</th><th>Synopsis</th><th>Author</th></tr>";
                    for (var i = 0; i < data.books.length; i++)
                        result += '<tr><td>' + data.books[i].id + '</td><td>' + data.books[i].title + '</td><td>' + data.books[i].synopsis + '</td><td>' + data.books[i].author.name + '</td></tr>';
                    result += '</tbody>';
                    $('#books').html(result);
                } else if (typeof data.books.id !== 'undefined') {
                    $('#bookTitle').val(data.books.title);
                    $('#bookSynopsis').val(data.books.synopsis);
                    $('#bookAuthor').val(data.books.author.id);
                }
            }
        }
    };

    function getAuthorById(id) {
        for (var i = 0; i < authors.length; i++) {
            if (authors[i].id == id) {
                return authors[i];
            }
        }
        return null;
    }

    lws.onopen = function (evt) {
        lws.send(JSON.stringify({"get": "books"}));
        lws.send(JSON.stringify({"get": "authors"}));
    }

    $('#findAuthorById').click(function () {
        lws.send(JSON.stringify({"get": "authors", "id": parseInt($('#authorId').val())}));
    });
    $('#deleteAuthorById').click(function () {
        lws.send(JSON.stringify({"delete": "authors", "id": parseInt($('#authorId').val())}));
        lws.send(JSON.stringify({"get": "authors"}));
    });
    $('#addAuthor').click(function () {
        lws.send(JSON.stringify({post: "authors",
            data: {
                name: $('#authorName').val(),
                nationality: $('#authorNationality').val()
            }}));
        lws.send(JSON.stringify({"get": "authors"}));
    });
    $('#setAuthor').click(function () {
        lws.send(JSON.stringify({put: "authors",
            data: {
                id: parseInt($('#authorId').val()),
                name: $('#authorName').val(),
                nationality: $('#authorNationality').val()
            }}));
        lws.send(JSON.stringify({"get": "authors"}));
    });
    $('#findBookById').click(function () {
        lws.send(JSON.stringify({"get": "books", "id": parseInt($('#bookId').val())}));
    });
    $('#deleteBookById').click(function () {
        lws.send(JSON.stringify({"delete": "books", "id": parseInt($('#bookId').val())}));
        lws.send(JSON.stringify({"get": "books"}));
    });
    $('#addBook').click(function () {
        lws.send(JSON.stringify({post: "books",
            data: {
                title: $('#bookTitle').val(),
                synopsis: $('#bookSynopsis').val(),
                author: getAuthorById($('#bookAuthor').val())
            }}));
        lws.send(JSON.stringify({"get": "books"}));
    });
    $('#setBook').click(function () {
        lws.send(JSON.stringify({put: "books",
            data: {
                id: parseInt($('#bookId').val()),
                title: $('#bookTitle').val(),
                synopsis: $('#bookSynopsis').val(),
                author: getAuthorById($('#bookAuthor').val())
            }}));
        lws.send(JSON.stringify({"get": "books"}));
    });
});