package com.example.Canteen.Controller;

import com.example.Canteen.Models.Menu;
import com.example.Canteen.Models.User;
import com.example.Canteen.Models.orders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CanteenController {
    private static final String USERS_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Juniors Project\\Canteen\\src\\main\\java\\com\\example\\Canteen\\Models\\users.json";
    private static final String MENU_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Juniors Project\\Canteen\\src\\main\\java\\com\\example\\Canteen\\Models\\menu.json";
    private static final String ORDERS_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Juniors Project\\Canteen\\src\\main\\java\\com\\example\\Canteen\\Models\\orders.json";


    private final List<Menu> menus = new ArrayList<>();

    @GetMapping("/hi")
    public String hello() {
        return "Hello";
    }

    private final List<User> users = new ArrayList<>();

    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody User newUser) {
        try {
            // Load existing users from the JSON file
            loadUsers();

            // Set the ID for the new user
            Long nextId = getNextUserId();
            newUser.setId(nextId);

            // Add the new user to the list
            users.add(newUser);

            // Save the updated user list to the JSON file
            saveUsers();

            // Create a response payload with the userID and username
            // You can customize the response structure as per your requirements
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("userId", newUser.getId());
            responseBody.put("username", newUser.getUsername());
            responseBody.put("role", newUser.getRole());

            return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during signup");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User loginUser) {
        try {
            // Load existing users from the JSON file
            loadUsers();

            // Find the user with matching username and password
            for (User user : users) {
                if (user.getUsername().equals(loginUser.getUsername()) && user.getPassword().equals(User.hashPassword(loginUser.getPassword()))) {
                    // Create a response payload with the userID and username
                    // You can customize the response structure as per your requirements
                    Map<String, Object> responseBody = new HashMap<>();
                    responseBody.put("userId", user.getId());
                    responseBody.put("username", user.getUsername());
                    responseBody.put("role", user.getRole());

                    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during login");
        }
    }
    // Helper method to load users from the JSON file
    private void loadUsers() throws IOException {
        File file = new File(USERS_FILE_PATH);

        // If the file exists, load the users
        if (file.exists()) {
            ObjectMapper mapper = new ObjectMapper();
            users.clear();
            users.addAll(mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, User.class)));
        }
    }

    // Helper method to save users to the JSON file
    private void saveUsers() throws IOException {
        File file = new File(USERS_FILE_PATH);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, users);
    }

    // Helper method to generate the next available user ID
    private Long getNextUserId() {
        Long maxId = 0L;
        for (User user : users) {
            if (user.getId() > maxId) {
                maxId = user.getId();
            }
        }
        return maxId + 1;
    }

    //----------------------------------------------Login--------------------------------------------------------------------//
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

    @DeleteMapping("/menu/{id}")
    public ResponseEntity<String> deleteMenuEntry(@PathVariable Long id) {
        try {
            // Find the menu item with the matching ID
            Optional<Menu> menuToDelete = menus.stream()
                    .filter(menu -> menu.getId().equals(id))
                    .findFirst();

            if (menuToDelete.isPresent()) {
                // Remove the menu item from the list
                menus.remove(menuToDelete.get());

                // Save the updated list to the JSON file
                saveMenuToJsonFile();

                return ResponseEntity.status(HttpStatus.OK).body("Menu entry deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu entry not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting menu entry.");
        }
    }


    @PutMapping("/menu/{id}")
    public ResponseEntity<String> updateMenuEntry(@PathVariable Long id, @RequestBody Menu updatedMenu) {
        try {
            // Find the menu item with the matching ID
            Optional<Menu> menuToUpdate = menus.stream()
                    .filter(menu -> menu.getId().equals(id))
                    .findFirst();

            if (menuToUpdate.isPresent()) {
                // Update the menu item
                Menu menu = menuToUpdate.get();
                menu.setName(updatedMenu.getName());
                menu.setDescription(updatedMenu.getDescription());
                menu.setPrice(updatedMenu.getPrice());

                // Save the updated list to the JSON file
                saveMenuToJsonFile();

                return ResponseEntity.status(HttpStatus.OK).body("Menu entry updated successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Menu entry not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while updating menu entry.");
        }
    }


    @GetMapping("/menu/get/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        try {
            // Find the menu item with the matching ID
            Optional<Menu> menu = menus.stream()
                    .filter(item -> item.getId().equals(id))
                    .findFirst();

            if (menu.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(menu.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //--------------------------------------------------Orders Start--------------------------------------------//
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

    @PostMapping("/orders/add")
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

    @GetMapping("/orders/{username}")
    public ResponseEntity<List<orders>> getOrdersByUsername(@PathVariable String username) {
        // Load orders from JSON file
        List<orders> orders = loadOrdersFromJsonFile();

        // Filter orders by username
        List<orders> filteredOrders = orders.stream()
                .filter(order -> order.getUsername().equals(username))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(filteredOrders);
    }


}
