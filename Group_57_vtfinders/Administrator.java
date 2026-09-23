/**
 * Administrator class for VT Finders.
 *
 * Handles administrator specific operations for managing lost items.
 *
 * @author Phani Kathuroju
 * @version 9.23.26
 */

public class Administrator
{
    private String adminId;
    private String username;
    private String password;
    private LostItemDatabase database;

    public Administrator(String adminId, String username,
        String password, LostItemDatabase database){
            this.adminId = adminId;
            this.username = username;
            this.password = password;
            this.database = database;
        }

    public boolean authentication(String username, String password)
    {
        return this.username.equals(username) && this.password.equals(password);
    }

    public ArrayList<LostItem> viewAllItems()
    {
        return database.getAllItems();
    }

    public void removeItem(String id)
    {
        database.removeItem(id);
    }
    
}
