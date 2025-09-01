package th.mfu.boot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    public static Map<String, User> users = new HashMap<String, User>();

    @PostMapping("/users")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Check if username already exists
        if (users.containsKey(user.getUsername())) {
            // Username conflict → 409
            return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
        }
        // Add user to map
        users.put(user.getUsername(), user);
        // Created → 201
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Collection<User>> list() {
        // Return all users → 200
        return new ResponseEntity<>(users.values(), HttpStatus.OK);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        // Find user
        User user = users.get(username);
        if (user == null) {
            // Not found → 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Found → 200
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
