package com.midterm.midterm.entities;

import com.midterm.midterm.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "staff")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    private Long sid;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;
    @Column(name = "image_url", nullable = false, length = 150)
    private String imageUrl;

    @OneToMany(mappedBy = "staff", fetch = FetchType.LAZY)
    private List<Sale> sales;
}