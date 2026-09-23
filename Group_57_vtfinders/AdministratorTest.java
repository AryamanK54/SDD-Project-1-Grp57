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
}