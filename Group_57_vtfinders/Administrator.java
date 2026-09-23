import java.util.ArrayList;
public class Administrator
{
    private String adminId;
    private String username;
    private String password;
    private LostItemDatabase database;

    public Administrator(String adminId, String username,
        String password, ListItemDatabase database){
            this.adminId = adminId;
            this.username = username;
            this.password = password;
            this.database = database
        }

    public boolean authentication(String username, String password)
    {
        return this.username.equals(username) && this.password.equals(password;)
    }

    public ArrayList<LostItem> viewAllItems()
    {
        reuturn database.getAllItems();
    }

    public void removeItem(String id)
    {
        ArrayList<LostItem> = items = database.getAllItems();

        for (int i  = 0; i < items.size(); i++)
        {
            if (items.get(i).getId().equals(id))
            {
                items.remove(i)
                return;
            }
        }
    }
    
}
