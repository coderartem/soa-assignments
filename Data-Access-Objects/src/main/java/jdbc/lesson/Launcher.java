package jdbc.lesson;

import java.util.HashSet;
import java.util.Set;

import jdbc.lesson.Dao.InterestDao;
import jdbc.lesson.Dao.LocationDao;
import jdbc.lesson.Dao.PersonDao;
import jdbc.lesson.Entities.Interest;
import jdbc.lesson.Entities.Location;
import jdbc.lesson.Entities.Person;

public class Launcher {

	public static void main(String[] args) {

		PersonDao personDao = new PersonDao();
		LocationDao locationDao = new LocationDao();
		InterestDao interestDao = new InterestDao();
		
		/**
		 * Test of get(id) methods
		 */
//		System.out.println(interestDao.get(new Long(20)));
//		System.out.println(locationDao.get(new Long(20)));
//		System.out.println(personDao.get(new Long(21)));
		
		/**
		 * Test of save(object) methods with null id's (creating new entries in all DBs)
		 */
//		HashSet<Interest> intersts = new HashSet<>();
//		intersts.add(new Interest(null, "Dance"));
//		intersts.add(new Interest(null, "Run"));
//		personDao.save(new Person(null,"Doe","Joe",new Long(25),new Location(null,"Miami","Florida","USA"), intersts));
//		
//		interestDao.save(new Interest("Hockey"));
		
		
		/**
		 * Test of save() methods  with not null id's (updating existing records in all DB's)
		 */
//		Set<Interest> interests = new HashSet<Interest>();
//		interests.add(new Interest(new Long(20), "Footbal"));
//		interests.add(new Interest("Fishing"));
//		personDao.save(new Person(new Long(21),"Alice","Shmalice",new Long(20),new Location("WonderCity","WonderLand","WonderCountry"), interests));
//		
//		locationDao.save(new Location(new Long(16),"Dallas","Texas","USA"));
//		interestDao.save(new Interest(new Long(17),"Reading"));
		
		
		/**
		 * Test of findInterestGroup("Interest", "Location") method
		 */
		Set<Person> sP = personDao.findInterestGroup("fart","USA");
		for(Person x : sP){
			System.out.println(x);
		}
				
	}

}
