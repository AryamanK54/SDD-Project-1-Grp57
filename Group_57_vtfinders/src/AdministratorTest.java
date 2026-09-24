import student.TestCase;

public class AdministratorTest extends TestCase
{
    private Administrator administrator;
    private LostItemDatabase database;

    public void setUp()
    {
        database = new LostItemDatabase();
        administrator = new Administrator("admin1", "adminUsername", "f21", database);
    }

    public void testAuthentication()
    {
        assertTrue(administrator.authentication("adminUsername", "f21"));
        assertFalse(administrator.authentication("wrongUsername", "f21"));
        assertFalse(administrator.authentication("adminUsername", "wrongPassword"));
        assertFalse(administrator.authentication("wrongUsername", "wrongPassword"));
    }

    public void testViewAllItems()
    {
        LostItem item1 = new LostItem("1", "Backpack", "Black backpack", "library",
            "Tuesday", "Personal Items");
        database.addItem(item1);

        assertEquals(1, administrator.viewAllItems().size());
        assertEquals(item1, administrator.viewAllItems().get(0));
    }
    
    public void testRemoveItem()
    {
        LostItem item1 = new LostItem("1", "Backpack", "Black backpack", "library",
            "Tuesday", "Personal Items");
        database.addItem(item1);
        administrator.removeItem("1");
        assertEquals(0, administrator.viewAllItems().size());
    }

}

