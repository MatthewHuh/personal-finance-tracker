package application.dao;

public interface DAOInt<Type> {
	
	// write the input java bean into cvs file
	public void create(Type obj);
	
	// edit existing value in csv file
	public void update(Type curr, Type update);
}
