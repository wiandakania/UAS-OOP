package controller;

import dao.PaymentDAO;
import java.util.List;
import model.Payment;

/**
 *
 * @author WIANDA
 */
public class PaymentController {

    private PaymentDAO paymentDAO = new PaymentDAO();

    public PaymentController() {
    }

    public int create(Payment payment) {
        if (payment.getReservationId() <= 0
                || payment.getAmount() <= 0) {
            return -1;
        }
        return paymentDAO.create(payment);
    }

    public List<Payment> getAll() {
        return paymentDAO.getAll();
    }

    public int update(Payment payment, int id) {
        if (payment.getReservationId() <= 0
                || payment.getAmount() <= 0) {
            return -1;
        }
        return paymentDAO.update(payment);
    }

    public int delete(int id) {
        if (id <= 0) {
            return -1;
        }
        return paymentDAO.delete(id);
    }

    public Payment search(int id) {
        if (id <= 0) {
            return null;
        }
        return paymentDAO.search(id);
    }

    public List<Payment> getPage(int page) {
        if (page < 1) {
            page = 1;
        }

        int limit = 5;
        int offset = (page - 1) * limit;

        return paymentDAO.getPage(limit, offset);
    }

    public double getTotalRevenue() {
        return paymentDAO.getTotalRevenue();
    }

    public int count() {
        return paymentDAO.count();
    }
}