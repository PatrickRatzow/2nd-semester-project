package dao.mssql;

import dao.OrderInvoiceDao;
import datasource.DBConnection;
import entity.OrderInvoice;
import entity.Price;
import exception.DataAccessException;
import exception.DataWriteException;
import util.SQLDateConverter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class OrderInvoiceDaoMsSql implements OrderInvoiceDao {
    private static final String FIND_BY_ID_Q = "SELECT * FROM GetOrderInvoices WHERE orderId = ?";
    private PreparedStatement findByIdPS;
    private static final String INSERT_Q = "INSERT INTO orders_invoices (orderId, createdAt, dueDate, toPay, hasPaid) "
            + "VALUES(?, ?, ?, ?, ?)";
    private PreparedStatement insertPS;

    public OrderInvoiceDaoMsSql(DBConnection conn) {
        init(conn);
    }

    private void init(DBConnection conn) {
        try {
            findByIdPS = conn.prepareStatement(FIND_BY_ID_Q);
            insertPS = conn.prepareStatement(INSERT_Q);
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
        final List<OrderInvoice> orderInvoices = new LinkedList<>();

        while (rs.next()) {
            orderInvoices.add(buildObject(rs));
        }

        return orderInvoices;
    }

    @Override
    public OrderInvoice findById(int id) throws DataAccessException {
        final OrderInvoice orderInvoice;

        try {
            findByIdPS.setInt(1, id);
            ResultSet rs = findByIdPS.executeQuery();
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
    public OrderInvoice create(int orderId, OrderInvoice orderInvoice) throws DataWriteException {
        try {
            insertPS.setInt(1, orderId);
            insertPS.setTimestamp(2,
                    SQLDateConverter.localDateTimeToTimestamp(orderInvoice.getCreatedAt()));
            insertPS.setDate(3, SQLDateConverter.localDateToDate(orderInvoice.getDueDate()));
            insertPS.setInt(4, orderInvoice.getToPay().getAmount());
            insertPS.setInt(5, orderInvoice.getHasPaid().getAmount());
            insertPS.execute();
        } catch (SQLException e) {
            throw new DataWriteException("Unable to save OrderInvoice with order id - " + orderId);
        }

        orderInvoice.setId(orderId);

        return orderInvoice;
    }
}
