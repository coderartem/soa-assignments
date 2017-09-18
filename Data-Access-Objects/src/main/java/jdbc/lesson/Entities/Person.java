package jdbc.lesson.Entities;

import java.util.Set;

public class Person {

	private Long id;
	private String firstName;
	private String lastName;
	private Long age;
	private Location location;
	private Set<Interest> personInterests;
	
	
	public Person(Long id, String firstName, String lastName, Long age, Location location, Set<Interest> personInterests) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.location = location;
		this.personInterests = personInterests;
	}
	
	public Person(String firstName, String lastName, Long age, Location location, Set<Interest> personInterests) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.location = location;
		this.personInterests = personInterests;
	}
	
	
	public Set<Interest> getPersonInterests() {
		return personInterests;
	}
	public void setPersonInterests(Set<Interest> personInterests) {
		this.personInterests = personInterests;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Long getAge() {
		return age;
	}
	public void setAge(Long age) {
		this.age = age;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		return "Person: [id: "+id+", First Name: "+firstName+", Last Name: "+lastName+", Age: "+age+"; Location: ("+location.getCity()+", "+
				location.getState()+", "+location.getCountry()+")"+"; Interests: ("+interestsToString()+") ];";
	}
	
	public String interestsToString(){
		String result = "";
		for(Interest x : personInterests){
			result+=x.getTitle()+"; ";
		}
		return result;
	}

}

