//package com.example.weather_service.controller;
//
//import com.example.weather_service.repository.CityRepository;
//import com.example.weather_service.repository.UserRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.utility.DockerImageName;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//@Testcontainers
//public abstract class BaseTest {
//
//    @Autowired
//    protected MockMvc mockMvc;
//    @Autowired
//    protected UserRepository userRepository;
//    @Autowired
//    protected CityRepository cityRepository;
//    @Autowired
//    protected PasswordEncoder passwordEncoder;
//    @Autowired
//    protected ObjectMapper objectMapper;
//
//    protected static final PostgreSQLContainer<?> postgres;
//
//    static {
//        postgres = (PostgreSQLContainer) new PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
//                .withInitScript("sql/table-init.sql")
//                .withExposedPorts(5432);
//        postgres.withReuse(true);
//    }
//
//    @DynamicPropertySource
//    static void setUpPostgresConnectionProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//        registry.add("spring.datasource.database", postgres::getDatabaseName);
//    }
//}