package com.example.mapsi3.REST;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ForImportCoordinateAndColors {
    @GET("/onMapClick/{name}/{latitude}/{longitude}/{red}/{green}/{blue}")
public Call<Integer> addPx(@Path("name") String name,@Path("latitude")  double latitude, @Path("longitude") double longitude,@Path("red") int red, @Path("green") int green, @Path("blue") int blue);
}
