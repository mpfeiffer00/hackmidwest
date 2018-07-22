package shareit.api;

import shareit.api.*;
import shareit.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import shareit.model.Item;
import shareit.model.User;

import java.util.List;
import shareit.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;

public abstract class UsersApiService {
    public abstract Response createItemForUser(String userId,Item body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response createUser(User body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getItemForUser(String userId,String itemId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getItemsForUser(String userId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getUserByName(String userId,SecurityContext securityContext) throws NotFoundException;
}
