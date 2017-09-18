package jdbc.lesson.Entities;

public class Interest {
	
	private Long id;
	private String title;
	
	
	public Interest(Long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
	
	public Interest(String title) {
		super();
		this.title = title;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
		Interest other = (Interest) obj;
		if (id == null) {	
//			Deleted if (other.id != null) to avoid situation when Set<Interest> 
//			don't want to add 2 or more null id's, because this equal() method based on "id" only, because that's 
//			the only way to connect to DB. In DB "id" set as "NOT NULL", so should not be a problem here
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		return "Interest: [id: "+id+", Title: "+title+"]; ";
	}

}
