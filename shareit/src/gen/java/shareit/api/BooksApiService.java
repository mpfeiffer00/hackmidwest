package shareit.api;

import shareit.api.*;
import shareit.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import shareit.model.Book;

import java.util.List;
import shareit.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;

public abstract class BooksApiService {
    public abstract Response getBookInformation(String bookISBN,SecurityContext securityContext) throws NotFoundException;
}
