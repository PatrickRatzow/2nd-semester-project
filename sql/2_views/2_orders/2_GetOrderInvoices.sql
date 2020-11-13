CREATE VIEW GetOrderInvoices AS
SELECT
    ol.orderId AS orderId,
    ol.createdAt AS invoiceCreatedAt,
    ol.dueDate AS invoiceDueDate,
    ol.toPay as invoiceToPay,
    ol.hasPaid AS invoiceHasPaid
FROM orders_invoices ol