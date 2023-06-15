package com.example.Canteen.Controller;

import com.example.Canteen.Models.Menu;
import com.example.Canteen.Models.User;
import com.example.Canteen.Models.orders;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    private static final String USERS_FILE_PATH = "C:\\Users\\hp\\Desktop\\6th sem FS\\CMS\\BackendForCMS\\src\\main\\java\\com\\example\\Canteen\\Models\\users.json";
    private static final String MENU_FILE_PATH = "C:\\Users\\hp\\Desktop\\6th sem FS\\CMS\\BackendForCMS\\src\\main\\java\\com\\example\\Canteen\\Models\\menu.json";
    private static final String ORDERS_FILE_PATH = "C:\\Users\\hp\\Desktop\\6th sem FS\\CMS\\BackendForCMS\\src\\main\\java\\com\\example\\Canteen\\Models\\orders.json";

    private final List<User> users = new ArrayList<>();
    private final List<Menu> menus = new ArrayList<>();

    @GetMapping("/hi")
    public String hello() {
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
            user.setRole("user");

            // Add the user to the list
            users.add(user);

            // Save the updated list to the JSON file
            saveUsersToJsonFile();

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
        try (FileWriter fileWriter = new FileWriter(USERS_FILE_PATH)) {
            Gson gson = new Gson();

            gson.toJson(users, fileWriter);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> optionalUser = findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (optionalUser.isPresent()) {
            User loggedInUser = optionalUser.get();
            Map<String, String> response = new HashMap<>();
            response.put("username", loggedInUser.getUsername());
            response.put("role", loggedInUser.getRole());
            response.put("id", String.valueOf(loggedInUser.getId()));
            response.put("password", loggedInUser.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid username or password.");
        }
    }


    private Optional<User> findUserByUsernameAndPassword(String username, String password) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username) && user.getPassword().equals(password))
                .findFirst();
    }

    @GetMapping("/menu")
    public ResponseEntity<List<Menu>> getMenu() {
        return ResponseEntity.status(HttpStatus.OK).body(menus);
    }

    @PostMapping("/menu")
    public ResponseEntity<String> addToMenu(@RequestBody Menu menu) {
        try {
            System.out.println("Received addToMenu request with requestBody: " + menu);

            // Check if the item already exists in the menu
            if (menuExists(menu)) {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Item already exists in the menu.");
            }

            // Assign a unique ID to the menu item
            menu.setId(generateMenuId());

            // Add the menu item to the list
            menus.add(menu);

            // Save the updated list to the JSON file
            saveMenuToJsonFile();

            return ResponseEntity.status(HttpStatus.CREATED).body("Item added to the menu.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding item to the menu.");
        }
    }

    private boolean menuExists(Menu menu) {
        return menus.stream().anyMatch(existingMenu -> existingMenu.getName().equals(menu.getName()));
    }

    private Long generateMenuId() {
        return System.currentTimeMillis(); // You can implement your own ID generation logic
    }

    private void saveMenuToJsonFile() throws IOException {
        try (FileWriter fileWriter = new FileWriter(MENU_FILE_PATH)) {
            Gson gson = new Gson();

            gson.toJson(menus, fileWriter);
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<orders>> getOrders() {
        // Load orders from JSON file
        List<orders> Orders = loadOrdersFromJsonFile();

        return ResponseEntity.status(HttpStatus.OK).body(Orders);
    }

    private List<orders> loadOrdersFromJsonFile() {
        try (Reader reader = new FileReader(ORDERS_FILE_PATH)) {
            Gson gson = new Gson();
            Type orderListType = new TypeToken<List<orders>>(){}.getType();

            return gson.fromJson(reader, orderListType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list if there was an error reading the file
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<String> placeOrder(@RequestBody orders order) {
        try {
            System.out.println("Received placeOrder request with requestBody: " + order);

            // Assign a unique ID to the order
            order.setId(generateOrderId());

            // Save the order to the JSON file
            saveOrderToJsonFile(order);

            return ResponseEntity.status(HttpStatus.CREATED).body("Order placed successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while placing the order.");
        }
    }

    private Long generateOrderId() {
        return System.currentTimeMillis(); // You can implement your own ID generation logic
    }

    private void saveOrderToJsonFile(orders order) throws IOException {
        List<orders> orders = loadOrdersFromJsonFile();
        orders.add(order);

        try (FileWriter fileWriter = new FileWriter(ORDERS_FILE_PATH)) {
            Gson gson = new Gson();

            gson.toJson(orders, fileWriter);
        }
    }
}
