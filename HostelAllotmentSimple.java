import java.util.Scanner;


public class HostelAllotmentSimple{

    static final int TOTAL_ROOMS = 10;
    static final int ROOM_CAPACITY = 2;
    static final int TOTAL_STUDENTS = 21;

    // One entry per room
    static int[] roomCount = new int[TOTAL_ROOMS];        // how many students inside (0, 1 or 2)
    static char[] roomGender = new char[TOTAL_ROOMS];     
    static String[] roomCourse = new String[TOTAL_ROOMS]; 

    // One entry per bed: [room][bed]
    static String[][] studentName = new String[TOTAL_ROOMS][ROOM_CAPACITY];
 

    
    static int allotRoom(String name, char gender, String course) {
        int room = -1;

       
        for (int i = 0; i < TOTAL_ROOMS; i++) {
            if (roomCount[i] == 1 && roomGender[i] == gender && roomCourse[i].equals(course)) {
                room = i;
                break;
            }
        }

        if (room == -1) {
            for (int i = 0; i < TOTAL_ROOMS; i++) {
                if (roomCount[i] == 0) {
                    room = i;
                    roomGender[i] = gender;
                    roomCourse[i] = course;
                    break;
                }
            }
        }

        
        if (room == -1) {
            return -1;
        }

        studentName[room][roomCount[room]] = name;
        roomCount[room]++;
        return room;
    }

    static boolean allRoomsFull() {
        for (int i = 0; i < TOTAL_ROOMS; i++) {
            if (roomCount[i] < ROOM_CAPACITY) {
                return false;
            }
        }
        return true;
    }

    static char readGender(Scanner sc) {
        while (true) {
            System.out.print("Gender (M/F): ");
            String g = sc.nextLine().trim().toUpperCase();
            if (g.equals("M") || g.equals("F")) {
                return g.charAt(0);
            }
            System.out.println("Invalid gender. Enter M or F.");
        }
    }


    static String readCourse(Scanner sc) {
        while (true) {
            System.out.print("Course (UG/PG): ");
            String c = sc.nextLine().trim().toUpperCase();
            if (c.equals("UG") || c.equals("PG")) {
                return c;
            }
            System.out.println("Invalid course. Enter UG or PG.");
        }
    }

    static void printAllotment() {
        System.out.println("\n========== FINAL ROOM ALLOTMENT ==========");
        for (int i = 0; i < TOTAL_ROOMS; i++) {
            System.out.print("Room " + (i + 1) + ": ");
            if (roomCount[i] == 0) {
                System.out.println("Empty");
                continue;
            }
            for (int j = 0; j < roomCount[i]; j++) {
                System.out.print(studentName[i][j] + " (" + roomGender[i] + ", "
                        + ", " + roomCourse[i] + ")");
                if (j < roomCount[i] - 1) {
                    System.out.print("  &  ");
                }
            }
            if (roomCount[i] == 1) {
                System.out.print("  [1 bed vacant]");
            }
            System.out.println();
        }
        System.out.println("==========================================");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("---- Hostel Room Allotment ----");
        System.out.println(TOTAL_ROOMS + " rooms, " + ROOM_CAPACITY + " students per room.\n");

        for (int n = 1; n <= TOTAL_STUDENTS; n++) {
            System.out.println("--- Student " + n + " of " + TOTAL_STUDENTS + " ---");

            System.out.print("Name: ");
            String name = sc.nextLine().trim();
            char gender = readGender(sc);
            String course = readCourse(sc);

            int room = allotRoom(name, gender, course);

            if (room != -1) {
                System.out.println(">> " + name + " has been allotted Room " + (room + 1) + "\n");
            } else if (allRoomsFull()) {
                System.out.println(">> All rooms are occupied. Please check another hostel.\n");
            } else {
                System.out.println(">> No room available with a matching gender and course. "
                        + "Please check another hostel.\n");
            }
        }

        printAllotment();
        sc.close();
    }
}