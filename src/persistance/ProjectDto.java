package persistance;

import model.Price;

import java.util.HashSet;
import java.util.Set;

public class ProjectDto {
    private int id;
    private String name;
    private Price price;
    private Set<Integer> employeeIds = new HashSet<>();
    private Set<Integer> customerIds = new HashSet<>();
    private Set<Integer> orderIds = new HashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public void addEmployeeId(int id) {
        employeeIds.add(id);
    }

    public Set<Integer> getEmployeeIds() {
        return employeeIds;
    }

    public void addCustomerId(int id) {
        customerIds.add(id);
    }

    public Set<Integer> getCustomerIds() {
        return customerIds;
    }

    public void addOrderId(int id) {
        orderIds.add(id);
    }

    public Set<Integer> getOrderIds() {
        return orderIds;
    }
}
