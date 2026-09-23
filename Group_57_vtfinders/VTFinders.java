import java.util.Scanner;
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
                //report items
            }
            if (choice.equals("2"))
            {
                //brows items
            }
            if (choice.equals("3"))
            {
                //admin
            }
            if (choice.equals("4"))
            {
                running = false;
            }   
        }
    }

}
