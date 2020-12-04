package controller;

import dao.SpecificationToProductCategoryDao;
import datasource.DBManager;
import entity.Product;
import entity.Specification;
import entity.specifications.Roof;
import entity.specifications.Window;
import exception.DataAccessException;
import util.ConnectionThread;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SpecificationsController {
	private List<Consumer<List<Specification>>> onFindListeners = new LinkedList<>();
	private List<Consumer<Map<Specification, List<Product>>>> onSaveListeners = new LinkedList<>();
	private List<Specification> specifications = new LinkedList<>();

	public void addFindListener(Consumer<List<Specification>> listener) {
		onFindListeners.add(listener);
	}
	
	public void addSaveListener(Consumer<Map<Specification, List<Product>>> listener) {
		onSaveListeners.add(listener);
	}
	
    public void getSpecifications() {
        final List<Specification> specifications = new LinkedList<>();
        specifications.add(new Window());
        specifications.add(new Roof());
        
        onFindListeners.forEach(l -> l.accept(specifications));
    }
    
    public void setSpecifications(List<Specification> specifications) {
    	this.specifications = specifications;
    }
    
    private Thread findProductsBySpecification(Specification spec, Consumer<List<Product>> productsConsumer) {
        return new ConnectionThread(conn -> {
            SpecificationToProductCategoryDao dao =
                    DBManager.getDaoFactory().createSpecificationToProductCategoryDao(conn);
            try {
                List<Product> products = dao.findBySpecificationId(spec);

                productsConsumer.accept(products);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        });
    }

    public void getProductsFromSpecifications() {
    	new Thread(() -> {
    	    List<Specification> specificationsCopy = specifications;
            Map<Specification, List<Product>> specProductsMap = new HashMap<>();

            List<Thread> threads = new LinkedList<>();
            for (Specification specification : specificationsCopy) {
                final Specification specTemp = specification;
                threads.add(findProductsBySpecification(specTemp, ps -> specProductsMap.put(specTemp, ps)));
            }

            for (Thread t : threads) {
                t.start();
            }

            for (Thread t : threads) {
                try {
					t.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }

            onSaveListeners.forEach(l -> l.accept(specProductsMap));
    	}).start();
    }
}
