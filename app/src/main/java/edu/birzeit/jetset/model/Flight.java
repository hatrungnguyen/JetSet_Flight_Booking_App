package edu.birzeit.jetset.model;

import java.util.Date;

public class Flight {
    private int flightId;
    private String flightNumber;
    private String departureCity;
    private String destinationCity;
    private Date departureDate;
    private Date departureTime;
    private Date arrivalDate;
    private Date arrivalTime;
    private String duration;
    private String aircraftModel;
    private int currentReservations;
    private int maxSeats;
    private int missedFlights;
    private Date bookingOpenDate;
    private double priceEconomy;
    private double priceBusiness;
    private double priceExtraBaggage;
    private String isRecurrent; // e.g., "None", "Daily", "Weekly"

    // Constructor
    public Flight(int flightId, String flightNumber, String departureCity, String destinationCity, Date departureDate, Date departureTime, Date arrivalDate,
                  Date arrivalTime, String duration, String aircraftModel, int currentReservations, int maxSeats, int missedFlights,
                  Date bookingOpenDate, double priceEconomy, double priceBusiness, double priceExtraBaggage, String isRecurrent) {

        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departureCity = departureCity;
        this.destinationCity = destinationCity;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.aircraftModel = aircraftModel;
        this.currentReservations = currentReservations;
        this.maxSeats = maxSeats;
        this.missedFlights = missedFlights;
        this.bookingOpenDate = bookingOpenDate;
        this.priceEconomy = priceEconomy;
        this.priceBusiness = priceBusiness;
        this.priceExtraBaggage = priceExtraBaggage;
        this.isRecurrent = isRecurrent;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public void setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
    }

    public int getCurrentReservations() {
        return currentReservations;
    }

    public void setCurrentReservations(int currentReservations) {
        this.currentReservations = currentReservations;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public int getMissedFlights() {
        return missedFlights;
    }

    public void setMissedFlights(int missedFlights) {
        this.missedFlights = missedFlights;
    }

    public Date getBookingOpenDate() {
        return bookingOpenDate;
    }

    public void setBookingOpenDate(Date bookingOpenDate) {
        this.bookingOpenDate = bookingOpenDate;
    }

    public double getPriceEconomy() {
        return priceEconomy;
    }

    public void setPriceEconomy(double priceEconomy) {
        this.priceEconomy = priceEconomy;
    }

    public double getPriceBusiness() {
        return priceBusiness;
    }

    public void setPriceBusiness(double priceBusiness) {
        this.priceBusiness = priceBusiness;
    }

    public double getPriceExtraBaggage() {
        return priceExtraBaggage;
    }

    public void setPriceExtraBaggage(double priceExtraBaggage) {
        this.priceExtraBaggage = priceExtraBaggage;
    }

    public String getIsRecurrent() {
        return isRecurrent;
    }

    public void setIsRecurrent(String isRecurrent) {
        this.isRecurrent = isRecurrent;
    }
}
