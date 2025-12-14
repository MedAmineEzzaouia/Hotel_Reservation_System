package hotel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Entry point demonstrating the Service API. Executes the test case provided
 * in the technical assessment and prints final state summaries.
 */
public class Main {
    public static void main(String[] args) {
        Service service = new Service();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Create rooms
        service.setRoom(1, RoomType.STANDARD, 1000);
        service.setRoom(2, RoomType.JUNIOR, 2000);
        service.setRoom(3, RoomType.SUITE, 3000);

        // Create users
        service.setUser(1, 5000);
        service.setUser(2, 10000);

        // User 1 tries booking Room 2 from 30/06/2026 to 07/07/2026 (7 nights)
        service.bookRoom(1, 2,
                LocalDate.parse("30/06/2026", fmt),
                LocalDate.parse("07/07/2026", fmt));

        // User 1 tries booking Room 2 from 07/07/2026 to 30/06/2026 (invalid range)
        service.bookRoom(1, 2,
                LocalDate.parse("07/07/2026", fmt),
                LocalDate.parse("30/06/2026", fmt));

        // User 1 tries booking Room 1 from 07/07/2026 to 08/07/2026 (1 night)
        service.bookRoom(1, 1,
                LocalDate.parse("07/07/2026", fmt),
                LocalDate.parse("08/07/2026", fmt));

        // User 2 tries booking Room 1 from 07/07/2026 to 09/07/2026 (2 nights)
        service.bookRoom(2, 1,
                LocalDate.parse("07/07/2026", fmt),
                LocalDate.parse("09/07/2026", fmt));

        // User 2 tries booking Room 3 from 07/07/2026 to 08/07/2026 (1 night)
        service.bookRoom(2, 3,
                LocalDate.parse("07/07/2026", fmt),
                LocalDate.parse("08/07/2026", fmt));

        // setRoom(1, suite, 10000)
        service.setRoom(1, RoomType.SUITE, 10000);

        // Print final state
        System.out.println();
        service.printAll();
        System.out.println();
        service.printAllUsers();
    }
}