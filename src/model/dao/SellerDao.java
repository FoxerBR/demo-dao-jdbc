package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.entities.Seller;
import model.entities.Department;

public interface SellerDao {

	public void insert(Seller obj);
	public void update(Seller obj);
	public void deleteById(Integer id) throws SQLException;
	Seller findById(Integer id) throws SQLException;
	List<Seller> findAll() throws SQLException;
	List<Seller> findByDepartment(Department departament);
}
