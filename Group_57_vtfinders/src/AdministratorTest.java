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
}