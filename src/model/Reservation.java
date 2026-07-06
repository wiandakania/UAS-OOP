package model;

import java.time.LocalDate;

public class Reservation {

    private int id;
    private int guestId;
    private int roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private double totalPrice;
    private String status;

    // tambahan
    private String guestName;
    private String roomNumber;
    private String roomType;

    public Reservation() {
    }

    public Reservation(int id,
            int guestId,
            int roomId,
            LocalDate checkIn,
            LocalDate checkOut,
            double totalPrice,
            String status) {

        this.id = id;
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    // ===========================
    // Getter & Setter
    // ===========================

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // ===========================
    // Tambahan untuk JOIN
    // ===========================

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

}