package controller;

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
import java.util.Set;
import java.util.function.Consumer;

import dao.SpecificationToProductCategoryDao;
import datasource.DBConnection;
import datasource.DBManager;

public class SpecificationsController {
	private List<Consumer<List<Specification>>> onFindListeners = new LinkedList<>();
	private List<Consumer<Map<Specification, List<Product>>>> onSaveListeners = new LinkedList<>();
	private ProjectController projectController;
	private Map<Specification, Void> specMap;
	
	public SpecificationsController(ProjectController projectController) {
		this.projectController = projectController;
		specMap = new HashMap<>();
	}
    
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
    
    public void addSpecification(Specification spec) {
    	specMap.put(spec, null);
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
    		Set<Specification> specifications = specMap.keySet();
            Map<Specification, List<Product>> specProductsMap = new HashMap<>();

            List<Thread> threads = new LinkedList<>();
            for (Specification specification : specifications) {
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
