package jdbc.lesson.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.lesson.Connect.Connect;
import jdbc.lesson.Entities.Interest;

public class InterestDao {
	
	private Connect connection = new Connect();
	
	public Interest get(Long id){
		
			Interest interest=null;
			Statement statement = connection.getStatment();
			ResultSet resSet;
			
			try {
				resSet = statement.executeQuery("SELECT * FROM interest WHERE id="+id);
				while(resSet.next()){
					interest = new Interest(resSet.getLong(1),resSet.getString(2));
				}
				resSet.close();
				statement.close();
			return interest;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public Long save(Interest interest){
				
		try {
			if(interest.getId()==null){
				PreparedStatement prepState = connection.getConnection().prepareStatement("INSERT INTO interest (title) VALUES ('"+interest.getTitle()+
						"')", Statement.RETURN_GENERATED_KEYS);
				int rowsAffected = prepState.executeUpdate();
				if(rowsAffected!=0){
					System.out.println("INTEREST Added");
				}
				try(ResultSet genKey = prepState.getGeneratedKeys()){
					if(genKey.next()){
							Long key = genKey.getLong(1);
							genKey.close();
							prepState.close();
						return key;
					}
				}
			}
			
			Statement statement = connection.getStatment();
			ResultSet resSet;
			
			resSet = statement.executeQuery("SELECT * FROM interest");
			while(resSet.next()){
				if(resSet.getLong(1)==interest.getId()){
					int rowsAffected = statement.executeUpdate("UPDATE interest SET title='"+interest.getTitle()+"' WHERE id="+interest.getId());
					if(rowsAffected!=0){
						System.out.println("INTEREST Updated");
					}
				return interest.getId();
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
