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

		PersonDao pD = new PersonDao();
		LocationDao lD = new LocationDao();
		InterestDao iD = new InterestDao();
		
		
		//System.out.println(iD.get(new Long(20)));
		//System.out.println(lD.get(new Long(20)));
		//System.out.println(pD.get(new Long(21)));
		
		/*HashSet<Interest> i = new HashSet<Interest>();
		i.add(new Interest(null, "Dance"));
		i.add(new Interest(null, "Run"));
		pD.save(new Person(null,"Doe","Joe",new Long(25),new Location(null,"Miami","Florida","USA"), i));
		*/
		
		//lD.save(new Location(new Long(16),"Dallas","Texas","USA"));
		//iD.save(new Interest(new Long(17),"Reading"));
		/*Set<Interest> k = new HashSet<Interest>();
		k.add(new Interest(new Long(20), "Footbal"));
		k.add(new Interest("Fishing"));
		pD.save(new Person(new Long(21),"Alice","Shmalice",new Long(20),new Location("WonderCity","WonderLand","WonderCountry"), k));
		*/
		
		//iD.save(new Interest("Hockey"));
				
	}

}
