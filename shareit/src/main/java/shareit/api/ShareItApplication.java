package shareit.api;

import java.util.Set;

import javax.ws.rs.core.Application;

import com.google.common.collect.Sets;

import io.swagger.jaxrs.listing.AcceptHeaderApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

public final class ShareItApplication extends Application
{
    @Override
    public Set<Class<?>> getClasses()
    {
        return Sets.<Class<?>> newHashSet(AcceptHeaderApiListingResource.class, SwaggerSerializers.class, UsersApi.class, BooksApi.class);
    }
}
