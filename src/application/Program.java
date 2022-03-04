package application;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) throws SQLException {
				
		SellerDao sellerDao = DaoFactory.createSellerDao();
		Seller seller;
		
//		System.out.println("==== TEST 1 - Seller FindById ====");
//		seller = sellerDao.findById(3);
//		System.out.println(seller);
//		System.out.println();
//		
//		System.out.println("==== TEST 2 - Seller FindByDepartment ====");
//		Department department = new Department(2, null);
//		List<Seller> list = sellerDao.findByDepartment(department);
//		for(Seller obj : list) {
//			System.out.println(obj);
//		}
//		System.out.println();
//		
//		System.out.println("==== TEST 3 - Seller FindAll ====");
//		List<Seller> listAll = sellerDao.findAll();
//		for(Seller obj : listAll) {
//			System.out.println(obj);
//		}
//		System.out.println();
//		
//		System.out.println("==== TEST 4 - Seller Insert ====");
//		seller = new Seller(null, "Heron Purple", "heron@gmail.com", new Date(), 6000.0, department);
//		
//		sellerDao.insert(seller);
//		System.out.println();
		
		System.out.println("==== TEST 5 - Seller Update ====");
		Seller su = new Seller();
		
		su.setBaseSalary(500.0);
		su.setId(10);
		sellerDao.update(su);

	}
}
