package controller;

import dao.SpecificationToProductCategoryDao;
import datasource.DBConnection;
import datasource.DBManager;
import entity.Product;
import entity.Requirement;
import entity.Specification;
import exception.DataAccessException;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class SpecificationController {
    private int displayId;
    private Specification specification;
    private List<Consumer<SpecificationController>> onSaveListeners = new LinkedList<>();
    
    public SpecificationController(Specification specification) {
        this.specification = specification;
    }

    public List<Requirement> getRequirements() {
        return specification.getRequirements();
    }
    
    public void setRequirements(List<Requirement> requirements) {
    	specification.setRequirements(requirements);
    }

    public void addSaveListener(Consumer<SpecificationController> listener) {
		onSaveListeners.add(listener);
	}
    
    public void save() {
    	onSaveListeners.forEach(l -> l.accept(this));
    }

    public void load() throws DataAccessException {
        final DBConnection connection = DBManager.getPool().getConnection();
        final SpecificationToProductCategoryDao dao =
                DBManager.getDaoFactory().createSpecificationToProductCategoryDao(connection);
        final List<Product> products = dao.findBySpecificationId(specification);

        connection.release();
    }

    public void setDisplayName(String name) {
    	specification.setDisplayName(name);
    }

    public String getDislayName() {
    	return specification.getName();
    }

    public void setResultAmount(int amount) {
    	specification.setResultAmount(amount);
    }

    public int getResultAmount() {
    	return specification.getResultAmount();
    }

    public Specification getSpecification() {
    	return specification;
    }

    public int getDisplayId() {
        return displayId;
    }

    public void setDisplayId(int displayId) {
        this.displayId = displayId;
    }


    /*
    public static void main(String[] args) {
        SpecificationController ctr = new SpecificationController(new Window());

        for (Requirement reg : ctr.getRequirements()) {
            switch (reg.getId()) {
                case "color":
                    reg.setValue(Color.BLUE);
                    break;

                case "width":
                    reg.setValue(150);
                    break;

                case "height":
                    reg.setValue(100);
                    break;
            }
        }

        try {
            ctr.load();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        ctr.getRequirements().forEach(r -> System.out.println(r.getName() + " - " + r.getValue()));
    }
    */
}
