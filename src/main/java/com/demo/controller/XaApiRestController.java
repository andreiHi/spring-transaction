package com.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class XaApiRestController {
    private final JmsTemplate jmsTemplate;
    private final JdbcTemplate jdbcTemplate;

    public static final String DESTINATION = "messages";

    @PostMapping
    @Transactional
    public void write(@RequestBody Map<String , String> payload,
                      @RequestParam Optional<Boolean>rollback) {

        String id = UUID.randomUUID().toString();
        String message = payload.get("name");
        this.jdbcTemplate.update("INSERT into MESSAGE(ID, MESSAGE) VALUES (?, ?)", id, message);
        this.jmsTemplate.convertAndSend(DESTINATION, id);
        if (rollback.orElse(false)) {
            throw new RuntimeException("couldn't write the message");
        }
    }

    @GetMapping
    public Collection<Map<String, String>>read() {
        return  this.jdbcTemplate.query("SELECT * from MESSAGE", (rs, rowNum) -> {
            Map<String, String>map = new HashMap<>();
            map.put("id", rs.getString("ID"));
            map.put("message", rs.getString("MESSAGE"));
            return map;
        });
    }
}
