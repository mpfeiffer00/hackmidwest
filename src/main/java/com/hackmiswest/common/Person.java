package com.hackmiswest.common;

import java.util.List;

public class Person {
    private final String id;
    private final String name;
    private final List<Address> addresses;
    //TODO map it to the item

    public Person(final String id, final String name, final List<Address> addresses) {
        this.id = id;
        this.name = name;
        this.addresses = addresses;
    }

    //getters and setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

     //useful for testing

    @Override
    public String toString() {
        return "Person{"
               + "id='" + id + '\''
               + ", name='" + name + '\''
               + ", addresses=" + addresses
               + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person person = (Person) o;

        if (!addresses.equals(person.addresses)) {
            return false;
        }
        if (!id.equals(person.id)) {
            return false;
        }
        if (!name.equals(person.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + addresses.hashCode();
        return result;
    }
}