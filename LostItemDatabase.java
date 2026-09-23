import java.util.ArrayList;

/**
 * Manages the collection of reported LostItem objects: storing submitted
 * items and providing them back when they need to be displayed to the user.
 * 
 * @author Group 57
 * @version 2026.09.23
 */
public class LostItemDatabase {
    private ArrayList<LostItem> items = new ArrayList<>();

    /**
     * Saves a new item to the ArrayList.
     *
     * @param item
     *            the item to store
     */
    public void addItem(LostItem item) {
        items.add(item);
    }


    /**
     * @return every stored item, in the order they were reported
     */
    public ArrayList<LostItem> getAllItems() {
        return items;
    }


    /**
     * Returns a filtered list of items matching the given category
     * (case-insensitive).
     *
     * @param cat
     *            the category to filter by
     * @return an ArrayList of items in that category; empty if none match
     */
    public ArrayList<LostItem> getItemsByCategory(String cat) {
        ArrayList<LostItem> result = new ArrayList<>();
        if (cat == null) {
            return result;
        }
        for (LostItem item : items) {
            String category = item.getCategory();
            if (category != null && cat.length() == category.length()) {
                boolean matches = true;

                for (int i = 0; i < cat.length(); i++) {
                    if (Character.toLowerCase(cat.charAt(i)) != Character
                        .toLowerCase(category.charAt(i))) {
                        matches = false;
                        break;
                    }
                }

                if (matches) {
                    result.add(item);
                }
            }

        }
        return result;
    }
}