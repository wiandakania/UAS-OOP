package controller;

import dao.ReservationDAO;
import dao.RoomDAO;
import java.util.List;
import model.Reservation;
import model.Room;

public class ReservationController {

    private ReservationDAO reservationDAO = new ReservationDAO();
    private RoomDAO roomDAO = new RoomDAO();

    public ReservationController() {
    }

    public int create(Reservation r) {
        if (r == null
                || r.getCheckIn() == null
                || r.getCheckOut() == null
                || !r.getCheckOut().isAfter(r.getCheckIn())) {
            return -1;
        }

        Room room = roomDAO.search(r.getRoomId());
        if (room == null || !"AVAILABLE".equals(room.getStatus())) {
            return -1;
        }

        long nights = java.time.temporal.ChronoUnit.DAYS.between(
                r.getCheckIn(), r.getCheckOut()
        );

        if (nights <= 0) return -1;

        r.setTotalPrice(nights * room.getPricePerNight());
        r.setStatus("CONFIRMED");

        int result = reservationDAO.create(r);

        if (result == 1) {
            room.setStatus("OCCUPIED");
            roomDAO.update(room);
        }

        return result;
    }

    public List<Reservation> getAll() {
        return reservationDAO.getAll();
    }

    public int update(Reservation r, int id) {
        if (r == null || id <= 0 || r.getStatus().trim().isEmpty()) {
            return -1;
        }

        r.setId(id);
        return reservationDAO.update(r);
    }

    public int delete(int id) {
        if (id <= 0) return -1;

        Reservation r = reservationDAO.search(id);

        if (r != null) {
            if ("CONFIRMED".equals(r.getStatus())
                    || "CHECKED_IN".equals(r.getStatus())) {

                Room room = roomDAO.search(r.getRoomId());
                if (room != null) {
                    room.setStatus("AVAILABLE");
                    roomDAO.update(room);
                }
            }
        }

        return reservationDAO.delete(id);
    }

    public Reservation search(int id) {
        if (id <= 0) return null;

        return reservationDAO.search(id);
    }
    
    public int complete(int id) {
    if (id <= 0) return -1;

    Reservation r = reservationDAO.search(id);
    if (r == null) return -1;

    r.setStatus("COMPLETED");

    return reservationDAO.update(r);
}
    
    public int cancel(int id) {
    if (id <= 0) return -1;

    Reservation r = reservationDAO.search(id);
    if (r == null) return -1;

    r.setStatus("CANCELLED");

    Room room = roomDAO.search(r.getRoomId());
    if (room != null) {
        room.setStatus("AVAILABLE");
        roomDAO.update(room);
    }

    return reservationDAO.update(r);
}

    public List<Reservation> getPage(int page) {
        if (page < 1) page = 1;

        int limit = 5;
        int offset = (page - 1) * limit;

        return reservationDAO.getPage(limit, offset);
    }

    public int count() {
        return reservationDAO.count();
    }
   public int update(Reservation r) {
    return reservationDAO.update(r);
    }
}