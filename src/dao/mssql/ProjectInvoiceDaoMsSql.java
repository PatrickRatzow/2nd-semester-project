package dao.mssql;

import dao.ProjectInvoiceDao;
import datasource.DBConnection;
import entity.Price;
import entity.ProjectInvoice;
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

public class ProjectInvoiceDaoMsSql implements ProjectInvoiceDao {
    private static final String FIND_BY_ID_Q = "SELECT * FROM project_invoice WHERE project_id = ?";
    private PreparedStatement findByIdPS;
    private static final String INSERT_Q = "INSERT INTO project_invoice (project_id, created_at, due_date, to_pay, has_paid) "
            + "VALUES(?, ?, ?, ?, ?)";
    private PreparedStatement insertPS;

    public ProjectInvoiceDaoMsSql(DBConnection conn) {
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

    private ProjectInvoice buildObject(ResultSet rs) throws SQLException {
        final ProjectInvoice projectInvoice = new ProjectInvoice();

        projectInvoice.setId(rs.getInt("project_id"));
        projectInvoice.setHasPaid(new Price(rs.getInt("has_paid")));
        projectInvoice.setToPay(new Price(rs.getInt("to_pay")));
        projectInvoice.setCreatedAt(LocalDateTime.now());
        projectInvoice.setDueDate(LocalDate.now());

        return projectInvoice;
    }

    private List<ProjectInvoice> buildObjects(ResultSet rs) throws SQLException {
        final List<ProjectInvoice> projectInvoices = new LinkedList<>();

        while (rs.next()) {
            projectInvoices.add(buildObject(rs));
        }

        return projectInvoices;
    }

    @Override
    public ProjectInvoice findByOrderId(int id) throws DataAccessException {
        final ProjectInvoice projectInvoice;

        try {
            findByIdPS.setInt(1, id);
            ResultSet rs = findByIdPS.executeQuery();
            if (!rs.next()) {
                throw new DataAccessException("Unable to find project invoice with id - " + id);
            }

            projectInvoice = buildObject(rs);
        } catch (SQLException e) {
            e.printStackTrace();

            throw new DataAccessException("Something when wrong when finding project invoice with id - " + id);
        }

        return projectInvoice;
    }

    @Override
    public ProjectInvoice create(int projectId, ProjectInvoice projectInvoice) throws DataWriteException {
        try {
            insertPS.setInt(1, projectId);
            insertPS.setTimestamp(2,
                    SQLDateConverter.localDateTimeToTimestamp(projectInvoice.getCreatedAt()));
            insertPS.setDate(3, SQLDateConverter.localDateToDate(projectInvoice.getDueDate()));
            insertPS.setInt(4, projectInvoice.getToPay().getAmount());
            insertPS.setInt(5, projectInvoice.getHasPaid().getAmount());
            insertPS.execute();
        } catch (SQLException e) {
            throw new DataWriteException("Unable to save ProjectInvoice with order id - " + projectId);
        }

        projectInvoice.setId(projectId);

        return projectInvoice;
    }
}



