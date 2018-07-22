package shareit.api.factories;

import shareit.api.UsersApiService;
import shareit.api.impl.UsersApiServiceImpl;


public class UsersApiServiceFactory {
    private final static UsersApiService service = new UsersApiServiceImpl();

    public static UsersApiService getUsersApi() {
        return service;
    }
}
