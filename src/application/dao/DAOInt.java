package application.dao;

import java.util.List;

public interface DAOInt<Type> {
	
	// write the input java bean into cvs file
	public void create(Type obj);
	
	// edit existing value in csv file
	public void update(Type obj);
	
	// read csv file and put entries into objects and store in list
	public List<Type> load();
	
	// search for specific entry
	public Type search(String id);
}
