package hotel;

import java.time.LocalDate;

/**
 * Represents a reservation made by a user for a given room over a date range.
 *
 * <p>The booking captures a snapshot of the room’s type and price at the
 * moment it was created to ensure later changes to room definitions do not
 * affect historical bookings. Similarly the user’s balance at the time of
 * booking is stored for reporting purposes.</p>
 */
public class Booking {
    private final int userId;
    private final int roomNumber;
    private final RoomType roomTypeAtBooking;
    private final int priceAtBooking;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final int nights;
    private final int totalCost;
    private final int userBalanceAtBooking;

    public Booking(int userId, int roomNumber, RoomType roomTypeAtBooking,
                   int priceAtBooking, LocalDate checkIn, LocalDate checkOut,
                   int userBalanceAtBooking) {
        this.userId = userId;
        this.roomNumber = roomNumber;
        this.roomTypeAtBooking = roomTypeAtBooking;
        this.priceAtBooking = priceAtBooking;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.nights = (int) (java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut));
        this.totalCost = this.nights * this.priceAtBooking;
        this.userBalanceAtBooking = userBalanceAtBooking;
    }

    public int getUserId() {
        return userId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomTypeAtBooking() {
        return roomTypeAtBooking;
    }

    public int getPriceAtBooking() {
        return priceAtBooking;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public int getNights() {
        return nights;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public int getUserBalanceAtBooking() {
        return userBalanceAtBooking;
    }
}