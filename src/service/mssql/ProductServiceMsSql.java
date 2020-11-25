package service.mssql;

import dao.ProductDao;
import dao.mssql.DaoFactoryMsSql;
import dao.mssql.ProductDaoMsSql;
import entity.Product;
import exception.DataAccessException;
import service.ProductService;

public class ProductServiceMsSql implements ProductService {
    ProductDao dao = new ProductDaoMsSql();

    @Override
    public Product findById(int id) throws DataAccessException {
        return dao.findById(id);
    }
}
