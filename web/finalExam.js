/*=================================================================
 * Warning. This file is fine. You should not need to change this *
 * file at all to complete this project. If you think you do, ask *
 * the instructor about what you're planning to do.               *
 =================================================================*/
var authors;
var getAuthors = function () {
    $.getJSON('./api/authors', function (data) {
        authors = data;
        var resultTable = '<tbody>';
        var resultOptions = '';
        resultTable += "<tr><th>ID</th><th>Name</th><th>Nationality</th></tr>";
        for (var i = 0; i < data.length; i++) {
            resultTable += '<tr><td>' + data[i].id + '</td><td>' + data[i].name + '</td><td>' + data[i].nationality + '</td></tr>';
            resultOptions += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
        }
        resultTable += '</tbody>';
        $('#authors').html(resultTable);
        $('#bookAuthor').html(resultOptions);
    });
};
var getBooks = function () {
    $.getJSON('./api/books', function (data) {
        var result = '<tbody>';
        result += "<tr><th>ID</th><th>Title</th><th>Synopsis</th><th>Author</th></tr>";
        for (var i = 0; i < data.length; i++)
            result += '<tr><td>' + data[i].id + '</td><td>' +
                    data[i].title + '</td><td>' + data[i].synopsis +
                    '</td><td>' + data[i].author.name + '</td></tr>';
        result += '</tbody>';
        $('#books').html(result);
    });
};
function getAuthorById(id) {
    for (var i = 0; i < authors.length; i++) {
        if (authors[i].id == id) {
            return authors[i];
        }
    }
    return null;
}

$(document).ready(function () {
    getAuthors();
    getBooks();
    $('#findAuthorById').click(function () {
        $.ajax({
            url: './api/authors/' + $('#authorId').val(),
            method: 'get',
            dataType: 'json',
            success: function (data) {
                $('#authorName').val(data.name);
                $('#authorNationality').val(data.nationality);
            }
        });
    });
    $('#deleteAuthorById').click(function () {
        $.ajax({
            url: './api/authors/' + $('#authorId').val(),
            method: 'delete',
            complete: getAuthors
        });
    });
    $('#addAuthor').click(function () {
        $.ajax({
            url: './api/authors',
            method: 'post',
            contentType: 'application/json',
            data: JSON.stringify({
                name: $('#authorName').val(),
                nationality: $('#authorNationality').val()
            }),
            complete: getAuthors
        });
    });
    $('#setAuthor').click(function () {
        $.ajax({
            url: './api/authors/' + $('#authorId').val(),
            method: 'put',
            contentType: 'application/json',
            data: JSON.stringify({
                authorId: parseInt($('#authorId').val()),
                name: $('#authorName').val(),
                nationality: $('#authorNationality').val()
            }),
            complete: getAuthors
        });
    });
    $('#findBookById').click(function () {
        $.ajax({
            url: './api/books/' + $('#bookId').val(),
            method: 'get',
            dataType: 'json',
            success: function (data) {
                $('#bookTitle').val(data.title);
                $('#bookSynopsis').val(data.synopsis);
                $('#bookAuthor').val(data.author.id);
            }
        });
    });
    $('#deleteBookById').click(function () {
        $.ajax({
            url: './api/books/' + $('#bookId').val(),
            method: 'delete',
            complete: getBooks
        });
    });
    $('#addBook').click(function () {
        $.ajax({
            url: './api/books',
            method: 'post',
            contentType: 'application/json',
            data: JSON.stringify({
                title: $('#bookTitle').val(),
                synopsis: $('#bookSynopsis').val(),
                author: getAuthorById($('#bookAuthor').val())
            }),
            complete: getBooks
        });
    });
    $('#setBook').click(function () {
        $.ajax({
            url: './api/books/' + $('#bookId').val(),
            method: 'put',
            contentType: 'application/json',
            data: JSON.stringify({
                id: parseInt($('#bookId').val()),
                title: $('#bookTitle').val(),
                synopsis: $('#bookSynopsis').val(),
                author: getAuthorById($('#bookAuthor').val())
            }),
            complete: getBooks
        });
    });
});