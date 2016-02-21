package busy.company;

import java.io.Serializable;

public class Category implements Serializable{

	private static final long serialVersionUID = -2844610433599629096L;

	private int id;
	private String name;
	
	public Category() {
		
	}
	
	public Category(String name) {
		
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@SuppressWarnings("unused")
	private void setId(Integer id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

}