package com.midterm.midterm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.midterm.midterm.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "staff")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "sales")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid")
    @EqualsAndHashCode.Include
    private Long sid;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String userName;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private Role role;

    @Column(name = "status", nullable = false)
    @Builder.Default
    private Boolean status = true;

    @Column(name = "image_url", length = 150)
    private String imageUrl;

    @OneToMany(mappedBy = "staff", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Sale> sales;
}
