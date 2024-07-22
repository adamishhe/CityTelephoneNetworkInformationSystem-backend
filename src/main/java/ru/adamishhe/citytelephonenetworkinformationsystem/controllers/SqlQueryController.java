package ru.adamishhe.citytelephonenetworkinformationsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class SqlQueryController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/execute-sql")
    public List<Map<String, Object>> executeSql(@RequestBody Map<String, String> request) {
        String query = request.get("query");
        return jdbcTemplate.queryForList(query);
    }
}
