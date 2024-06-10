package org.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;


@Path("myresource")
public class MyResource {



    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(
            @HeaderParam("apikey")String apikey,
            @CookieParam("username") String username,
            @Context HttpHeaders headers

    ) {

        System.out.println(headers.getDate());
        System.out.println(headers.getLanguage());
        System.out.println(headers.getMediaType());
        System.out.println(headers.getCookies());
        return "Got it! name:" + username + " , apikey:" + apikey;
    }
}
