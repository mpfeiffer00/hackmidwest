package shareit.api;

import shareit.model.*;
import shareit.api.UsersApiService;
import shareit.api.factories.UsersApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import shareit.model.Item;
import shareit.model.User;

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

@Path("/users")


@io.swagger.annotations.Api(description = "the users API")

public class UsersApi  {
   private final UsersApiService delegate;

   public UsersApi(@Context ServletConfig servletContext) {
      UsersApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("UsersApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (UsersApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = UsersApiServiceFactory.getUsersApi();
      }

      this.delegate = delegate;
   }

    @POST
    @Path("/{userId}/items")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Create item for the user.", notes = "", response = Void.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Void.class) })
    public Response createItemForUser(@ApiParam(value = "The id that needs to be fetched.",required=true) @PathParam("userId") String userId
,@ApiParam(value = "Created item object" ,required=true) Item body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createItemForUser(userId,body,securityContext);
    }
    @POST
    
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Create new user", notes = "", response = Void.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Void.class) })
    public Response createUser(@ApiParam(value = "Created user object" ,required=true) User body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.createUser(body,securityContext);
    }
    @GET
    @Path("/{userId}/items/{itemId}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Get an item for the user.", notes = "Retrieve an item belonging to the user.", response = Item.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Item.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Item not found for user", response = Void.class) })
    public Response getItemForUser(@ApiParam(value = "The user id that needs to be fetched.",required=true) @PathParam("userId") String userId
,@ApiParam(value = "The item id that needs to be fetched.",required=true) @PathParam("itemId") String itemId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getItemForUser(userId,itemId,securityContext);
    }
    @GET
    @Path("/{userId}/items")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Get all items for the user.", notes = "Retrieve a list of all items belonging to the user.", response = Item.class, responseContainer = "List", tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Item.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "User not found", response = Void.class) })
    public Response getItemsForUser(@ApiParam(value = "The id that needs to be fetched.",required=true) @PathParam("userId") String userId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getItemsForUser(userId,securityContext);
    }
    @GET
    @Path("/{userId}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Get user by user id", notes = "Retrieve the User by the user's id", response = User.class, tags={  })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = User.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "User not found", response = Void.class) })
    public Response getUserByName(@ApiParam(value = "The id that needs to be fetched.",required=true) @PathParam("userId") String userId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getUserByName(userId,securityContext);
    }
}
