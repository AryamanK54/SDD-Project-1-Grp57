import java.util.ArrayList;

/**
 * Test class for LostItemDatabase
 *
 * @author Group 57
 * @version 2026.09.23
 */
public class LostItemDatabaseTest extends student.TestCase {
    private LostItemDatabase database;
    private LostItem hydroFlask;
    private LostItem notebook;
    private LostItem charger;

    /**
     * Set up for all test methods. Runs before every test.
     */
    public void setUp() {
        database = new LostItemDatabase();

        hydroFlask = new LostItem("1", "Hydro Flask",
            "Dented, dark green, 32oz", "Squires Student Center", "01/10/2026",
            "Personal Items");
        notebook = new LostItem("2", "Notebook", "Spiral, blue",
            "Torgersen Hall", "01/11/2026", "School Supplies");
        charger = new LostItem("3", "Charger", "USB-C, white", "Newman Library",
            "01/12/2026", "Electronics");
    }


    /**
     * Test method for getAllItems. Should return an empty list when nothing
     * has been added.
     */
    public void testGetAllItemsReturnsEmptyListWhenNothingAdded() {
        ArrayList<LostItem> all = database.getAllItems();
        assertTrue(all.isEmpty());
    }


    /**
     * Test method for addItem. Should store the item in the database.
     */
    public void testAddItemStoresItemInDatabase() {
        database.addItem(hydroFlask);

        ArrayList<LostItem> all = database.getAllItems();
        assertEquals(1, all.size());
        assertEquals(hydroFlask, all.get(0));
    }


    /**
     * Test method for addItem. Duplicate entries should be allowed.
     */
    public void testAddItemAllowsDuplicateEntries() {
        LostItem duplicateName = new LostItem("4", "Hydro Flask",
            "Different bottle", "War Memorial Gym", "01/13/2026",
            "Personal Items");

        database.addItem(hydroFlask);
        database.addItem(duplicateName);

        assertEquals(2, database.getAllItems().size());
    }


    /**
     * Test method for getAllItems. Should preserve insertion order.
     */
    public void testGetAllItemsPreservesInsertionOrder() {
        database.addItem(hydroFlask);
        database.addItem(notebook);
        database.addItem(charger);

        ArrayList<LostItem> all = database.getAllItems();

        assertEquals(hydroFlask, all.get(0));
        assertEquals(notebook, all.get(1));
        assertEquals(charger, all.get(2));
    }


    /**
     * Test method for getItemsByCategory. Should return only matching items.
     */
    public void testGetItemsByCategoryReturnsOnlyMatchingItems() {
        database.addItem(hydroFlask);
        database.addItem(notebook);
        database.addItem(charger);

        ArrayList<LostItem> result = database.getItemsByCategory("Electronics");

        assertEquals(1, result.size());
        assertEquals(charger, result.get(0));
    }


    /**
     * Test method for getItemsByCategory. Category matching should be
     * case-insensitive.
     */
    public void testGetItemsByCategoryIsCaseInsensitive() {
        database.addItem(hydroFlask);

        ArrayList<LostItem> result = database.getItemsByCategory(
            "personal items");

        assertEquals(1, result.size());
        assertEquals(hydroFlask, result.get(0));
    }


    /**
     * Test method for getItemsByCategory. Should return an empty list when
     * there are no matching items.
     */
    public void testGetItemsByCategoryReturnsEmptyListWhenNoMatches() {
        database.addItem(hydroFlask);
        database.addItem(notebook);

        ArrayList<LostItem> result = database.getItemsByCategory("Clothing");

        assertTrue(result.isEmpty());
    }


    /**
     * Test method for getItemsByCategory. Should return an empty list when
     * the category is null.
     */
    public void testGetItemsByCategoryReturnsEmptyListForNullCategory() {
        database.addItem(hydroFlask);

        ArrayList<LostItem> result = database.getItemsByCategory(null);

        assertTrue(result.isEmpty());
    }


    /**
     * Test method for getItemsByCategory. Should return all items when
     * multiple items share the same category.
     */
    public void testGetItemsByCategoryReturnsAllMatchingItemsWhenMultipleShareCategory() {
        LostItem secondElectronic = new LostItem("5", "Laptop",
            "Silver, 13-inch", "GLC", "01/14/2026", "Electronics");

        database.addItem(charger);
        database.addItem(secondElectronic);
        database.addItem(hydroFlask);

        ArrayList<LostItem> result = database.getItemsByCategory("Electronics");

        assertEquals(2, result.size());
        assertTrue(result.contains(charger));
        assertTrue(result.contains(secondElectronic));
    }
}