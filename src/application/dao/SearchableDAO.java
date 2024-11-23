package application.dao;

import java.util.List;

public interface SearchableDAO<Type> {
	// return all entries containing substring
	public List<Type> search(String subStr);
}
