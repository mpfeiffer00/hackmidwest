package shareit.api.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import shareit.api.ApiResponseMessage;
import shareit.api.NotFoundException;
import shareit.api.UsersApiService;
import shareit.model.Item;
import shareit.model.User;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-07-21T19:30:29.712-05:00")
public class UsersApiServiceImpl extends UsersApiService
{
    @Override
    public Response createItemForUser(final String userId, final Item body, final SecurityContext securityContext) throws NotFoundException
    {
        // do some magic!
        return Response.ok().entity(new Item()).build();
    }

    @Override
    public Response createUser(final User body, final SecurityContext securityContext) throws NotFoundException
    {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response getItemForUser(final String userId, final String itemId, final SecurityContext securityContext) throws NotFoundException
    {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response getItemsForUser(final String userId, final SecurityContext securityContext) throws NotFoundException
    {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "magic!")).build();
    }

    @Override
    public Response getUserByName(final String userId, final SecurityContext securityContext) throws NotFoundException
    {
        // do some magic!
        final User user = new User();
        user.setActive(true);
        user.setFirstName("what's");
        user.setLastName("this");
        user.setId("1234");
        user.setUsername(userId);
        return Response.ok().entity(user).build();
    }
}
