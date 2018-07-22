package shareit.api.impl;

import shareit.api.*;
import shareit.model.*;

import shareit.model.Book;

import java.util.List;
import shareit.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;

public class BooksApiServiceImpl extends BooksApiService {
    @Override
    public Response getBookInformation(String bookISBN, SecurityContext securityContext) throws NotFoundException {
        // do some magic!
        // stuff!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }
}
