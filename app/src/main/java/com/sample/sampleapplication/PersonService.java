package com.sample.sampleapplication;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by connieleung on 1/18/15.
 */
public interface PersonService  {

    @GET("/persons/all")
    List<Person> listPeople();

    @GET("/persons/{id}")
    Person getPerson(@Path("id") String id);
}
