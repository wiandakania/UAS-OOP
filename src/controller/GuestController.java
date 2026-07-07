package controller;

import dao.GuestDAO;
import java.util.List;
import model.Guest;

/**
 *
 * @author WIANDA
 */
public class GuestController {

    private GuestDAO guestDAO = new GuestDAO();

    public GuestController() {
    }

    public int create(Guest guest) {
        if (guest.getName().trim().isEmpty()
                || guest.getIdNumber().trim().isEmpty()) {
            return -1;
        }
        return guestDAO.create(guest);
    }

    public List<Guest> getAll() {
        return guestDAO.getAll();
    }

    public int update(Guest guest, int id) {
        if (guest.getName().trim().isEmpty()
                || guest.getIdNumber().trim().isEmpty()) {
            return -1;
        }
        return guestDAO.update(guest);
    }

    public int delete(int id) {
        if (id <= 0) {
            return -1;
        }
        return guestDAO.delete(id);
    }

    public Guest search(int id) {
        if (id <= 0) {
            return null;
        }
        return guestDAO.search(id);
    }

    public List<Guest> getPage(int page) {
        if (page < 1) {
            page = 1;
        }

        int limit = 5;
        int offset = (page - 1) * limit;

        return guestDAO.getPage(limit, offset);
    }
        public int count() {
        return guestDAO.count();
        }
}