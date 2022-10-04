package com.project.boot.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Id;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.project.boot.dao.ProductDao;
import com.project.boot.entity.Product;
import com.project.boot.validation.Validation;

@Service
public class ProductServiceImpl implements ProductService {
	
	String excludedRow = "";
	
	@Autowired
	private ProductDao dao;

	@Override
	public boolean saveProduct(Product product) {
		// set id in timestamp of 17 digit
		String Id = null;
		if ((product.getProductId()) != Id)
			Id = new SimpleDateFormat("yyyyMMddHHmmsss").format(new java.util.Date());
		product.setProductId(Id);
		boolean isAdded = dao.saveProduct(product);
		return isAdded;
	}

	@Override
	public List<Product> getAllProduct() {
		List<Product> list = dao.getAllProduct();
		return list;
	}

	@Override
	public Product getByProductId(String productId) {
		Product product = dao.getByProductId(productId);
		return product;
	}

	@Override
	public boolean updateProduct(Product product) {
		boolean isUpdated = dao.updateProduct(product);
		return isUpdated;

	}

	@Override
	public boolean deleteProductById(String productId) {
		boolean isDeleted = dao.deleteProductById(productId);
		return isDeleted;

	}

	@Override
	public List<Product> sortProducts(String sortByAny) {
		List<Product> list = dao.sortProducts(sortByAny);
		return list;

	}

	@Override
	public List<Product> sortProducts_Desc(String sortByAny) {
		List<Product> list = dao.sortProducts_Desc(sortByAny);
		return list;
	}

	@Override
	public double countSumOfPriceProduct() {
		double sum = dao.countSumOfPriceProduct();
		return sum;
	}

	@Override
	public long gettotalCountProduct() {
		long count = dao.gettotalCountProduct();
		return count;
	}

	@Override
	public Product maxPriceProduct() {
		Product product = dao.maxPriceProduct();
		return product;
	}

	/*****************************************************************************/
//flow is 	
	// client to server file store in webapp (fileoutputstream)
	// read data from webapp folder by fileinputstream

	public List<Product> readExcelfile(String filepath) {

		// for read file
		FileInputStream fileInputStream = null;
		List<Product> listOfProduct = new ArrayList<Product>();
		Product product = null;

		try {
			fileInputStream = new FileInputStream(new File(filepath));
			// flow be like workbook -> sheet-> rows->cell
			Workbook workbook = new XSSFWorkbook(fileInputStream);
			Sheet sheetAt = workbook.getSheetAt(0);
			Iterator<Row> rows = sheetAt.rowIterator();

			while (rows.hasNext()) {
				Row row = rows.next();
				product = new Product();

				Thread.sleep(1); // need to slow processing due to same id generation

				String Id = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new java.util.Date());
				product.setProductId(Id);

				Iterator<Cell> cells = row.cellIterator();
				// cells -> cell (single box)
				while (cells.hasNext()) {
					Cell cell = cells.next();

					int columnindex = cell.getColumnIndex();
					switch (columnindex) {
					case 0:
						product.setProductName(cell.getStringCellValue());

						break;
					case 1:
						product.setProductQuantity((int) cell.getNumericCellValue());

						break;
					case 2:
						product.setProductPrice(cell.getNumericCellValue());

						break;
					case 3:
						product.setProductType(cell.getStringCellValue());

						break;

					}

				}
				boolean vaildationProduct = Validation.vaildationProduct(product);
				if (vaildationProduct) {
					listOfProduct.add(product);

				} else {
					int rowNum = row.getRowNum() + 1;
					excludedRow = excludedRow + rowNum +","; 
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return listOfProduct;
	}

	/******************************************************************************/

	@Override
	public int uploadSheet(CommonsMultipartFile file, HttpSession httpsession) {
		// file read and write both comes under this
		// getServletContext ->The object of ServletContext provides an interface
		// between the container and servlet.

		String path = httpsession.getServletContext().getRealPath("/");
		System.out.println(path); // path of the file present
		String fileName = file.getOriginalFilename();

		int countOfProduct = 0;
		// Java FileOutputStream is an output stream used for writing data to a file.
		// If you have to write primitive values into a file, use FileOutputStream
		// class.

		// get file data in the form of byte and store in the form of array
		byte[] data = file.getBytes();
		try {
			// to write a data
			FileOutputStream fos = new FileOutputStream(new File(path + File.separator + fileName));
			fos.write(data);

			List<Product> listOfProduct = readExcelfile(path + File.separator + fileName);
//			for (Product product : listOfProduct) {
//				System.out.println(product);
//			}

			countOfProduct = dao.saveProductList(listOfProduct); // method from dao;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return countOfProduct;
	}

}
