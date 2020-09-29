package com.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "customer")
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String token;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void activatedWith(String token) {
        this.token = token;
    }

    public boolean hasToken() {
        return !StringUtils.isEmpty(this.token);
    }
}
