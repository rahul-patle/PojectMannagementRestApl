package com.project.boot.dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.project.boot.entity.Product;
import com.project.boot.sort.SortByName;
import com.project.boot.sort.SortByPrice;

@Repository
public class ProductDaoImpl implements ProductDao {
	@Autowired
	SessionFactory sessionfactory;

	@Override
	public boolean saveProduct(Product product) {
		Session session = sessionfactory.openSession();
		Transaction transaction = session.beginTransaction();

		boolean isAdded = false;
		try {
			session.save(product);
			transaction.commit();
			isAdded = true;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (session != null)
				session.close();
		}
		return isAdded;
	}

	@Override
	public List<Product> getAllProduct() {
		Session session = sessionfactory.openSession();
		Criteria criteria = session.createCriteria(Product.class);

		List<Product> list = null;
		try {
			list = criteria.list();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (session != null)
				session.close();
		}
		return list;

	}

	@Override
	public Product getByProductId(String productId) {
		Session session = sessionfactory.openSession();
		Product product = null;
		try {
			product = session.get(Product.class, productId);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (session != null)
				session.close();
		}
		return product;
	}

	@Override
	public boolean updateProduct(Product product) {
		Session session = sessionfactory.openSession();
		boolean isUpdated = false;
		try {

			Transaction transaction = session.beginTransaction();
			Product prd = session.load(Product.class, product.getProductId());
			prd = product;
			session.update(prd);
			transaction.commit();
			isUpdated = true;

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (session != null) {
				session.close();
			}
		}

		return isUpdated;
	}

	@Override
	public boolean deleteProductById(String productId) {
		Session session = sessionfactory.openSession();

		boolean isDeleted = false;
		try {
			Transaction transaction = session.beginTransaction();

			Product product = session.get(Product.class, productId);
			session.delete(product);
			transaction.commit();
			isDeleted = true; // if not witten then give 1 in postman
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return isDeleted;
	}

	@Override
	public List<Product> sortProducts(String sortByAny) {

		List<Product> list = null;
		try {
			list = getAllProduct();
			if (sortByAny.equalsIgnoreCase("productPrice")) {
				Collections.sort(list, new SortByPrice());
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return list;
	}

	@Override
	public List<Product> sortProducts_Desc(String sortByAny) {

		List<Product> list = null;
		try {
			list = getAllProduct();
			if (sortByAny.equalsIgnoreCase("productPrice")) {
				Collections.sort(list, new SortByPrice());
				Collections.reverse(list);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return list;

	}
	@Override
	public Product maxPriceProduct() {
		List<Product> list;
		Product product = null;
		try {
			list = getAllProduct();
			Collections.sort(list, new SortByPrice());
			Collections.reverse(list);
			product = list.get(0);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return product;
	}

	@Override
	public double countSumOfPriceProduct() {
		List<Product> list = null;
		double sum = 0;
		try {
			list = getAllProduct();
			Stream<Product> stream = list.stream();
			Stream<Double> stream2 = stream.map((product) -> product.getProductPrice());
			Optional<Double> optional = stream2.reduce((product1, product2) -> (product1 + (product2)));
			sum = optional.get();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return sum;
	}

	@Override
	public long gettotalCountProduct() {
		List<Product> list = null;
		long count = 0;
		try {
			list = getAllProduct();

			count = list.size();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	// excel sheet list of product
	@Override
	public int saveProductList(List<Product> list) {
		int count = 0;
		Session session = null;
		Transaction transaction = null;
		try {
			// taking list as input from excel file to save in database
			// iterate list an saving one by one product
			for (Product product : list) {
				session = sessionfactory.openSession();  //for each iteration new session required
				transaction = session.beginTransaction();
				session.save(product);
				transaction.commit();
				count++;

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return count;
	}

}
