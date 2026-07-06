package model;

import java.time.LocalDateTime;

public class Payment {

    private int id;
    private String guestName;
    private String roomNumber;
    private int reservationId;
    private double amount;
    private String method;
    private String status;
    private LocalDateTime paymentDate;
    

    public Payment() {}

    public Payment(int id,
                   int reservationId,
                   double amount,
                   String method,
                   String status,
                   LocalDateTime paymentDate) {

        this.id = id;
        this.reservationId = reservationId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    // ===== Encapsulation (inti OOP) =====
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
    
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
    

    // optional (biar enak dilihat di UI / debug)
    @Override
    public String toString() {
        return "Payment #" + id + " - " + amount + " (" + status + ")";
    }
}