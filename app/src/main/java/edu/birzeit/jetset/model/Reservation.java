package edu.birzeit.jetset.model;

public class Reservation {
    private int reservationId;
    private String flightNumber;
    private String passengerEmail;
    private String seatNumber;
    private String status;

    // Constructor
    public Reservation(int reservationId, String flightNumber, String passengerEmail, String seatNumber, String status) {
        this.reservationId = reservationId;
        this.flightNumber = flightNumber;
        this.passengerEmail = passengerEmail;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    // Getter and Setter methods

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
