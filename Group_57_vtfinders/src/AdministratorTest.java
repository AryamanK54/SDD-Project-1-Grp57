// import student.TestCase;

import static org.junit.Assert.*;

// import java.util.ArrayList;
/**
 * Tests the Aministrator class
 *
 * @author Phani Kathuroju
 * @version 9.24.26
 */

public class AdministratorTest /**extends TestCase*/
{
    private Administrator administrator;
    private LostItemDatabase database;

    /**
     * Sets up the Administrator and LostItemDatabase
     * before each test.
     */

    public void setUp()
    {
        database = new LostItemDatabase();
        administrator = new Administrator("admin1", "adminUsername", "f21", database);
    }

    /**
     * Tests the authentication method with valid and invalid
     * usernames and passwords.
     */

    public void testAuthentication()
    {
        assertTrue(administrator.authentication("adminUsername", "f21"));
        assertFalse(administrator.authentication("wrongUsername", "f21"));
        assertFalse(administrator.authentication("adminUsername", "wrongPassword"));
        assertFalse(administrator.authentication("wrongUsername", "wrongPassword"));
    }

    /**
     * Tests that viewAllItems returns all items in the database.
     */

    public void testViewAllItems()
    {
        LostItem item1 = new LostItem("1", "Backpack", "Black backpack", "library",
            "Tuesday", "Personal Items");
        database.addItem(item1);

        assertEquals(1, administrator.viewAllItems().size());
        assertEquals(item1, administrator.viewAllItems().get(0));
    }
    
    /**
     * Tests that removeItem removes the specified item from
     * the database.
     */

    public void testRemoveItem()
    {
        LostItem item1 = new LostItem("1", "Backpack", "Black backpack", "library",
            "Tuesday", "Personal Items");
        database.addItem(item1);
        administrator.removeItem("1", "Personal Items");
        assertEquals(0, administrator.viewAllItems().size());
    }

}

