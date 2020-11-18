package persistence.repository.mssql;

import exception.DataAccessException;
import model.Product;
import model.ProductCategory;
import persistence.dao.ProductCategoryDao;
import persistence.dao.mssql.MsSqlProductCategoryDao;
import persistence.repository.ProductCategoryRepository;
import persistence.repository.ProductRepository;
import persistence.repository.mssql.dto.ProductCategoryDto;

import java.util.List;

public class MsSqlProductCategoryRepository implements ProductCategoryRepository {
    final ProductCategoryDao productCategoryDao = new MsSqlProductCategoryDao();
    final ProductRepository productRepository = new MsSqlProductRepository();

    private ProductCategory buildObject(final ProductCategoryDto productCategoryDto) throws DataAccessException {
        final ProductCategory productCategory = new ProductCategory();
        productCategory.setId(productCategoryDto.getId());
        productCategory.setName(productCategoryDto.getName());
        productCategory.setDesc(productCategoryDto.getDesc());

        for (final int id : productCategoryDto.getProductIds()) {
            final Product product = productRepository.findById(id);
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
