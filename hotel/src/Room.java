package hotel;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hotel room with a unique number, a category and a nightly price.
 *
 * <p>Each room maintains a list of its bookings. The price and type can be
 * updated via the service API, however any existing bookings retain the
 * original information captured at the time of booking.</p>
 */
public class Room {
    private final int number;
    private RoomType type;
    private int pricePerNight;
    private final List<Booking> bookings;

    public Room(int number, RoomType type, int pricePerNight) {
        this.number = number;
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.bookings = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    /**
     * Adds a booking to this room’s internal list. Bookings are stored here
     * to facilitate availability checks.
     *
     * @param booking the booking to add
     */
    public void addBooking(Booking booking) {
        bookings.add(booking);
    }
}