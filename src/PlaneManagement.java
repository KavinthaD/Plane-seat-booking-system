import java.util.Scanner;

public class PlaneManagement {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        //create all necessary arrays
        int[][] rows = new int[4][14];
        String[] soldTickets = new String[52];
        Person[] persons = new Person[52];
        Ticket[] tickets = new Ticket[52];
        
        System.out.println("\nSsWelcome to the Plane Management application");

        boolean quit = false;

        //repeat menu until user enter 0
        while (!quit) {
            //print out the menu
            System.out.println(); //print a white space
            System.out.print(""" 
                    ****************************************************
                    *                   MENU OPTIONS                   *
                    ****************************************************
                        
                        1) Buy a seat
                        2) Cancel a seat
                        3) Find first available seat
                        4) Show seating plan
                        5) Print tickets information and total sales
                        6) Search ticket
                        0) Quit
                    ****************************************************
                       
                    """);
            System.out.print("Please select an option: ");
            try {
                String user_input = input.nextLine();
                int menuOption = Integer.parseInt(user_input); //pass the user input to an integer

                //calling private method according to the menu option entered
                switch (menuOption) {
                    case 1:
                        buy_seat(rows, soldTickets, persons, tickets);
                        break;
                    case 2:
                        cancel_seat(rows, soldTickets);
                        break;
                    case 3:
                        find_first_available(rows);
                        break;
                    case 4:
                        show_seating_plan(rows);
                        break;
                    case 5:
                        print_tickets_info(soldTickets, tickets);
                        break;
                    case 6:
                        search_ticket(rows, soldTickets, tickets);
                        break;
                    case 0:
                        quit = true;
                        System.out.println("Programme ended.");
                        break;
                    default:
                        System.out.println("Invalid input"); //print this if user input a number other than menu options.
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("\nPlease enter a valid number from menu."); //print this if user input a character other than menu options.
            }
        }
    }

    //method 1 (to book a seat)
    private static void buy_seat(int[][] rows, String[] soldTickets, Person[] persons, Ticket[] tickets) {
        char rowLetter = getValidRowLetterInput();
        int rowNumber = convertRowLetterToNumber(rowLetter);
        int seatNumber = getValidSeatNumberInput(rowLetter);

        CheckAndBookSeat(rows, rowNumber, seatNumber);
        CreateTicket(soldTickets, rowLetter, seatNumber, persons, tickets);
    }

    //method 2 (to cancel a seat)
    private static void cancel_seat(int[][] rows, String[] soldTickets) {
        char rowLetter = getValidRowLetterInput();
        int rowNumber = convertRowLetterToNumber(rowLetter);
        int seatNumber = getValidSeatNumberInput(rowLetter);

        CheckAndCancelSeat(rows, rowNumber, seatNumber);
        CancelTicket(soldTickets, rowLetter, seatNumber);
    }

    //method 3 (to find first available seat from the seating plan)
    private static void find_first_available(int[][] rows) {

        boolean foundSeat = false;

        //repeat this code block until it found a seat or all seats has been booked
        outerLoop: //used label break here (reference below)
        for (int rowNumber = 1; rowNumber <= 4; rowNumber++) {
            for (int seatNumber = 1; seatNumber <= 14; seatNumber++) {
                if (rows[rowNumber][seatNumber] == 0) {

                    //convert row number to a row letter
                    char rowLetter;
                    switch (rowNumber){
                        case 1:
                            rowLetter = 'A';
                            break;
                        case 2:
                            rowLetter = 'B';
                            break;
                        case 3:
                            rowLetter = 'C';
                            break;
                        default:
                            rowLetter = 'D';
                            break;
                    }
                    System.out.println(String.valueOf(rowLetter) + seatNumber+" Seat is available!"); //use "" to convert print data type to string
                    foundSeat = true;
                    break outerLoop; //label break
                }
            }
        }
        if (!foundSeat) {
            System.out.println("All seats already booked."); //print this if none of free seat found
        }
    }

    //method 4 (to show seat planning)
    private static void show_seating_plan(int[][] rows) {
        int maxSeats;
        System.out.println("\nSeating Plan (O = not booked , X = booked)\n"); //white space line
        for (int row = 1; row <= 4; row++) {
            //make row b,c max seats to 12
            if (row == 2 || row == 3)
                maxSeats = 12;
            else
                //make row a,d max seats to 14
                maxSeats = 14;
            for (int seat = 1; seat <= maxSeats; seat++) {
                if (rows[row-1][seat-1] == 0)
                    System.out.print("O ");
                else
                    System.out.print("X ");
            }
            System.out.println();
        }
    }

    //method 5 (print out all the sold tickets information)
    private static void print_tickets_info(String[] soldTickets, Ticket[] tickets) {
        int ticketNum = 1;
        double totalPrice = 0;
        for (int i = 0; i < 52; i++) {
            if (soldTickets[i] != null) {
                System.out.println("\nTicket " + ticketNum);
                tickets[i].printTicketInfo();
                totalPrice += tickets[i].getPrice();

            }
            ticketNum += 1;
        }
        System.out.println("\nTotal ticket price is " + totalPrice);
    }

