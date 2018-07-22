package shareit.api.factories;

import shareit.api.BooksApiService;
import shareit.api.impl.BooksApiServiceImpl;


public class BooksApiServiceFactory {
    private final static BooksApiService service = new BooksApiServiceImpl();

    public static BooksApiService getBooksApi() {
        return service;
    }
}
