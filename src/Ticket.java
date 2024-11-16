import java.io.FileWriter;
import java.io.IOException;
public class Ticket {
    private char row;
    private int seat;
    private double price;
    private Person person;

    public Ticket(char row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public char getRow() {
        return row;
    }

    public int getSeat() {
        return seat;
    }

    public double getPrice() {
        return price;
    }

    public Person getPerson() {
        return person;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void printTicketInfo() {
        System.out.println("Ticket Information:");
        System.out.println("Row: " + row);
        System.out.println("Seat: " + seat);
        System.out.println("Price: $" + price);
        System.out.println("Person Information:");
        System.out.println("Name: " + person.getName());
        System.out.println("Surname: " + person.getSurname());
        System.out.println("Email: " + person.getEmail());
    }

    public void save() {
        try {
            String fileName = "" + row + seat + ".txt";
            FileWriter writer = new FileWriter(fileName);
            writer.write("Ticket Information:\n");
            writer.write("Row " + row + "\n");
            writer.write("Seat: " + seat + "\n");
            writer.write("Price: $" + price + "\n");
            writer.write("Person Information:" + "\n");
            writer.write("Name: " + person.getName() + "\n");
            writer.write("Surname: " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error while writing to the file.");
        }
    }
}
