package com.example.Canteen.Controller;
import com.example.Canteen.Models.Menu;
import com.example.Canteen.Models.User;
<<<<<<< HEAD
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
=======
import com.example.Canteen.Models.orders;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
>>>>>>> d9d7865 (Update)
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;


@RestController
@RequestMapping("/api")
public class CanteenController {
    private static final String USERS_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Juniors Project\\Canteen\\src\\main\\java\\com\\example\\Canteen\\Models\\users.json";
    private static final List<User> users = new ArrayList<>();
    private static final String MENU_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Juniors Project\\Canteen\\src\\main\\java\\com\\example\\Canteen\\Models\\menu.json";
<<<<<<< HEAD
=======
    private static final String ORDERS_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Juniors Project\\Canteen\\src\\main\\java\\com\\example\\Canteen\\Models\\orders.json";
>>>>>>> d9d7865 (Update)

    @GetMapping("/hi")
    public String hello(){
        return "Hello";
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        try {
            System.out.println("Received signup request with requestBody: " + user);

            // Check if the user already exists
            if (userExists(user)) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists.");
            }

            // Assign a unique ID to the user
            user.setId(generateUserId());

            // Set the default role to "user"
<<<<<<< HEAD
            user.setRole("user");
=======
            user.setRole("admin");
>>>>>>> d9d7865 (Update)

            // Add the user to the list
            users.add(user);

            // Save the updated list to the JSON file
            saveUsersToJsonFile();
<<<<<<< HEAD

=======
>>>>>>> d9d7865 (Update)
            return ResponseEntity.status(HttpStatus.CREATED).body("Signup successful!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during signup.");
        }
    }

    private boolean userExists(User user) {
        return users.stream().anyMatch(existingUser -> existingUser.getUsername().equals(user.getUsername()));
    }


    // Helper method to generate a unique user ID
    private Long generateUserId() {
        return System.currentTimeMillis(); // You can implement your own ID generation logic
    }

    // Helper method to save the users list to the JSON file
    private void saveUsersToJsonFile() throws IOException {
        List<User> existingUsers = loadUsersFromJsonFile(); // Load existing users from the file

        try (FileWriter fileWriter = new FileWriter(USERS_FILE_PATH)) {
            Gson gson = new Gson();

<<<<<<< HEAD
            // Merge existing users and new users into a single list
            List<User> mergedUsers = new ArrayList<>(existingUsers);
            mergedUsers.addAll(users);

            gson.toJson(mergedUsers, fileWriter);
=======
            List<User> mergedUsers;

            if (existingUsers != null) {
                // Merge existing users and new users into a single list
                mergedUsers = new ArrayList<>(existingUsers);
            } else {
                // No existing users, create a new list with only new users
                mergedUsers = new ArrayList<>();
            }

            // Add new users to the merged list
            if (users != null) {
                mergedUsers.addAll(users);
            }

            gson.toJson(mergedUsers, fileWriter);
            // Merge existing users and new users into a single list
>>>>>>> d9d7865 (Update)
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> optionalUser = findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (optionalUser.isPresent()) {
            User loggedInUser = optionalUser.get();
            Map<String, String> response = new HashMap<>();
<<<<<<< HEAD
            response.put("username", loggedInUser.getUsername());
            response.put("role", loggedInUser.getRole());
=======
            System.out.println(loggedInUser);
            response.put("username", loggedInUser.getUsername());
            response.put("role", loggedInUser.getRole());
            response.put("id", String.valueOf(loggedInUser.getId()));
>>>>>>> d9d7865 (Update)
            response.put("password", loggedInUser.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid username or password.");
        }
    }
    // Helper method to load the users list from the JSON file
    private static List<User> loadUsersFromJsonFile() {
        try (Reader reader = new FileReader(USERS_FILE_PATH)) {
            Gson gson = new Gson();
            Type userListType = new TypeToken<List<User>>() {}.getType();
            return gson.fromJson(reader, userListType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private Optional<User> findUserByUsernameAndPassword(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst();
    }

    private List<Menu> menus = new ArrayList<>();

    @PostMapping("/menu/add")
<<<<<<< HEAD
    public ResponseEntity<String> addMenuItem(@RequestBody Menu menu) {
        try {
            menu.setId(generateUserId());
            menu.setCreatedAt(new Date());
            menus.add(menu);
            saveMenuItemsToJsonFile();
=======
    public ResponseEntity<String> addMenuItem(@RequestBody Menu newMenu) {
        try {
            newMenu.setId(generateUserId());
            newMenu.setCreatedAt(new Date());
            menus.add(newMenu);
            saveMenuItemsToJsonFile(newMenu);
>>>>>>> d9d7865 (Update)
            return ResponseEntity.status(HttpStatus.CREATED).body("Menu item added successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding menu item.");
        }
    }
<<<<<<< HEAD
    private void saveMenuItemsToJsonFile() throws IOException {
        try (FileWriter fileWriter = new FileWriter(MENU_FILE_PATH, true)) {
            Gson gson = new Gson();
            gson.toJson(menus, fileWriter);
        }
=======

    private void saveMenuItemsToJsonFile(Menu newMenu) throws IOException {
        Gson gson = new Gson();
        String jsonFilePath = MENU_FILE_PATH;

        // Read the existing JSON array from the file
        List<Menu> existingMenus = new ArrayList<>();
        try (JsonReader reader = new JsonReader(new FileReader(jsonFilePath))) {
            reader.setLenient(true); // Set JsonReader to be lenient
            Type menuListType = new TypeToken<List<Menu>>() {}.getType();
            existingMenus = gson.fromJson(reader, menuListType);
        }
        if (existingMenus == null) {
            existingMenus = new ArrayList<>();
        }
        // Append the new menu item to the existing array
        existingMenus.add(newMenu);

        // Write the updated array to the file
        try (FileWriter fileWriter = new FileWriter(jsonFilePath)) {
            gson.toJson(existingMenus, fileWriter);
        }
    }

//API to get the menu items
    @GetMapping("/menu")
    public ResponseEntity<List<Menu>> getMenuItems() {
        try {
            List<Menu> menuItems = loadMenuItemsFromJsonFile();
            return ResponseEntity.ok(menuItems);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private List<Menu> loadMenuItemsFromJsonFile() throws IOException {
        Gson gson = new Gson();
        String jsonFilePath = MENU_FILE_PATH;

        // Read the JSON array from the file
        try (JsonReader reader = new JsonReader(new FileReader(jsonFilePath))) {
            reader.setLenient(true); // Set JsonReader to be lenient
            Type menuListType = new TypeToken<List<Menu>>() {}.getType();
            return gson.fromJson(reader, menuListType);
        }
    }


    @DeleteMapping("/menu/{id}")
    public ResponseEntity<String> deleteMenuItem(@PathVariable("id") long id) {
        try {
            boolean deleted = deleteMenuItemById(id);
            if (deleted) {
                return ResponseEntity.ok("Menu item deleted successfully!");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu item not found");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting menu item.");
        }
    }

    private boolean deleteMenuItemById(long id) throws IOException {
        List<Menu> existingMenus = loadMenuItemsFromJsonFile();

        // Find the menu item by ID
        Optional<Menu> menuItemOptional = existingMenus.stream()
                .filter(menu -> menu.getId() == id)
                .findFirst();

        if (menuItemOptional.isPresent()) {
            // Remove the menu item from the list
            Menu menuItem = menuItemOptional.get();
            existingMenus.remove(menuItem);

            // Save the updated list to the file
            saveUpdatedMenuItemsToJsonFile(existingMenus);

            return true;
        } else {
            return false;
        }
    }

    private void saveUpdatedMenuItemsToJsonFile(List<Menu> updatedMenus) throws IOException {
        Gson gson = new Gson();
        String jsonFilePath = MENU_FILE_PATH;

        // Write the updated array to the file
        try (FileWriter fileWriter = new FileWriter(jsonFilePath)) {
            gson.toJson(updatedMenus, fileWriter);
        }
    }


    @PostMapping("/orders/add")
    public ResponseEntity<String> addOrder(@RequestBody orders newOrder) {
        try {
            // Generate a unique ID for the new order
            long orderId = generateUserId();

            // Set the generated ID and current timestamp for the new order
            newOrder.setId(orderId);
            newOrder.setCreatedAt(new Date());

            // Save the new order to the orders.json file
            saveOrderToJsonFile(newOrder);

            return ResponseEntity.status(HttpStatus.CREATED).body("Order added successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding order.");
        }
    }

    private void saveOrderToJsonFile(orders newOrder) throws IOException {
        Gson gson = new Gson();
        String jsonFilePath = ORDERS_FILE_PATH;

        // Read the existing JSON array from the file
        List<orders> existingOrders = new ArrayList<>();
        try (JsonReader reader = new JsonReader(new FileReader(jsonFilePath))) {
            reader.setLenient(true); // Set JsonReader to be lenient
            Type orderListType = new TypeToken<List<orders>>() {}.getType();
            existingOrders = gson.fromJson(reader, orderListType);
        }

        // Append the new order to the existing array
        if (existingOrders == null) {
            existingOrders = new ArrayList<>();
        }
        existingOrders.add(newOrder);

        // Write the updated array to the file
        try (FileWriter fileWriter = new FileWriter(jsonFilePath)) {
            gson.toJson(existingOrders, fileWriter);
        }
    }


    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<orders>> getOrdersByUserId(@PathVariable long userId) {
        try {
            List<orders> userOrders = getOrdersFromJsonFile(userId);
            if (userOrders.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(userOrders);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private List<orders> getOrdersFromJsonFile(long userId) throws IOException {
        Gson gson = new Gson();
        String jsonFilePath = ORDERS_FILE_PATH;

        List<orders> allOrders = new ArrayList<>();
        try (FileReader fileReader = new FileReader(jsonFilePath)) {
            Type orderListType = new TypeToken<List<orders>>() {}.getType();
            allOrders = gson.fromJson(fileReader, orderListType);
        }

        List<orders> userOrders = new ArrayList<>();
        for (orders order : allOrders) {
            if (order.getUserId() == userId) {
                userOrders.add(order);
            }
        }

        return userOrders;
    }
    @GetMapping("/orders")
    public ResponseEntity<List<orders>> getOrdersByUserId() {
        try {
            List<orders> userOrders = getAllOrdersFromJsonFile();
            if (userOrders.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(userOrders);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private List<orders> getAllOrdersFromJsonFile() throws IOException {
        Gson gson = new Gson();

        String jsonFilePath = ORDERS_FILE_PATH;

        List<orders> allOrders = new ArrayList<>();
        try (FileReader fileReader = new FileReader(jsonFilePath)) {
            Type orderListType = new TypeToken<List<orders>>() {
            }.getType();
            allOrders = gson.fromJson(fileReader, orderListType);
        }

        return allOrders;
>>>>>>> d9d7865 (Update)
    }
}

