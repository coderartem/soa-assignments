package jdbc.lesson.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.lesson.Connect.Connect;
import jdbc.lesson.Entities.Location;

public class LocationDao {
	
	private Connect connection = new Connect();
	
	public Location get(Long id){
		Location location = null;
		ResultSet resSet = null;
		Statement statement = connection.getStatment();
		try {
				resSet = statement.executeQuery("SELECT * FROM \"location\" WHERE id="+id);
				while(resSet.next()){
					location = new Location (resSet.getLong(1),resSet.getString(2),resSet.getString(3),resSet.getString(4));
				}
			return location;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				resSet.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	public Long save(Location location){
		
		try {
			//Add New Location
			if(location.getId()==null){
				PreparedStatement prepState = connection.getConnection().prepareStatement("INSERT INTO \"location\" (city,state,country) VALUES ('"+
						location.getCity()+"','"+location.getState()+"','"+location.getCountry()+"')", Statement.RETURN_GENERATED_KEYS);
				int rowsAffected = prepState.executeUpdate();
				if(rowsAffected!=0){
					System.out.println("LOCATION Added");
				}
				try(ResultSet genKeys = prepState.getGeneratedKeys()){
					if(genKeys.next()){
						Long key = genKeys.getLong(1);
						prepState.close();
						return key;
					}
				}
			}
			
			//Update Location
			ResultSet resSet = null;
			Statement statement = connection.getStatment();
			resSet = statement.executeQuery("SELECT id FROM \"location\"");
			while(resSet.next()){
				if(resSet.getLong(1)==location.getId()){
						int rowsAffected = statement.executeUpdate("UPDATE \"location\" SET city='"+location.getCity()+
								"',state='"+location.getState()+"',country='"+location.getCountry()+"' WHERE id="+location.getId());
						if(rowsAffected!=0){
							System.out.println("LOCATION Updated");
						}
						resSet.close();
						statement.close();
					return location.getId();
					}
			}
			resSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
