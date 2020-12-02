package entity;

public class Project {
    private int id;
    private String name;
    private Order order;
    private Employee employee;
    private ProjectInvoice invoice;
    private Price price;
    private int estimatedHours;
    private ProjectStatus status;
    private Customer customer;


    public Project() {}

    public Project(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Project(int id, String name, Order order,
    		Employee employee, ProjectInvoice invoice, Price price, int estimatedHours,
                   ProjectStatus status, Customer customer) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.invoice = invoice;
        this.employee = employee;
        this.price = price;
        this.estimatedHours = estimatedHours;
        this.status = status;
        this.customer = customer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProjectInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(ProjectInvoice invoice) {
        this.invoice = invoice;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Price getPrice() {
        return price;
    }

    public void setPrice(Price price) {
        this.price = price;
    }

    public int getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
