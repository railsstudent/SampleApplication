package com.sample.sampleapplication;

import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by connieleung on 1/18/15.
 */
public class PersonServiceHelper {

    private static PersonService service;

    // http://stackoverflow.com/questions/5495534/java-net-connectexception-localhost-127-0-0-18080-connection-refused
    static {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://10.0.2.2:8080/app/rest")
                .build();
        service = restAdapter.create(PersonService.class);
    }

    private PersonServiceHelper() {
    }

    public static List<Person> getPeople() {
        return service.listPeople();
    }

    public static Person getPersonById(String id) {
        return service.getPerson(id);
    }
}
