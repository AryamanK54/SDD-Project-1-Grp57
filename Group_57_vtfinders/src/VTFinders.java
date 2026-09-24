import java.util.Scanner;

/**
 * Runner class for VT Finders.
 *
 * Handles the main loop for user interaction, allowing users to report and
 * browse lost items. VTFinders owns the single Scanner for the whole program
 * (used here only to read the top-level menu choice) and hands that same
 * Scanner to Client, which does all the detailed prompting/validation for
 * item reports and category browsing.
 *
 * @author Phani Kathuroju
 * @version 9.23.26
 */
public class VTFinders {
    private static LostItemDatabase database;
    private static Client client;
    private static Administrator admin;

    // ----------------------------------------------------------
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        database = new LostItemDatabase();
        client = new Client(database, scanner);
        boolean running = true;

        while (running) {
            System.out.println("VTFinders");
            System.out.println("1: Report Item");
            System.out.println("2: Browse Items");
            System.out.println("3: Administration");
            System.out.println("4: Exit");

            String choice = scanner.nextLine();

            if (choice.equals("1")) {
                client.reportItem();
            } else if (choice.equals("2")) {
                client.browseItems();
            } else if (choice.equals("3")) {
                System.out.print("Enter admin username: ");
                String username = scanner.nextLine();

                System.out.print("Enter admin password: ");
                String password = scanner.nextLine();

                if (admin.authentication(username, password))
                {
                    boolean adminRunning = true;
                    while (adminRunning)
                    {
                        System.out.println("Administration");
                        System.out.println("1: View All Items");
                        System.out.println("2: Remove Item");
                        System.out.println("3: Exit Administration");

                        String adminChoice = scanner.nextLine();

                        if (adminChoice.equals("1"))
                        {
                            System.out.println(admin.viewAllItems());
                        }
                        else if (adminChoice.equals("2"))
                        {
                            System.out.print("Enter item ID:");
                            String itemId = scanner.nextLine();
                            
                            System.out.print("Enter category:");
                            String cat = scanner.nextLine();

                            admin.removeItem(itemId, cat);
                        }
                        else if(adminChoice.equals("3"))
                        {
                            System.out.println("Leaving administrative mode");
                            adminRunning = false;
                        }
                    }
                }
                else
                {
                    System.out.println("invalid username or password");
                }
            } else if (choice.equals("4")) {
                running = false;
            }
        }

        scanner.close();
    }

}