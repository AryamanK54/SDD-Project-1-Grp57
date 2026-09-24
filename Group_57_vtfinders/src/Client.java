import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Client class for VT Finders.
 *
 * Handles the user-facing operations for reporting and browsing lost items.
 * Client owns all console prompting/validation for item details. It is
 * handed the single shared Scanner (created once in VTFinders) rather than
 * creating its own, so there's only ever one Scanner reading from System.in
 * for the whole program.
 *
 * @author Aryaman Kapoor
 * @version 9.23.26
 */
public class Client {

    // Fields -----------------------------------------------------------------
    private LostItemDatabase database;
    private Scanner scanner;

    private static final int MAX_FIELD_LENGTH = 100;
    private static final int MAX_DESCRIPTION_LENGTH = 300;

    private static final ArrayList<String> validCategories = new ArrayList<>();

    static {
        validCategories.add("School Supplies");
        validCategories.add("Electronics");
        validCategories.add("Clothing");
        validCategories.add("Personal Items");
        validCategories.add("Miscellaneous");
    }

    // Constructor ------------------------------------------------------------
    /**
     * @param db      the shared lost item database
     * @param scanner the single Scanner shared with VTFinders, so only one
     *                Scanner ever reads from System.in for the whole program
     */
    public Client(LostItemDatabase db, Scanner scanner) {
        this.database = db;
        this.scanner = scanner;
    }

    // Methods ----------------------------------------------------------------
    /**
     * Prompts the user for all details of a lost/found item, validating and
     * re-prompting on each field until it's acceptable, generates a unique ID,
     * and adds the resulting LostItem to the shared database.
     */
    public void reportItem() {
        System.out.println("\n---- Report a lost item -----");

        String name = promptForRequiredField("Item name", MAX_FIELD_LENGTH);
        String description = promptForRequiredField("Description", MAX_DESCRIPTION_LENGTH);
        String location = promptForRequiredField("Location found", MAX_FIELD_LENGTH);
        String date = promptForDate();
        String category = promptForCategory();
        String id = generateId();

        LostItem item = new LostItem(id, name, description, location, date, category);
        database.addItem(item);

        System.out.println("Item reported successfully!");
        System.out.println(item);
    }

    // -------------------------------------------------------------------------
    /**
     * Prompts the user for a category, then displays all items in that category.
     */
    public void browseItems() {
        System.out.println("\n--- Browse Items by Category ---");
        String category = promptForCategory();

        ArrayList<LostItem> results = database.getItemsByCategory(category);

        if (results == null || results.isEmpty()) {
            System.out.println("No items found in the \"" + category + "\" category.");
            return;
        }

        System.out.println("Items in \"" + category + "\":");
        for (LostItem item : results) {
            System.out.println(item);
        }
    }

    // Validation and Scanner helper methods ----------------------------------
    /**
     * @param input
     * @return boolean
     *         Checks whether the given input is a valid category
     */
    public boolean isValidCategory(String input) {
        if (input == null) {
            return false;
        }

        for (String valid : validCategories) {
            if (valid.equalsIgnoreCase(input)) {
                return true;
            }
        }

        return false;
    }

    // -------------------------------------------------------------------------
    /**
     * @return String
     *         Repeatedly prompt for a category number until the user enters a valid
     *         choice (1 through the number of categories), then returns the
     *         matching category name.
     */
    public String promptForCategory() {
        while (true) {
            System.out.println("Choose a category:");
            for (int i = 0; i < validCategories.size(); i++) {
                System.out.println((i + 1) + ": " + validCategories.get(i));
            }
            System.out.print("Category (1-" + validCategories.size() + "): ");
            String input = scanner.nextLine().trim();

            int choice;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Error: \"" + input + "\" is not a valid choice. Please enter a number 1-"
                        + validCategories.size() + ".");
                continue;
            }

            if (choice < 1 || choice > validCategories.size()) {
                System.out.println("Error: \"" + choice + "\" is not a valid choice. Please enter a number 1-"
                        + validCategories.size() + ".");
                continue;
            }

            return validCategories.get(choice - 1);
        }
    }

    /**
     * @param fieldLabel
     * @param maxLength
     *                   Repeatedly prompts for a non-blank field up to maxLength
     *                   characters.
     */
    private String promptForRequiredField(String fieldLabel, int maxLength) {
        while (true) {
            System.out.print(fieldLabel + ": ");
            String input = scanner.nextLine();

            if (input == null || input.trim().isEmpty()) {
                System.out.println("Error: " + fieldLabel + " cannot be blank. Please try again.");
                continue;
            }

            int length = input.trim().length();
            if (length > maxLength) {
                int over = length - maxLength;
                System.out.println("Error: " + fieldLabel + " is " + length + "/" + maxLength
                        + " characters (" + over + " over). Please shorten it.");
                continue;
            }

            return input.trim();
        }
    }

    /**
     * Repeatedly prompts for a date in MM/DD/YYYY format, rejecting bad
     * formats, non-numeric input, impossible calendar dates, and future dates.
     */
    private String promptForDate() {
        while (true) {
            System.out.print("Date found (MM/DD/YYYY): ");
            String input = scanner.nextLine().trim();

            String[] parts = input.split("/");
            if (parts.length != 3) {
                System.out.println("Error: date must be in MM/DD/YYYY format. Please try again.");
                continue;
            }

            int month, day, year;
            try {
                month = Integer.parseInt(parts[0]);
                day = Integer.parseInt(parts[1]);
                year = Integer.parseInt(parts[2]);
            } catch (NumberFormatException e) {
                System.out.println("Error: date must contain only numbers (MM/DD/YYYY). Please try again.");
                continue;
            }

            if (!isValidCalendarDate(month, day, year)) {
                System.out.println("Error: \"" + input + "\" is not a valid date. Please try again.");
                continue;
            }

            LocalDate entered = LocalDate.of(year, month, day);
            if (entered.isAfter(LocalDate.now())) {
                System.out.println("Error: date cannot be in the future. Please try again.");
                continue;
            }

            return input;
        }
    }

    /**
     * Checks month/day/year ranges and day-of-month bounds (leap years
     * included), so bad numeric input never reaches LocalDate.of() unvalidated.
     */
    private boolean isValidCalendarDate(int month, int day, int year) {
        if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1) {
            return false;
        }

        int[] daysInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int maxDay = daysInMonth[month - 1];
        if (month == 2 && isLeapYear(year)) {
            maxDay = 29;
        }

        return day <= maxDay;
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * Generates a random 5-letter uppercase ID for a new LostItem, per the
     * group's ID format decision.
     */
    private String generateId() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            char letter = (char) ('A' + random.nextInt(26));
            sb.append(letter);
        }
        return sb.toString();
    }
}