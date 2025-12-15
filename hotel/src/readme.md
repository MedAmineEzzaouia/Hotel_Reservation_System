# Hotel Reservation System

A simple hotel reservation system in Java that manages rooms, users and bookings.
---

## Structure

- **RoomType** - Defines three room categories (standard, junior, suite)
- **Room** - Stores room number, type, price and bookings
- **User** - Stores identifier, balance and bookings
- **Booking** - Captures reservation snapshot (room details, dates, cost, user balance)
- **Service** - Creates/updates rooms and users, performs bookings with validation, prints summaries
- **Main** - Demonstrates the Skypay test case and prints final state

---

## Architecture Diagram

![Hotel Reservation System - Class Diagram](docs/class-diagram.png)


## Run

Compile and run from repository root:

```bash
javac -d out hotel/src/*.java
java -cp out hotel.Main
```
---

## Design Questions

**Suppose we put all the functions inside the same service. Is this the
recommended approach ? Please explain.?**

-> Keeping everything in one big service makes it harder to understand or modify the program. It mixes together very different tasks like managing rooms, handling customer balances, tracking reservations. If you ever add features like new payment methods or special room maintenance, that single class becomes an unwieldy knot. By giving each concept its own class and letting a separate service coordinate them, each part stays focused on one job and is easier to test and change.

**2/- In this design, we chose to have a function setRoom(..) that should
not impact the previous bookings. What is another way ? What is your
recommendation ? Please explain and justify.**

-> When a room’s type or price changes, you don’t want past reservations to suddenly show different information. In this solution, every booking records the room details and user balance at the time it was made, so later updates can’t rewrite history. Another way would be to make each change create a new, separate version of the room and keep old bookings tied to the old version. That approach protects the past as well but is more involved. For this exercise, storing a snapshot of the room’s state in the booking is simple and effective.

---