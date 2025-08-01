# Dashboard.java Overview

This project includes a graphical dashboard for managing patient data, implemented in the `Dashboard` class (`src/Dashboard.java`). The dashboard provides a user-friendly interface for quick access to patient records, statistics, and utility functions.

## What Happens in Dashboard.java

- **Main Window**: Creates a non-resizable main frame titled "Dashboard".
- **Menu Bar**: Includes menus for viewing patient data, graphs, expenditure, assistance, standard lists, settings, and help.
- **To-Do List**: Allows users to add and remove tasks, simulating database operations.
- **Quick Actions**: Provides buttons for common tasks like adding/searching patients and viewing statistics.
- **Graph Display**: Shows patient statistics in graphical form using sample data.

## Functions and Their Tasks

- **Dashboard()**  
  Constructor. Initializes the main frame and calls `display()` to set up the UI.

- **display()**  
  Sets up the main panel, menu bar, To-Do list, and quick action buttons. Arranges components using layouts.

- **handleButtonClick(ActionEvent e)**  
  Handles quick action button clicks. Opens relevant windows or prints messages for each action.

- **setupMenuBar(JMenuBar menuBar)**  
  Configures the menu bar with all menus and menu items. Adds action listeners for menu item events.

- **createTodoPanel()**  
  Builds the To-Do list panel with input field and add/remove buttons.

- **addTodoToList(String task)**  
  Adds a task to the To-Do list and simulates adding it to a database.

- **removeTodoFromList(String task)**  
  Removes a task from the To-Do list and simulates removing it from a database.

- **showGraphFor(String type)**  
  Fetches sample patient data for the selected type and displays it using the `PatientGraph.showGraph` method.

- **fetchPatientData(String type)**  
  Returns sample data for different patient statistics (e.g., monthly, yearly, follow-ups).

- **main(String[] args)**  
  Entry point. Launches the dashboard.

---

This class provides the main user interface for the patient management system, combining navigation, data
