package shareit.api.impl;

import com.cerner.engineering.ReadISBN;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import shareit.api.BooksApiService;
import shareit.api.NotFoundException;
import shareit.model.Book;

public class BooksApiServiceImpl extends BooksApiService
{
    @Override
    public Response getBookInformation(final String bookISBN, final SecurityContext securityContext) throws NotFoundException
    {
        // do some magic!
        // stuff!
        final object.Book book = ReadISBN.getBookFromISBN(bookISBN);

        final Book restBook = new Book();
        restBook.setId(String.valueOf(book.getGoodReadsId()));
        restBook.setTitle(book.getTitle());
        restBook.setAuthor(book.getAuthor());
        restBook.setIsbn(book.getIsbn());
        restBook.setIsbn13(book.getIsbn13());
        restBook.setImageUrl(book.getImageUrl());
        restBook.setSmallImageUrl(book.getSmallImageUrl());

        return Response.ok().entity(book).build();
    }
}
