package com.example.Canteen.Controller;
import com.example.Canteen.Models.Menu;
import com.example.Canteen.Models.User;
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
    private static final String USERS_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Juniors Project\\Canteen\\src\\main\\java\\com\\example\\Canteen\\Models\\users.json";
    private static final List<User> users = new ArrayList<>();
    private static final String MENU_FILE_PATH = "C:\\Users\\prajw\\.vscode\\Juniors Project\\Canteen\\src\\main\\java\\com\\example\\Canteen\\Models\\menu.json";

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
        List<User> existingUsers = loadUsersFromJsonFile(); // Load existing users from the file

        try (FileWriter fileWriter = new FileWriter(USERS_FILE_PATH)) {
            Gson gson = new Gson();

            // Merge existing users and new users into a single list
            List<User> mergedUsers = new ArrayList<>(existingUsers);
            mergedUsers.addAll(users);

            gson.toJson(mergedUsers, fileWriter);
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
    public ResponseEntity<String> addMenuItem(@RequestBody Menu menu) {
        try {
            menu.setId(generateUserId());
            menu.setCreatedAt(new Date());
            menus.add(menu);
            saveMenuItemsToJsonFile();
            return ResponseEntity.status(HttpStatus.CREATED).body("Menu item added successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while adding menu item.");
        }
    }
    private void saveMenuItemsToJsonFile() throws IOException {
        try (FileWriter fileWriter = new FileWriter(MENU_FILE_PATH, true)) {
            Gson gson = new Gson();
            gson.toJson(menus, fileWriter);
        }
    }
}

