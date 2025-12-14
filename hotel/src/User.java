package hotel;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer of the hotel. Each user has a unique identifier
 * and a balance representing their available funds. Bookings made by the
 * user are recorded here to allow reporting and auditing.
 */
public class User {
    private final int id;
    private int balance;
    private final List<Booking> bookings;

    public User(int id, int balance) {
        this.id = id;
        this.balance = balance;
        this.bookings = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Adds a booking made by this user to the list of their bookings.
     *
     * @param booking the booking to add
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}