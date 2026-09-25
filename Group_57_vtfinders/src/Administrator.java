import java.util.ArrayList;
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

    /* Constructor for Administrator class.
     *
     * @param adminId unique id assigned to each administrator
     * @param username administrator's username
     * @param password administrator's password
     * @param database reference to the shared LostItemDatabase
     */

    public Administrator(String adminId, String username,
        String password, LostItemDatabase database){
            this.adminId = adminId;
            this.username = username;
            this.password = password;
            this.database = database;
        }

    /*
     * Authenticates the administrator using the provided username and password.
     *
     * @param username the username to authenticate
     * @param password the password to authenticate
     * @return true if authentication is successful, false otherwise
     */
    public boolean authentication(String username, String password)
    {
        return this.username.equals(username) && this.password.equals(password);
    }

    /* Returns a list of all lost items in the database.
     *
     * @return an ArrayList of all lost items
     */
    public ArrayList<LostItem> viewAllItems()
    {
        return database.getAllItems();
    }

    /* Removes a lost item from the database.
     *
     * @param id the ID of the item to remove
     * @param cat the category of the item
     */
    public void removeItem(String id, int cat)
    {
        database.removeItem(id, cat);
    }
    
}
