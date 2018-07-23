package shareit.common;

import java.util.List;

public class Person {
    private final long personId;
    private final String firstName;
    private final String secondName;
    private final long itemId;
    private final List<Address> addresses;

    public Person(final long personId, final String firstName, final String secondName, final long itemId, final List<Address> addresses) {
        this.personId = personId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.itemId = itemId;
        this.addresses = addresses;
    }

    //getters and setters

    public List<Address> getAddresses() {
        return addresses;
    }

     //useful for testing

    @Override
    public String toString() {
        return "Person{"
               + "personId='" + personId + '\''
               + ", firstName='" + firstName + '\''
                + ", secondName='" + secondName + '\''
                 + ", itemId='" + itemId + '\''
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
        if (!firstName.equals(person.firstName)) {
            return false;
        }
        if (!secondName.equals(person.secondName)) {
            return false;
        }
        
        if(itemId != person.itemId){
        	return false;
        }

        if(personId != person.personId){
        	return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + secondName.hashCode();
        result = 31 * result + addresses.hashCode();
        return result;
    }

	public long getPersonId() {
		return personId;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public long getItemId() {
		return itemId;
	}
}