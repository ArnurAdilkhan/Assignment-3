import java.sql.*;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String connectionURl = "jdbc:postgresql://localhost:5432/onlineticket";
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(connectionURl, "postgres", "1234");
        System.out.println();
        System.out.println("Welcome to online ticket buying servise!");
        System.out.println("1. Register" + "\n" + "2. Login" + "\n" + "3. Exit program");
        System.out.println("Select option: ");
        int ch = scanner.nextInt();
        if (ch == 1) {
            register(con);
            menu(con);
        }
        else if (ch == 2) {
            login(con);
            menu(con);
        }
        else if (ch == 3) {
            System.out.println("Thank you for visiting, bye!");
        }
    }

    public static void register(Connection con) throws Exception {
        PreparedStatement pst = con.prepareStatement("insert into users (username, password) values (?,?)");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String username = scanner.next();
        System.out.println("Enter your password: ");
        String password = scanner.next();

        pst.setString(1 , username);
        pst.setString(2 , password);
        pst.executeUpdate();
        System.out.println("Success!");
        System.out.println();
    }
    public static void login(Connection con) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from users");
        System.out.println();
        System.out.println("Welcome back!");
        System.out.println("Enter your username: ");
        String username = scanner.next();
        System.out.println("Enter your password: ");
        String password = scanner.next();
        boolean bl = false;
        while (rs.next()) {
            if (rs.getString("username").equals(username) && rs.getString("password").equals(password)){
                System.out.println("Success! Welcome " + username);
                System.out.println();
                bl = true;
                break;
            }
        }
        if (!bl) {
            System.out.println("Something went wrong. Try again.");
            login(con);
        }
        rs.close();
        st.close();
    }
    public static void menu(Connection con) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Main menu");
        System.out.println("__________________________");
        System.out.println("1. See upcoming events");
        System.out.println("2. Add new event");
        System.out.println("3. Update current event");
        System.out.println("4. Delete event");
        System.out.println("5. Exit");
        System.out.println("Select option: ");
        int ch = scanner.nextInt();
        if (ch == 1) {
            showData(con);
        }
        else if (ch == 2) {
            addEvent(con);
        }
        else if (ch == 3) {
            updEvent(con);
        }
        else if (ch == 4) {
            delEvent(con);
        }
        else if (ch == 5) {
            System.out.println("Thanks for visiting, bye!");
        }
    }
    public static void showData(Connection con) throws Exception{
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery("select * from events");
        System.out.println("Upcoming events: ");
        while (rs.next()) {
            System.out.println("id:" + rs.getInt(1) + "\n" + " event name: " + rs.getString(2) + "\n" + " event date: " + rs.getString(3) + "\n" + " price: " + rs.getString(4) + "\n" + " aviable amount: " + rs.getString(5));
            System.out.println();
        }
    }

    public static void addEvent(Connection con) throws Exception{
        Scanner scanner = new Scanner(System.in);
        PreparedStatement pst = con.prepareStatement("insert into events (event_name, event_date, price, amount) values (?,?,?,?)");
        System.out.println("Enter name of event: ");
        String name = scanner.nextLine();
        pst.setString(1, name);
        System.out.println("Enter date of event: ");
        String date = scanner.nextLine();
        pst.setString(2, date);
        System.out.println("Enter price of event: ");
        String price = scanner.nextLine();
        pst.setString(3, price);
        System.out.println("Enter how many events will be: ");
        String amount = scanner.nextLine();
        pst.setString(4, amount);
        pst.executeUpdate();
        System.out.println("Success!");
        System.out.println();
    }

    public static void delEvent(Connection con) throws Exception{
        Scanner scanner = new Scanner(System.in);
        PreparedStatement pst = con.prepareStatement("delete from events where id = ?;");
        System.out.println("Enter id of row, that you want to delete: ");
        int i = scanner.nextInt();
        pst.setInt(1, i);
        pst.executeUpdate();
        System.out.println("Success!");
        System.out.println();
    }
    public static void updEvent(Connection con) throws Exception {
        PreparedStatement pst = con.prepareStatement("update events set event_name = ?, event_date = ?, price = ?, amount = ? where id = ?;");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id of row, that you want to update: ");
        int i = scanner.nextInt();
        String empty = scanner.nextLine();
        pst.setInt(5, i);
        System.out.println("Enter new name of event: ");
        String name = scanner.nextLine();
        pst.setString(1, name);
        System.out.println("Enter new date of event: ");
        String date = scanner.nextLine();
        pst.setString(2, date);
        System.out.println("Enter new price of event: ");
        String pr = scanner.nextLine();
        pst.setString(3, pr);
        System.out.println("Enter how many events will be aviable: ");
        String at = scanner.nextLine();
        pst.setString(4, at);
        pst.executeUpdate();
        System.out.println("Success!");
        System.out.println();
    }
}