package test.service.mssql;

import exception.DataAccessException;
import exception.DataWriteException;
import model.OrderInvoice;
import model.Price;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.connection.mssql.MsSqlPersistenceConnection;
import persistence.repository.OrderInvoiceRepository;
import persistence.repository.mssql.MsSqlOrderInvoiceRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MsSqlOrderInvoiceRepositoryTest {
    private final OrderInvoiceRepository orderInvoiceRepository = new MsSqlOrderInvoiceRepository();

    @BeforeAll
    static void setUpAll() throws SQLException {
        MsSqlPersistenceConnection.getInstance().startTransaction();
    }

    @Test
    @DisplayName("findById(id) finds the matching order invoice if it exists")
    void canFindOrderInvoiceByExistingId() throws DataAccessException {
        // Arrange
        final OrderInvoice orderInvoice;
        final int id = 1;

        // Act
        orderInvoice = orderInvoiceRepository.findById(id);

        // Assert
        assertNotNull(orderInvoice);
    }

    @Test
    @DisplayName("findById(id) throws DataAccessException if order doesn't exist in database")
    void cantFindOrderLineIfIdDoesntExist() {
        // Arrange
        final int id = 500;

        assertThrows(DataAccessException.class, () -> orderInvoiceRepository.findById(id));
    }

    @Test
    @DisplayName("create(orderId, orderInvoice) doesn't throw an exception if orderId exists, and orderInvoice has valid data")
    void canCreateOrderLineIfAllDataIsCorrect() throws DataWriteException {
        // Arrange
        final int id = 3;
        final OrderInvoice orderInvoice = new OrderInvoice();
        orderInvoice.setToPay(new Price(500 * 500));
        orderInvoice.setHasPaid(new Price(0));
        orderInvoice.setDueDate(LocalDate.of(2021, 2, 5));
        orderInvoice.setCreatedAt(LocalDateTime.of(2020, 10, 28, 6, 45));
        final OrderInvoice returnOrderInvoice;

        // Act
        returnOrderInvoice = orderInvoiceRepository.create(id, orderInvoice);

        // Assert
        assertNotNull(returnOrderInvoice);
    }

    @Test
    @DisplayName("create(orderId, orderInvoice) throws DataWriteException if there's already an order invoice with that id")
    void cantCreateOrderLineIfOrderAlreadyHasAnInvoice() {
        // Arrange
        final int id = 2;
        final OrderInvoice orderInvoice = new OrderInvoice();
        orderInvoice.setToPay(new Price(500 * 500));
        orderInvoice.setHasPaid(new Price(0));
        orderInvoice.setDueDate(LocalDate.of(2021, 2, 5));
        orderInvoice.setCreatedAt(LocalDateTime.of(2020, 10, 28, 6, 45));

        assertThrows(DataWriteException.class, () -> orderInvoiceRepository.create(id, orderInvoice));
    }

    @AfterAll
    static void tearDownAll() throws SQLException {
        MsSqlPersistenceConnection.getInstance().commitTransaction();
    }
}
