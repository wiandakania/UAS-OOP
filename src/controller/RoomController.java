package controller;

import dao.RoomDAO;
import java.util.List;
import model.Room;

public class RoomController {

    private RoomDAO roomDAO = new RoomDAO();

    public RoomController() {
    }

    public int create(Room r) {
        if (r == null
                || r.getRoomNumber().trim().isEmpty()
                || r.getPricePerNight() <= 0
                || r.getCapacity() <= 0) {
            return -1;
        }

        return roomDAO.create(r);
    }

    public List<Room> getAll() {
        return roomDAO.getAll();
    }

    public int update(Room r, int id) {
        if (r == null
                || id <= 0
                || r.getRoomNumber().trim().isEmpty()
                || r.getPricePerNight() <= 0
                || r.getCapacity() <= 0) {
            return -1;
        }

        r.setId(id);
        return roomDAO.update(r);
    }

    public int delete(int id) {
        if (id <= 0) {
            return -1;
        }

        return roomDAO.delete(id);
    }

    public Room search(int id) {
        if (id <= 0) {
            return null;
        }

        return roomDAO.search(id);
    }

    public List<Room> getPage(int page) {
        if (page < 1) {
            page = 1;
        }

        int limit = 5;
        int offset = (page - 1) * limit;

        return roomDAO.getPage(limit, offset);
    }

    public List<Room> getAvailable() {
        return roomDAO.getAvailable();
    }

    public int count() {
        return roomDAO.count();
    }
}