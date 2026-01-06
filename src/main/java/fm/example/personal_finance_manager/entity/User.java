package fm.example.personal_finance_manager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // email

    @Column(nullable = false)
    private String password; // hashed

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String phoneNumber;
}