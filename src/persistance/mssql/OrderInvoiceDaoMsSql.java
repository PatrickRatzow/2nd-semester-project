package persistance.mssql;

import exception.DataAccessException;
import model.DBConnection;
import model.OrderInvoice;
import model.Price;
import persistance.OrderInvoiceDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderInvoiceDaoMsSql implements OrderInvoiceDao {
    private static final String FIND_BY_ID_Q = "SELECT * FROM GetOrderInvoices WHERE orderId = ?";
    private PreparedStatement findById;

    public OrderInvoiceDaoMsSql() {
        init();
    }

    private void init() {
        final DBConnection con = DBConnection.getInstance();

        try {
            findById = con.prepareStatement(FIND_BY_ID_Q);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private OrderInvoice buildObject(ResultSet rs) throws SQLException {
        final OrderInvoice orderInvoice = new OrderInvoice();

        orderInvoice.setId(rs.getInt("orderId"));
        orderInvoice.setHasPaid(new Price(rs.getInt("invoiceHasPaid")));
        orderInvoice.setToPay(new Price(rs.getInt("invoiceToPay")));
        orderInvoice.setCreatedAt(LocalDateTime.now());
        orderInvoice.setDueDate(LocalDate.now());

        return orderInvoice;
    }

    private List<OrderInvoice> buildObjects(ResultSet rs) throws SQLException {
        final List<OrderInvoice> orderInvoices = new ArrayList<>();

        while (rs.next()) {
            orderInvoices.add(buildObject(rs));
        }

        return orderInvoices;
    }

    @Override
    public OrderInvoice findById(int id) throws DataAccessException {
        final OrderInvoice orderInvoice;

        try {
            findById.setInt(1, id);
            ResultSet rs = findById.executeQuery();
            if (!rs.next()) {
                throw new DataAccessException("Unable to find order invoice with id - " + id);
            }

            orderInvoice = buildObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Something when wrong when finding order invoice with id - " + id);
        }

        return orderInvoice;
    }

    @Override
    public OrderInvoice create(int orderId, LocalDateTime createdAt, LocalDate dueDate, int toPay, int hasPaid) {
        return null;
    }
}
