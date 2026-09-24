import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Unit tests for Client.
 *
 * Client owns its own Scanner(System.in) prompting loops, so these tests
 * simulate user input by redirecting System.in before constructing each
 * Client, and capture console output via System.out to confirm the right
 * prompts/errors are shown and the right values end up in the database.
 *
 * Assumes JUnit 4 and that LostItem's toString() follows the
 * "Name - Description - Location - Date - Category" format shown in the
 * spec doc, since LostItem doesn't expose a getDate()/getId() getter.
 */
public class ClientTest {

    private LostItemDatabase database;
    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputCapture;

    @Before
    public void setUp() {
        database = new LostItemDatabase();
        outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
    }

    @After
    public void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /** Builds a Client whose Scanner reads the given simulated user input. */
    private Client clientWithInput(String simulatedInput) {
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));
        return new Client(database, new Scanner(System.in));
    }

    private String consoleOutput() {
        return outputCapture.toString(StandardCharsets.UTF_8);
    }

    // ------------------ isValidCategory ------------------

    @Test
    public void testIsValidCategory_allFiveExactMatches() {
        Client client = clientWithInput("");
        assertTrue(client.isValidCategory("School Supplies"));
        assertTrue(client.isValidCategory("Electronics"));
        assertTrue(client.isValidCategory("Clothing"));
        assertTrue(client.isValidCategory("Personal Items"));
        assertTrue(client.isValidCategory("Miscellaneous"));
    }

    @Test
    public void testIsValidCategory_caseInsensitive() {
        Client client = clientWithInput("");
        assertTrue(client.isValidCategory("electronics"));
        assertTrue(client.isValidCategory("CLOTHING"));
        assertTrue(client.isValidCategory("miscellaneous"));
    }

    @Test
    public void testIsValidCategory_invalidInputs() {
        Client client = clientWithInput("");
        assertFalse(client.isValidCategory("Toys"));
        assertFalse(client.isValidCategory(""));
        assertFalse(client.isValidCategory(null));
    }

    // ------------------ reportItem: happy path ------------------

    @Test
    public void testReportItem_validInputAddsOneItemToDatabase() {
        String input = String.join("\n",
                "Hydro Flask",
                "Blue 32oz water bottle",
                "Squires Student Center",
                "09/17/2026",
                "2") + "\n"; // Electronics
        Client client = clientWithInput(input);

        client.reportItem();

        ArrayList<LostItem> items = database.getAllItems();
        assertEquals(1, items.size());
        assertEquals("Hydro Flask", items.get(0).getName());
        assertEquals("Electronics", items.get(0).getCategory());
    }

    // ------------------ reportItem: bad-input re-prompt loops ------------------

    @Test
    public void testReportItem_blankNameIsRejectedAndReprompted() {
        String input = String.join("\n",
                "", // blank name -> rejected
                "Hydro Flask", // valid name
                "Blue water bottle",
                "West End Market",
                "09/10/2026",
                "5") + "\n"; // Miscellaneous
        Client client = clientWithInput(input);

        client.reportItem();

        assertEquals(1, database.getAllItems().size());
        assertEquals("Hydro Flask", database.getAllItems().get(0).getName());
        assertTrue(consoleOutput().contains("cannot be blank"));
    }

    @Test
    public void testReportItem_invalidCategoryIsRejectedAndReprompted() {
        String input = String.join("\n",
                "Backpack",
                "Black North Face backpack",
                "Torgersen Hall",
                "09/12/2026",
                "9", // out-of-range choice -> rejected
                "1") + "\n"; // valid: School Supplies
        Client client = clientWithInput(input);

        client.reportItem();

        assertEquals("School Supplies", database.getAllItems().get(0).getCategory());
        assertTrue(consoleOutput().contains("not a valid choice"));
    }

    @Test
    public void testReportItem_badDateFormatAndImpossibleDateAreRejectedAndReprompted() {
        String input = String.join("\n",
                "Umbrella",
                "Black umbrella",
                "Newman Library",
                "September 12th", // wrong format -> rejected
                "13/40/2026", // impossible month/day -> rejected
                "09/12/2026", // valid
                "4") + "\n"; // Personal Items
        Client client = clientWithInput(input);

        client.reportItem();

        assertTrue(database.getAllItems().get(0).toString().contains("09/12/2026"));
        assertTrue(consoleOutput().contains("MM/DD/YYYY"));
    }

    @Test
    public void testReportItem_futureDateIsRejectedAndReprompted() {
        String input = String.join("\n",
                "Notebook",
                "Spiral notebook",
                "McBryde Hall",
                "01/01/2099", // future date -> rejected
                "09/01/2026", // valid, past date
                "1") + "\n"; // School Supplies
        Client client = clientWithInput(input);

        client.reportItem();

        assertTrue(database.getAllItems().get(0).toString().contains("09/01/2026"));
        assertTrue(consoleOutput().contains("cannot be in the future"));
    }

    @Test
    public void testReportItem_tooLongNameIsRejectedAndReprompted() {
        String tooLong = "A".repeat(150); // 150 chars, 50 over the 100-char max
        String input = String.join("\n",
                tooLong, // too long -> rejected
                "Water Bottle", // valid
                "Metal water bottle",
                "Owens Dining Hall",
                "09/05/2026",
                "5") + "\n"; // Miscellaneous
        Client client = clientWithInput(input);

        client.reportItem();

        assertEquals("Water Bottle", database.getAllItems().get(0).getName());
        assertTrue(consoleOutput().contains("150/100 characters (50 over)"));
    }

    // ------------------ browseItems ------------------

    @Test
    public void testBrowseItems_onlyShowsMatchingCategory() {
        database.addItem(new LostItem("AAAAA", "Hoodie", "Black hoodie", "Squires", "09/01/2026", "Clothing"));
        database.addItem(
                new LostItem("BBBBB", "Laptop Charger", "Dell charger", "Newman Library", "09/02/2026", "Electronics"));

        Client client = clientWithInput("3\n"); // Clothing
        client.browseItems();

        String output = consoleOutput();
        assertTrue(output.contains("Hoodie"));
        assertFalse(output.contains("Laptop Charger"));
    }

    @Test
    public void testBrowseItems_emptyCategoryPrintsNoItemsMessage() {
        Client client = clientWithInput("2\n"); // Electronics
        client.browseItems();

        assertTrue(consoleOutput().contains("No items found"));
    }

    @Test
    public void testBrowseItems_invalidCategoryIsRejectedAndReprompted() {
        database.addItem(new LostItem("CCCCC", "Keys", "Set of keys", "Squires", "09/03/2026", "Personal Items"));

        String input = String.join("\n",
                "9", // out-of-range choice -> rejected
                "4") + "\n"; // valid: Personal Items
        Client client = clientWithInput(input);

        client.browseItems();

        assertTrue(consoleOutput().contains("Keys"));
        assertTrue(consoleOutput().contains("not a valid choice"));
    }

    @Test
    public void testBrowseItems_nonNumericCategoryIsRejectedAndReprompted() {
        database.addItem(new LostItem("DDDDD", "Charger", "USB-C charger", "Squires", "09/04/2026", "Electronics"));

        String input = String.join("\n",
                "Electronics", // typed name instead of a number -> rejected
                "2") + "\n"; // valid: Electronics
        Client client = clientWithInput(input);

        client.browseItems();

        assertTrue(consoleOutput().contains("Charger"));
        assertTrue(consoleOutput().contains("not a valid choice"));
    }
}