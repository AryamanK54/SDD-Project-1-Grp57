import java.util.Scanner;

/**
 * Runner class for VT Finders.
 *
 * Handles the main loop for user interaction, allowing users to report and browse lost items.
 *
 * @author Phani Kathuroju
 * @version 9.23.26
 */
public class VTFinders
{
    private static LostItemDatabase database;
    private static Client client;
    
    public static void main(String[] args)
    {
        database = new LostItemDatabase();
        client = new Client(database);
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while(running){
            System.out.println("VTFinders");
            System.out.println("1: Report Item");
            System.out.println("2: Browse Items");
            System.out.println("3: Administration");
            System.out.println("4: Exit");

            String choice = scanner.nextLine();

            if (choice.equals("1"))
            {
                System.out.print("Enter item name:");
                String name = scanner.nextLine();

                System.out.print("Enter Description:");
                String description = scanner.nextLine();

                System.out.print("Enter Location:");
                String location = scanner.nextLine();

                System.out.print("Enter date found:");
                String date = scanner.nextLine();

                System.out.println("Choose a category:");
                System.out.println("A: School Supplies");
                System.out.println("B: Electronics");
                System.out.println("C: Clothing");
                System.out.println("D: Personal Items");
                System.out.println("E: Miscellaneous");

                String categoryChoice = scanner.nextLine();
                String category = "";

                if (categoryChoice.equals("A"))
                {
                    category = "School Supplies";
                }

                else if (categoryChoice.equals("B"))
                {
                    category = "Electronics";
                }

                else if (categoryChoice.equals("C"))
                {
                    category = "Clothing";
                }

                else if (categoryChoice.equals("D"))
                {
                    category = "Personal Items";
                }

                else if (categoryChoice.equals("E"))
                {
                    category = "Miscellaneous";
                }

                client.reportItem(name, description, location, date, category);

            }
            else if (choice.equals("2"))
            {
                System.out.println("Choose a category:");
                System.out.println("A: School Supplies");
                System.out.println("B: Electronics");
                System.out.println("C: Clothing");
                System.out.println("D: Personal Items");
                System.out.println("E: Miscellaneous");

                String categoryChoice = scanner.nextLine();
                String category = "";

                if (categoryChoice.equals("A"))
                {
                    category = "School Supplies";
                }

                else if (categoryChoice.equals("B"))
                {
                    category = "Electronics";
                }

                else if (categoryChoice.equals("C"))
                {
                    category = "Clothing";
                }

                else if (categoryChoice.equals("D"))
                {
                    category = "Personal Items";
                }

                else if (categoryChoice.equals("E"))
                {
                    category = "Miscellaneous";
                }

                client.browseItems(category);
            }
            else if (choice.equals("3"))
            {
                //admin
            }
            else if (choice.equals("4"))
            {
                System.out.println("Come back if you find something!");
                running = false;
            }   
        }

        scanner.close();
    }

}
