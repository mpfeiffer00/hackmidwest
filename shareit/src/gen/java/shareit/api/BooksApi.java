package shareit.api;

import shareit.model.*;
import shareit.api.BooksApiService;
import shareit.api.factories.BooksApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import shareit.model.Book;

import java.util.Map;
import java.util.List;
import shareit.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/books")


@io.swagger.annotations.Api(description = "the books API")

public class BooksApi  {
   private final BooksApiService delegate;

   public BooksApi(@Context ServletConfig servletContext) {
      BooksApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("BooksApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (BooksApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = BooksApiServiceFactory.getBooksApi();
      }

      this.delegate = delegate;
   }

    @GET
    @Path("/{bookISBN}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Get book information", notes = "", response = Book.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Book.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Book not found", response = Void.class) })
    public Response getBookInformation(@ApiParam(value = "The ISBN to get book information for.",required=true) @PathParam("bookISBN") String bookISBN
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getBookInformation(bookISBN,securityContext);
    }
}
