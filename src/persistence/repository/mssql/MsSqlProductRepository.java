package persistence.repository.mssql;

import exception.DataAccessException;
import model.Product;
import persistence.dao.ProductDao;
import persistence.dao.mssql.MsSqlProductDao;
import persistence.repository.ProductRepository;

public class MsSqlProductRepository implements ProductRepository {
    final ProductDao productDao = new MsSqlProductDao();

    @Override
    public Product findById(int id) throws DataAccessException {
        return productDao.findById(id);
    }
}
