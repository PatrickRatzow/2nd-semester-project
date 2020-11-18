package persistence.repository.mssql.dto;

import java.util.HashSet;
import java.util.Set;

public class ProjectDto {
    private final int id;
    private final String name;
    private final int price;
    private final Set<Integer> employeeIds = new HashSet<>();
    private final Set<Integer> customerIds = new HashSet<>();
    private final Set<Integer> orderIds = new HashSet<>();

    public ProjectDto(final int id, final String name, final int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
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
