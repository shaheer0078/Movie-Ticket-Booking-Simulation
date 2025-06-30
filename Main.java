import java.io.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner In = new Scanner(System.in);

        String[] movieNames = {"Super Mario", "Sinners ", "Thunders", "Substance"};
        String[] movieTimes = {"4:00 PM", "6:15 PM", "8:30 PM", "9:45 PM"};
        int[] price = {800, 1200, 1200, 1500};

        int[][][] seats = new int[4][5][8];
        int choice;
        int movieChoice = -1;
        String bookedMovie = "";
        String bookedTime = "";
        int ticketPrice = 0;
        int bookedRow = -1;
        int bookedCol = -1;
        String adminUsername = "admin";
        String adminPassword = "7890";
        do {
            System.out.println("\n=== Movie Ticket Booking System 🍿=== ");
            System.out.println("1. View Movies 🎥");
            System.out.println("2. View Seats 💺");
            System.out.println("3. Book a Ticket 🎫");
            System.out.println("4. Admin 🔧");
            System.out.println("5. Exit 👋");
            System.out.print("Enter your choice: ");
            choice = In.nextInt();

            if (choice == 1) {
                System.out.println("\nAvailable Movies: ");
                System.out.println("Index\tMovie Name\t\tTime\t\tPrice");
                for (int i = 0; i < movieNames.length; i++) {
                    System.out.println((i + 1) + ".\t\t" + movieNames[i] + "\t\t" + movieTimes[i] + "\t\t" + price[i] + " PKR");
                }
                System.out.print("Select a movie (1-4): ");
                movieChoice = In.nextInt();
                if (movieChoice >= 1 && movieChoice <= 4) {
                    System.out.println("✅ You selected: " + movieNames[movieChoice - 1] + " at " + movieTimes[movieChoice - 1]);
                } else {
                    System.out.println("❌ Invalid movie selection.");
                    movieChoice = -1;
                }
            }
            else if (choice == 2) {
                if (movieChoice == -1) {
                    System.out.println("❗Please select a movie first from option 1.");
                } else {
                    System.out.println("\nSeat Layout (Available = 0, Booked = 1):");
                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 8; j++) {
                            System.out.print(seats[movieChoice - 1][i][j] + " ");
                        }
                        System.out.println();
                    }
                }
            }
            else if (choice == 3) {
                if (movieChoice == -1) {
                    System.out.println("❗Please select a movie first (from option 1).");
                    continue;
                }
                System.out.print("How many users want to book tickets: ");
                int tUsers = In.nextInt();
                for (int i = 1; i<=tUsers; i++){
                    System.out.println("=== Booking for User "+i+" ===");
                    System.out.print("Enter your name: ");
                    String uname = In.next();
                    System.out.print("Enter Row (0–4): ");
                    int row = In.nextInt();
                    System.out.print("Enter Column (0–7): ");
                    int column = In.nextInt();
                    if (row >= 0 && row < 5 && column >= 0 && column < 8) {
                        if (seats[movieChoice-1][row][column] == 0) {
                            seats[movieChoice-1][row][column] = 1;
                            bookedMovie = movieNames[movieChoice - 1];
                            bookedTime = movieTimes[movieChoice - 1];
                            ticketPrice = price[movieChoice - 1];
                            bookedRow = row;
                            bookedCol = column;

                            System.out.println("\n✅ Booking Successful!");
                            System.out.println("👤 Name: " + uname);
                            System.out.println("🎬 Movie: " + bookedMovie);
                            System.out.println("📍 Seat: Row " + bookedRow + ", Column " + bookedCol);
                            System.out.println("⏱️ Time: " + bookedTime);
                            System.out.println("💵 Price: " + ticketPrice + " PKR");
                            Receipt(uname, bookedMovie, bookedTime, bookedRow, bookedCol, ticketPrice);
                        } else {
                            System.out.println("❌ Seat already booked. Please choose another.");
                        }
                    } else {
                        System.out.println("❌ Invalid seat number.");
                    }
                }
            }
            else if (choice==4){
                System.out.print("Enter User Name: ");
                String admin = In.next();
                System.out.print("Enter Password: ");
                String pass = In.next();
                if (admin.equals(adminUsername)&& pass.equals(adminPassword)){
                    System.out.println("\n✅ Access granted");
                    viewBookings();
                }
                else {
                    System.out.println("❌ Incorrect credentials. Access denied.");
                }
            }
            else if (choice == 5) {
                System.out.println("Thank you for using the system. Goodbye!");
            }
            else {
                System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }
    public static void Receipt(String name, String movie, String time, int row, int column, int price) {
        File myFile = new File("Bookings.txt");
        try {
            myFile.createNewFile();
            FileWriter writer = new FileWriter("Bookings.txt", true);
            writer.write("Name: " + name + ", Movie: " + movie + ", Time: " + time + ", Seat: " + row + "-" + column + ", Price: " + price + "PKR\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while saving the receipt.");
            e.printStackTrace();
        }
    }
    public static void viewBookings(){
        try{
            File file = new File("Bookings.txt");
            Scanner reader = new Scanner(file);
            System.out.println("====== All Bookings ======");
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading bookings.");
        }
    }
}