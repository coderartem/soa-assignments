package jdbc.lesson.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import jdbc.lesson.Connect.Connect;
import jdbc.lesson.Entities.Interest;
import jdbc.lesson.Entities.Person;

public class PersonDao {
	
	private Connect connection = new Connect();
	private LocationDao locationDao = new LocationDao();
	private InterestDao interestDao = new InterestDao();
	
	public Person get(Long id){
		Person person = null;
		ResultSet resSet =null;
		Statement statment = connection.getStatment();
		try {
			resSet = statment.executeQuery("SELECT * FROM people WHERE id ="+id+";");
			while(resSet.next()){
				person = new Person(
						resSet.getLong(1),
						resSet.getString(2),
						resSet.getString(3),
						resSet.getLong(4),
						locationDao.get(resSet.getLong(5)),
						getPersonInterests(resSet.getLong(1)));
				
			}
			resSet.close();
			statment.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return person;
	}
	
	
	public void save(Person person){
		
		try {
			//New Person should be created
			//---------------------------------------------------------------------
			if(person.getId()==null){
				
				//Add to location DB and get back location key
				Long locKey = saveLocation(person);
				
				//Add to person DB
				PreparedStatement prepState = connection.getConnection().prepareStatement("INSERT INTO people (first_name,last_name,age,loc_id) VALUES ('"+
						person.getFirstName()+"','"+person.getLastName()+"',"+person.getAge()+","+locKey+")", Statement.RETURN_GENERATED_KEYS);
				int rowsAffected = prepState.executeUpdate();
				if(rowsAffected!=0){
					System.out.println("PERSON added");
				}
				//Get generated key in person DB
				Long personKey = null; 
				try(ResultSet genKey = prepState.getGeneratedKeys()){
					if(genKey.next()){
						personKey = genKey.getLong(1);
					}
				}
				//Add to interest DB and person_interest DB
				adjustPersonInterest(person,personKey);
				
				prepState.close();
				return;
			}
			
			//----------------------------------------------------------------------
			//Person should be updated
			//----------------------------------------------------------------------
			ResultSet resSet =null;
			Statement statment = connection.getStatment();
			
			resSet = statment.executeQuery("SELECT id FROM people");
			//Check if person's id is exist in person DB 
			while (resSet.next()){
				if(resSet.getLong(1)==person.getId()){
					
					//Add to location DB and get back location key
					Long locKey = saveLocation(person);
					
					//Update person DB
					int rowsAffected = statment.executeUpdate("UPDATE people SET first_name='"+person.getFirstName()+"',last_name='"+person.getLastName()+
							"',age="+person.getAge()+", loc_id="+locKey+" WHERE id="+person.getId());
					if(rowsAffected!=0){
						System.out.println("PERSON Updated");
					}
										
					//Add to interest DB and person_interest DB
					adjustPersonInterest(person, person.getId());
					
					resSet.close();
					statment.close();
					return;
				}
			}
			
			throw new SQLException("There is no such Person in DB");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Long saveLocation(Person person) throws SQLException{
		//Add to location DB and get back location key
		Long locKey = locationDao.save(person.getLocation());
		if(locKey==null){
			throw new SQLException("No such LOCATION in DB");
		}
		return locKey;
	}
	
	public void adjustPersonInterest(Person person, Long personKey){
		
		Statement statement = connection.getStatment();

		try {
		statement.executeUpdate("DELETE FROM people_interest WHERE person_id="+personKey);
		System.out.println("Deleted in person_interest DB");
		statement.close();
		
		for(Interest x : person.getPersonInterests()){
			Long interestKey = interestDao.save(x);
			if(interestKey==null){
				throw new SQLException("No such INTEREST in DB");
			}
			Statement perIntDbUpdate = connection.getStatment();
			perIntDbUpdate.executeUpdate("INSERT INTO people_interest (person_id,interest_id) VALUES ("+personKey+","+interestKey+")");
			perIntDbUpdate.close();
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Set<Person> findInterestGroup(String Interest, String Location){
		ResultSet resSet =null;
		Statement statment = connection.getStatment();
		Set<Person> people = new HashSet<Person>();
		try {
			resSet = statment.executeQuery("select * from interestFunction('"+Interest+"','"+Location+"')");
			/**
			 * Stored Procedure I use in DataBase
			 * 
			 * create or replace function interestFunction(Intrst text, Locatn text)
				returns table ("id" bigint, First_Name text, Last_Name text, age bigint, Title text, City text)
				as $$
				begin
				return query
				
				select people.id, people.first_name, people.last_name, people.age, interest.title, "location".city
				from 
				(((people inner join "location" on people.loc_id = "location".id)
				     inner join people_interest on people_interest.person_id = people.id)
				     inner join interest on interest.id = people_interest.interest_id)
				     where interest.title = Intrst and ("location".city = Locatn or "location".state = Locatn 
				     or "location".country = Locatn);
				     
				  end;   $$
				  language 'plpgsql';
			 */
			while(resSet.next()){
				people.add(get(resSet.getLong(1)));
			}
			statment.close();
			resSet.close();
			return people;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Set<Interest> getPersonInterests(Long id){
		Set<Interest> setOfInterests = new HashSet<Interest>();
		ResultSet resSet =null;
		Statement statment = connection.getStatment();
		try {
			resSet = statment.executeQuery("SELECT * FROM findPersonInterests("+id+");");
			/**
			 * Stored Procedure I use in DataBase
			 * 
			 * create or replace function findPersonInterests(Person bigint)
				returns table (int_id bigint, Interests text)
				as $$
				begin
				return query
				select interest.id, interest.title from interest
				inner join people_interest on people_interest.interest_id = interest.id
				inner join people on people.id = people_interest.person_id
				where people.id = Person;
				
				end; $$
				language 'plpgsql';
			 */
			while(resSet.next()){
				setOfInterests.add(new Interest(resSet.getLong(1),resSet.getString(2)));
			}
			statment.close();
			resSet.close();
			return setOfInterests;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	
	}
}
