package hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Core service that exposes operations to manage users, rooms and bookings.
 *
 * <p>The service maintains lists of rooms, users and bookings. All operations
 * mutate these lists directly – there are no external repositories or
 * persistence layers. The ordering of the lists reflects creation order and
 * is used when printing data.</p>
 */
public class Service {
    private final List<Room> rooms;
    private final List<User> users;
    private final List<Booking> bookings;

    public Service() {
        this.rooms = new ArrayList<>();
        this.users = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    /**
     * Creates a new room or updates an existing one. Updating a room’s type or
     * price does not affect any bookings that were already created because
     * booking objects capture a snapshot of the room’s definition at booking
     * time.
     *
     * @param roomNumber      the unique identifier of the room
     * @param roomType        the category of the room
     * @param roomPricePerNight the nightly rate of the room
     */
    public void setRoom(int roomNumber, RoomType roomType, int roomPricePerNight) {
        Room existing = findRoom(roomNumber);
        if (existing == null) {
            Room room = new Room(roomNumber, roomType, roomPricePerNight);
            rooms.add(room);
            System.out.println("Created room: " + roomNumber);
        } else {
            // update existing room definition
            existing.setType(roomType);
            existing.setPricePerNight(roomPricePerNight);
            System.out.println("Updated room: " + roomNumber);
        }
    }

    /**
     * Creates a new user or updates the balance of an existing one.
     *
     * @param userId  the identifier of the user
     * @param balance the starting balance or updated balance
     */
    public void setUser(int userId, int balance) {
        User existing = findUser(userId);
        if (existing == null) {
            User user = new User(userId, balance);
            users.add(user);
            System.out.println("Created user: " + userId);
        } else {
            existing.setBalance(balance);
            System.out.println("Updated user: " + userId);
        }
    }

    /**
     * Attempts to reserve a room for a user over a date range. If any of the
     * preconditions fail (unknown user/room, invalid dates, insufficient
     * balance or overlapping booking), an informative message is printed and
     * no changes are made.
     *
     * @param userId    the identifier of the booking user
     * @param roomNumber the identifier of the room to book
     * @param checkIn   the start date, inclusive
     * @param checkOut  the end date, exclusive
     */
    public void bookRoom(int userId, int roomNumber, LocalDate checkIn, LocalDate checkOut) {
        // Validate user and room
        User user = findUser(userId);
        if (user == null) {
            System.out.println("Booking failed: user " + userId + " does not exist.");
            return;
        }
        Room room = findRoom(roomNumber);
        if (room == null) {
            System.out.println("Booking failed: room " + roomNumber + " does not exist.");
            return;
        }
        // Validate dates
        if (checkIn == null || checkOut == null) {
            System.out.println("Booking failed: dates cannot be null.");
            return;
        }
        if (!checkIn.isBefore(checkOut)) {
            System.out.println("Booking failed: check‑in date must be before check‑out date.");
            return;
        }
        int nights = (int) (java.time.temporal.ChronoUnit.DAYS.between(checkIn, checkOut));
        if (nights <= 0) {
            System.out.println("Booking failed: stay must be at least one night.");
            return;
        }
        int totalCost = nights * room.getPricePerNight();
        if (user.getBalance() < totalCost) {
            System.out.println("Booking failed: user " + userId + " does not have enough balance. Required: " + totalCost + ", available: " + user.getBalance());
            return;
        }
        // Check room availability
        for (Booking existingBooking : room.getBookings()) {
            LocalDate existingStart = existingBooking.getCheckIn();
            LocalDate existingEnd = existingBooking.getCheckOut();
            // If periods overlap: (checkIn < existingEnd) && (checkOut > existingStart)
            if (!checkOut.isBefore(existingStart) && !checkIn.isAfter(existingEnd.minusDays(1))) {
                // Overlap occurs
                System.out.println("Booking failed: room " + roomNumber + " is not available from " + checkIn + " to " + checkOut + ".");
                return;
            }
        }
        // Create booking and update state
        int userBalanceAtBooking = user.getBalance();
        Booking booking = new Booking(userId, roomNumber, room.getType(), room.getPricePerNight(), checkIn, checkOut, userBalanceAtBooking);
        // Deduct funds
        user.setBalance(user.getBalance() - totalCost);
        // Register booking globally and in associated objects
        bookings.add(booking);
        room.addBooking(booking);
        user.addBooking(booking);
        System.out.println("Booking succeeded: user " + userId + " booked room " + roomNumber + " from " + checkIn + " to " + checkOut + ".");
    }

    /**
     * Prints all rooms and bookings in reverse order of creation. Each room
     * displays its current state while each booking displays the state at the
     * time it was created.
     */
    public void printAll() {
        System.out.println("=== Rooms (latest to oldest) ===");
        List<Room> roomsCopy = new ArrayList<>(rooms);
        Collections.reverse(roomsCopy);
        for (Room room : roomsCopy) {
            System.out.println("Room " + room.getNumber() + ": type=" + room.getType() + ", price/night=" + room.getPricePerNight());
        }
        System.out.println();
        System.out.println("=== Bookings (latest to oldest) ===");
        List<Booking> bookingsCopy = new ArrayList<>(bookings);
        Collections.reverse(bookingsCopy);
        for (Booking booking : bookingsCopy) {
            System.out.println("Booking => user=" + booking.getUserId() + ", room=" + booking.getRoomNumber() + ", type at booking=" + booking.getRoomTypeAtBooking() + ", price at booking=" + booking.getPriceAtBooking() + ", checkIn=" + booking.getCheckIn() + ", checkOut=" + booking.getCheckOut() + ", nights=" + booking.getNights() + ", total cost=" + booking.getTotalCost() + ", user balance at booking=" + booking.getUserBalanceAtBooking());
        }
    }

    /**
     * Prints all users in reverse order of creation. Each user displays their
     * current balance.
     */
    public void printAllUsers() {
        System.out.println("=== Users (latest to oldest) ===");
        List<User> usersCopy = new ArrayList<>(users);
        Collections.reverse(usersCopy);
        for (User user : usersCopy) {
            System.out.println("User " + user.getId() + ": balance=" + user.getBalance());
        }
    }

    private Room findRoom(int roomNumber) {
        for (Room room : rooms) {
            if (room.getNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    private User findUser(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }
}