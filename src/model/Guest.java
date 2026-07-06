package model;

public class Guest extends Person {

    private String idNumber;
    private String address;

    public Guest() {}

    public Guest(int id,
                 String name,
                 String email,
                 String phone,
                 String idNumber,
                 String address) {

        super(id, name, email, phone);
        this.idNumber = idNumber;
        this.address = address;
    }

    @Override
    public String getRole() {
        return "GUEST";
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}