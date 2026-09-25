# VT Finders

A console-based lost-and-found application built for CS 2114 (Group 57). Users can report items they've lost or found, browse reported items by category, and — for administrators — view and remove reports.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Admin Access](#admin-access)
- [Categories](#categories)
- [Project Structure](#project-structure)
- [Running the Program](#running-the-program)
- [Running the Tests](#running-the-tests)
- [Team](#team)
- [Known Limitations](#known-limitations)

## Overview

VT Finders records items reported as lost or found and organizes them into five categories, making it easier for users to locate items they may have lost, or for finders to report what they've turned in.

## Features

**Report an item** — Prompts for name, description, location, date found, and category. Every field is validated with a re-prompt loop:
- Name, description, and location cannot be blank, and are capped at a max length (see below); if too long, the prompt tells you exactly how many characters over the limit you are.
- Date must be entered as `MM/DD/YYYY`, must be a real calendar date, and cannot be in the future.
- Category is chosen by number (see [Categories](#categories)) rather than typed out.

Each reported item is assigned a random 5-letter uppercase ID (e.g. `AXQPT`).

**Browse items** — Choose a category by number to see every item reported under it.

**Administration** (requires login) —
- View all reported items.
- Remove an item by its ID and category.

## Admin Access

The Administration menu (option `3` from the main menu) requires a login. The credentials are currently hardcoded in `VTFinders.java`:

| Field    | Value           |
|----------|-----------------|
| Username | `adminUsername` |
| Password | `f21`           |

> **Note:** These credentials are stored in plain text directly in the source code. This is fine for an in-class MVP, but should not be used as-is in any real deployment — a production version would need proper authentication (hashed passwords, a real user store, etc.).

## Categories

Wherever a category is requested (reporting or browsing), it's selected by number:

| Number | Category         |
|--------|------------------|
| 1      | School Supplies  |
| 2      | Electronics      |
| 3      | Clothing         |
| 4      | Personal Items   |
| 5      | Miscellaneous    |

## Project Structure

| File                        | Responsibility |
|-----------------------------|----------------|
| `VTFinders.java`             | Entry point / main menu loop. Owns the single shared `Scanner` and routes user choices to `Client` or `Administrator`. |
| `Client.java`                | User-facing operations: reporting and browsing items. Owns all console prompting/validation for item details. |
| `Administrator.java`         | Admin-only operations: authentication, viewing all items, removing an item. |
| `LostItem.java`               | Represents a single reported item (id, name, description, location, date, category). |
| `LostItemDatabase.java`       | Stores and manages the collection of reported `LostItem` objects (add, retrieve all, filter by category, remove). |
| `ClientTest.java`             | Unit tests for `Client`. |
| `AdministratorTest.java`      | Unit tests for `Administrator`. |
| `LostItemTest.java`           | Unit tests for `LostItem`. |
| `LostItemDatabaseTest.java`   | Unit tests for `LostItemDatabase`. |

## Running the Program

1. Compile all `.java` files in the project's source folder:
   ```
   javac *.java
   ```
2. Run the program:
   ```
   java VTFinders
   ```
3. Follow the on-screen menu to report items, browse items, or (with the admin credentials above) enter Administration mode.

## Running the Tests

The test classes use **JUnit 4**. Make sure `junit-4.13.2.jar` and `hamcrest-core-1.3.jar` are on your classpath, then compile and run with your IDE's test runner (Eclipse, IntelliJ, VS Code with the Java Test Runner extension) or from the command line:

```
javac -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar *.java
java -cp .:junit-4.13.2.jar:hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore ClientTest AdministratorTest LostItemTest LostItemDatabaseTest
```

(Use `;` instead of `:` between classpath entries on Windows.)

## Team

**Group 57**

| Member  | Responsibility |
|---------|-----------------|
| Aryaman | `Client` class |
| Phani   | `Administrator` class, `VTFinders` |
| Maddie  | `LostItem`, `LostItemDatabase` |\

## Known Limitations

- Duplicate-report detection (warning on identical name + location) has not been implemented in `LostItemDatabase.addItem()` yet.
- Admin credentials are hardcoded and stored in plain text — not suitable for production use.
- No persistent storage — all reported items are lost when the program exits (in-memory `ArrayList` only).
