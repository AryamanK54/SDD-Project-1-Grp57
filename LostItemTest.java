/**
 * Test class for LostItem
 *
 * @author Group 57
 * @version 2026.09.23
 */
public class LostItemTest extends student.TestCase {
    private LostItem item;

    /**
     * Set up for all test methods. Runs before every test.
     */
    public void setUp() {
        item = new LostItem("1", "Hydro Flask", "Dented, dark green, 32oz",
            "Squires Student Center", "01/10/2026", "Personal Items");
    }


    /**
     * Test method for the constructor. All fields should be set correctly.
     */
    public void testConstructorSetsAllFields() {
        assertEquals("1", item.getId());
        assertEquals("Hydro Flask", item.getName());
        assertEquals("Dented, dark green, 32oz", item.getDescription());
        assertEquals("Squires Student Center", item.getLocation());
        assertEquals("01/10/2026", item.getDate());
        assertEquals("Personal Items", item.getCategory());
    }


    /**
     * Test method for getName.
     */
    public void testGetNameReturnsCorrectValue() {
        assertEquals("Hydro Flask", item.getName());
    }


    /**
     * Test method for getCategory.
     */
    public void testGetCategoryReturnsCorrectValue() {
        assertEquals("Personal Items", item.getCategory());
    }


    /**
     * Test method for toString. Should contain all field values.
     */
    public void testToStringContainsAllFieldValues() {
        String result = item.toString();

        assertTrue(result.contains("1"));
        assertTrue(result.contains("Hydro Flask"));
        assertTrue(result.contains("Dented, dark green, 32oz"));
        assertTrue(result.contains("Squires Student Center"));
        assertTrue(result.contains("01/10/2026"));
        assertTrue(result.contains("Personal Items"));
    }


    /**
     * Test method for the constructor with each valid category.
     */
    public void testConstructorHandlesEachValidCategory() {
        LostItem schoolSupplies = new LostItem("2", "Notebook", "Spiral, blue",
            "Torgersen Hall", "01/11/2026", "School Supplies");
        LostItem electronics = new LostItem("3", "Charger", "USB-C, white",
            "Newman Library", "01/12/2026", "Electronics");
        LostItem clothing = new LostItem("4", "Hoodie", "Maroon, size M",
            "War Memorial Gym", "01/13/2026", "Clothing");
        LostItem misc = new LostItem("5", "Umbrella", "Black, compact", "GLC",
            "01/14/2026", "Miscellaneous");

        assertEquals("School Supplies", schoolSupplies.getCategory());
        assertEquals("Electronics", electronics.getCategory());
        assertEquals("Clothing", clothing.getCategory());
        assertEquals("Miscellaneous", misc.getCategory());
    }


    /**
     * Test method for creating two LostItem objects with different IDs.
     */
    public void testTwoItemsWithDifferentIdsAreDistinctObjects() {
        LostItem other = new LostItem("2", "Hydro Flask",
            "Dented, dark green, 32oz", "Squires Student Center", "01/10/2026",
            "Personal Items");

        assertEquals(item.getName(), other.getName());
        assertTrue(!item.getId().equals(other.getId()));
    }
}
