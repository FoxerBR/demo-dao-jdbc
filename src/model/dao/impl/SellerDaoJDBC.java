package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{
	

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller obj) {
		
		PreparedStatement pst = null;
		
		try {
			pst = conn.prepareStatement("INSERT INTO seller"
					                  + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
					                  + "VALUES "
					                  + "(?, ?, ?, ?, ?)");
					                  
			pst.setString(1, obj.getName());
			pst.setString(2, obj.getEmail());
			pst.setDate(3, new java.sql.Date (obj.getBirthDate().getTime()));
			pst.setDouble(4, obj.getBaseSalary());
			pst.setInt(5, obj.getDepartment().getId());
			
			pst.executeUpdate();
			
			System.out.println("Inserido com sucesso");
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatment(pst);
		}
	}

	@Override
	public void update(Seller obj) {
		
		 PreparedStatement pst = null;
		 
		 try {
			 pst = conn.prepareStatement("UPDATE seller s "
			 		                   + "SET s.BaseSalary = s.BaseSalary + ? "
			 		                   + "WHERE s.Id = ?");
			 
			 pst.setDouble(1, obj.getBaseSalary());
			 pst.setInt(2, obj.getId());
			 
			 pst.executeUpdate();
			 
			 System.out.println("Atualizado com sucesso!");
		 }
		 catch (Exception e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatment(pst);
		}
		
	}

	@Override
	public void deleteById(Integer id) {

		PreparedStatement pst = null;
		
		try {
			
			pst = conn.prepareStatement("DELETE "
					                  + "FROM seller s "
					                  + "WHERE s.Id = ?");
			
			pst.setInt(1, id);
			
			int af = pst.executeUpdate();
			
			if(af > 0 ) {
				System.out.println("Deletado com sucesso!");
			}else {
				System.out.println("Registro não encontrado!");
			}
			
		}
		catch (Exception e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatment(pst);
		}
		
	}

	@Override
	public Seller findById(Integer id) {
		
		PreparedStatement pst = null;
		ResultSet rs = null;
		Seller seller = null;
		
		try {
			pst = conn.prepareStatement("SELECT s.*, d.Name AS DepName "
					                  + "FROM seller s, department d "
					                  + "WHERE s.DepartmentId = d.Id "
					                  + "AND s.Id = ?");
			
			pst.setInt(1, id);			
			rs = pst.executeQuery();
			
			if(rs.next()) {
				Department dep = instantDep(rs);
				seller = instantSeller(rs, dep);
			}else {
				return null;
			}
		}
		catch (SQLException e) {
			e.getMessage();
		}
		finally {
			DB.closeStatment(pst);
			DB.closeResultSet(rs);
		}
		
		return seller;
	}

	private Seller instantSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
	    seller.setBaseSalary(rs.getDouble("BaseSalary"));
	    seller.setDepartment(dep);
	    
	    return seller;
	}

	private Department instantDep(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		
		List <Seller> list = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("SELECT s.*, d.Name AS DepName "
					                  + "FROM seller s, department d "
					                  + "WHERE s.DepartmentId = d.Id "
					                  + "ORDER BY Name");
			
			rs = pst.executeQuery();		
			
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instantDep(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instantSeller(rs, dep);
			    list.add(seller);
			}			
		}
		catch (Exception e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatment(pst);
			DB.closeResultSet(rs);
		}
		return list;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		List <Seller> list = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("SELECT s.*, d.Name AS DepName "
					                  + "FROM seller s, department d "
					                  + "WHERE s.DepartmentId = d.Id "
					                  + "AND s.DepartmentId = ? "
					                  + "ORDER BY Name");
			
			pst.setInt(1, department.getId());
			
			rs = pst.executeQuery();	
			
			Map<Integer, Department> map = new HashMap<>();
			
			while(rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instantDep(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller seller = instantSeller(rs, dep);
			    list.add(seller);
			}
		
		}
		catch (Exception e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatment(pst);
			DB.closeResultSet(rs);
		}
		return list;
	}

}