    //method 6 (search the ticket and print out the information)
    private static void search_ticket(int[][] rows, String[] soldTickets, Ticket[] tickets) {
        char rowLetter = getValidRowLetterInput();
        int seatNumber = getValidSeatNumberInput(rowLetter);
        int rowNumber = convertRowLetterToNumber(rowLetter);
        if (rows[rowNumber - 1][seatNumber - 1] == 1) {
            String seatCheck = "" + rowLetter + seatNumber;
            for (int i = 0; i < 52; i++) {
                if (soldTickets[i] != null) {
                    if (seatCheck.equals(soldTickets[i])) {
                        System.out.println("\nThis seat has been booked\n");
                        tickets[i].printTicketInfo();
                    }
                }

            }
        } else {
            System.out.println("\nThis seat is available.");
        }
    }

    //method to create ticket (for buy_seat method)
    private static void CreateTicket(String[] soldTickets, char rowLetter, int seatNumber, Person[] persons, Ticket[] tickets) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter following details to create a ticket.");
        boolean validInput = false;
        String name = "";
        while(!validInput){
        System.out.print("Name :");

        //------------------add reference form somewhere--------------------
        name = input.next();
        validInput = name.matches("^[a-zA-Z]+$"); //check if user input something other than a letter.
        if (!validInput)
            System.out.println("Enter a valid name");
        }
        validInput = false;
        String surname = "";
        while(!validInput) {
            System.out.print("Surname :");
            surname = input.next();
            validInput = surname.matches("^[a-zA-Z]+$"); //check if user input something other than a letter.
            if (!validInput)
                System.out.println("Enter a valid name");
        }
        System.out.print("email :");
        String email = input.next();

        int price;
        if (seatNumber < 6) {
            price = 200;
        } else if (seatNumber < 10) {
            price = 150;
        } else {
            price = 180;
        }
        boolean allTicketsBooked = true;

        for (int i = 0; i <= 52; i++) {
            if (soldTickets[i] == null) {
                soldTickets[i] = String.valueOf(rowLetter) + seatNumber;

                //************ might have to comment here ***************
                persons[i] = new Person(name, surname, email);
                tickets[i] = new Ticket(rowLetter, seatNumber, price, persons[i]);
                tickets[i].save(); // save the ticket to a text file.

                allTicketsBooked = false;
                System.out.println("Ticket created.");
                break;
            }
        }
        if (allTicketsBooked) {
            System.out.println("All tickets booked");
        }
    }

    //method to cancel ticket (for cancel_seat method)
    private static void CancelTicket(String[] soldTickets, char rowLetter, int seatNumber) {
        String seatCheck = ""+ rowLetter+ seatNumber;
        for (int i = 0; i < 52; i++) {
            if (soldTickets[i] != null) {
                if (seatCheck.equals(soldTickets[i])) {
                    soldTickets[i] = null;

                    System.out.println("Ticket cancelled.");
                    break;
                }
            }
        }
    }

    //method to book the seat only if it's previously not booked (for buy_seat method)
    private static void CheckAndBookSeat(int[][] rows, int rowNumber, int seatNumber) {
        if (rows[rowNumber - 1][seatNumber - 1] == 0) {
            rows[rowNumber - 1][seatNumber - 1] = 1;
            System.out.println("Booking Success!");
        } else {
            System.out.println("already booked");
        }
    }

    //method to cancel the seat only if it's previously not booked (for cancel_seat method)
    private static void CheckAndCancelSeat(int[][] rows, int rowNumber, int seatNumber) {
        if (rows[rowNumber - 1][seatNumber - 1] == 1) {
            rows[rowNumber - 1][seatNumber - 1] = 0;
            System.out.println("seat is cancelled");
        } else {
            System.out.println("seat is never booked before");
        }
    }

    //method to convert row letter got form user to row number to access array elements easily.
    private static int convertRowLetterToNumber(char rowLetter) {
        switch (rowLetter) {
            case 'A':
                rowLetter = 1;
                break;
            case 'B':
                rowLetter = 2;
                break;
            case 'C':
                rowLetter = 3;
                break;
            case 'D':
                rowLetter = 4;
                break;
        }
        return rowLetter;
    }

    //method to return only a valid row letter
    private static char getValidRowLetterInput() {
        Scanner input = new Scanner(System.in);
        boolean validRowLetter = false;
        char rowLetter = 0;
        while (!validRowLetter) {
            System.out.print("Enter row letter (A,B,C,D) :");
            rowLetter = input.next().toUpperCase().charAt(0);
            if (rowLetter == 'A' || rowLetter == 'B' || rowLetter == 'C' || rowLetter == 'D') {
                validRowLetter = true;
            } else {
                System.out.println("Please enter a valid Row Letter.");
            }
        }
        return rowLetter;
    }

    //method to return only a valid seat number
    private static int getValidSeatNumberInput(char rowLetter) {

        Scanner input = new Scanner(System.in);
        boolean validSeatNumber = false;
        int seatNumber = 0;
        while (!validSeatNumber) {
            System.out.print("Enter the seat number :");
            try {
                String userInput = input.nextLine();
                seatNumber = Integer.parseInt(userInput);
                int maxSeats;
                if (rowLetter == 'A' || rowLetter == 'D')
                    maxSeats = 14;
                else
                    maxSeats = 12;
                if (seatNumber >= 1 && seatNumber <= maxSeats) {
                    validSeatNumber = true;
                } else {
                    System.out.println("Please enter a valid seat number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid seat number.");
            }
        }
        return seatNumber;
    }
}

/*
References

label break method - https://www.baeldung.com/java-continue-and-break#:~:text=A%20labeled%20break%20terminates%20the%20outer%20loop.&text=When%20rowNum%20equals%201%20and,end%20of%20outer%20for%20loop.

 */