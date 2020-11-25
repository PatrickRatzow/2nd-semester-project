package service.mssql;

import dao.ProductCategoryDao;
import dao.ProductDao;
import dao.mssql.ProductCategoryDaoMsSql;
import dao.mssql.ProductDaoMsSql;
import dto.ProductCategoryDto;
import exception.DataAccessException;
import entity.Product;
import entity.ProductCategory;
import service.ProductCategoryService;

import java.util.List;

public class ProductCategoryServiceMsSql implements ProductCategoryService {
    final ProductCategoryDao productCategoryDao = new ProductCategoryDaoMsSql();
    final ProductDao productDao = new ProductDaoMsSql();

    private ProductCategory buildObject(final ProductCategoryDto productCategoryDto) throws DataAccessException {
        final ProductCategory productCategory = new ProductCategory();
        productCategory.setId(productCategoryDto.getId());
        productCategory.setName(productCategoryDto.getName());
        productCategory.setDesc(productCategoryDto.getDesc());

        for (final int id : productCategoryDto.getProductIds()) {
            final Product product = productDao.findById(id);
            productCategory.addProduct(product);
        }

        return productCategory;
    }

    @Override
    public ProductCategory findById(final int id) throws DataAccessException {
        return buildObject(productCategoryDao.findById(id));
    }

    @Override
    public List<ProductCategory> findByName(final String name) throws DataAccessException {
        return null;
    }
}
