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
}
