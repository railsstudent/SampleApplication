package com.sample.sampleapplication;

/**
 * Created by connieleung on 1/18/15.
 */
public class Person {

    private String firstname;
    private String lastname;
    private GENDER gender;

    public enum GENDER { MALE, FEMALE };

    public Person(String firstname, String lastname, GENDER gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    public String getLastname() {
        return lastname;
    }

    public GENDER getGender() {
        return gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!firstname.equals(person.firstname)) return false;
        if (gender != person.gender) return false;
        if (!lastname.equals(person.lastname)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstname.hashCode();
        result = 31 * result + lastname.hashCode();
        result = 31 * result + gender.hashCode();
        return result;
    }
}
