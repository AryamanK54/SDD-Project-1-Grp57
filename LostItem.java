
/**
 * Represents one reported lost item and stores its identifying information:
 * id, name, description, location found, date found, and category.
 * 
 * @author Group 57
 * @version 2026.09.23
 */
public class LostItem {

    private String id;
    private String name;
    private String description;
    private String location;
    private String date;
    private String category;

    /**
     * Initializes all fields for a lost item.
     *
     * @param id
     *            unique id assigned to each item
     * @param name
     *            name of the lost item
     * @param desc
     *            decriptor of the item
     * @param loc
     *            location where the item was found
     * @param date
     *            date found
     * @param cat
     *            one of the five valid categories items are sorted into
     */
    public LostItem(
        String id,
        String name,
        String desc,
        String loc,
        String date,
        String cat) {
        this.id = id;
        this.name = name;
        this.description = desc;
        this.location = loc;
        this.date = date;
        this.category = cat;
    }


    /**
     * @return the item's name
     */
    public String getName() {
        return name;
    }


    /**
     * @return the item's category
     */
    public String getCategory() {
        return category;
    }


    public String getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }


    public String getLocation() {
        return location;
    }


    public String getDate() {
        return date;
    }


    /**
     * @return a readable summary of the item's details
     */
    @Override
    public String toString() {
        return "ID: " + id + " | Name: " + name + " | Category: " + category
            + " | Location: " + location + " | Date Found: " + date
            + " | Description: " + description;
    }
}
