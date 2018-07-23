package com.cerner.engineering;

import shareit.api.external.ReadISBN;
import shareit.object.Book;

/**
 * @author am025347
 */
public class CallShit
{
    public static Book getBook(final String isbn)
    {

        return ReadISBN.getBookFromISBN(isbn);
    }
}
