package shareit.api;

import shareit.model.Item;
import shareit.model.User;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2018-07-21T19:30:29.712-05:00")
public abstract class UsersApiService {
    public abstract Response createItemForUser(String userId,Item body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response createUser(User body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getItemForUser(String userId,String itemId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getItemsForUser(String userId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getUserByName(String userId,SecurityContext securityContext) throws NotFoundException;
}
