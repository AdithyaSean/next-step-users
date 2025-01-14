# Development Documentation

## Entity Relationships

### User Inheritance

The `User` entity serves as the base class for both `Student` and `Institution` entities. This inheritance allows for shared attributes and behaviors across different types of users in the system.

#### User Entity

The `User` entity contains common attributes such as `id`, `name`, `email`, `password`, `role`, and `active` status. It also includes timestamps for `createdAt` and `updatedAt`.

```java
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(nullable = false)
    private boolean active = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }
}
```

#### Student Entity

The `Student` entity extends the `User` entity and adds additional attributes specific to students, such as `contact`, `school`, `district`, `olResults`, `alResults`, `stream`, `zScore`, `gpa`, `interests`, `skills`, and `predictions`.

```java
@Entity
public class Student extends User {
    private String contact;
    private String school;
    private String district;
    
    @ElementCollection
    private Map<String, String> olResults;
    
    @ElementCollection
    private Map<String, String> alResults;
    
    private String stream;
    private double zScore;
    private double gpa;
    
    @ElementCollection
    private List<String> interests;
    
    @ElementCollection
    private List<String> skills;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CareerPrediction> predictions;

    private String firebaseUid;
    private String firebaseToken;

    // Getters and Setters
}
```

#### Institution Entity

The `Institution` entity also extends the `User` entity and includes attributes specific to institutions, such as `address`, `contactPerson`, and `institutionType`.

```java
@Entity
public class Institution extends User {
    private String address;
    private String contactPerson;
    private String institutionType;

    // Getters and Setters
}
```

### Service Layer

The `UserService` class handles the business logic for user management, including creating, retrieving, updating, and deleting users. It also includes methods for converting entities to DTOs.

```java
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO createUser(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(UUID id) {
        return userRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteUser(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setActive(false);
        userRepository.save(user);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setActive(user.isActive());
        return dto;
    }
}
```

### Controller Layer

The `UserController` class exposes REST endpoints for user management, including creating, retrieving, updating, and deleting users.

```java
@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
```

### Repository Layer

The `UserRepository` interface extends `JpaRepository` and provides methods for querying the database, such as finding a user by email and checking if an email exists.

```java
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
```

### Conclusion

This inheritance structure allows for a clean and maintainable design, where common user attributes and behaviors are centralized in the `User` entity, while specific attributes for students and institutions are added in their respective subclasses. This approach promotes code reuse and simplifies the management of different user types within the system.