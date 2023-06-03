//package com.example.weather_service.controller;
//
//
//
//import com.example.weather_service.dto.request.UserLoginDTO;
//import com.example.weather_service.dto.request.UserRequestDTO;
//import com.example.weather_service.entity.Enum.RoleEnum;
//import com.example.weather_service.entity.UserEntity;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.testcontainers.lifecycle.Startables;
//
//import java.util.Collections;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//class AuthControllerTest extends BaseTest {
//    private final static String PATH_REGISTER = "/api/auth/register";
//    private final static String PATH_LOGIN = "/api/auth/login";
//    private final static String PATH_TOKEN = "/api/auth/token";
//
//    @BeforeAll
//    static void beforeAll() {
//        Startables.deepStart(postgres).join();
//    }
//
//    @AfterEach
//    void afterAll() {
//        userRepository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("registerUserShouldReturnOKStatus")
//    public void register() throws Exception {
//        callAdd().andExpect(status().isCreated());
//    }
//
//    @Test
//    @DisplayName("registerUserShouldThrowUserExist")
//    public void registerThrow() throws Exception {
//        callAdd();
//        callAdd().andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("addAdminShouldThrowCanNotBeAdmin")
//    public void registerAdminThrow() throws Exception {
//        callAdminAdd().andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @DisplayName("loginWithValidCredentialsShouldReturnToken")
//    public void login() throws Exception {
//        callLogin().andExpect(status().isOk());
//    }
//
//    private ResultActions callLogin() throws Exception {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setEmail("abdulatifjalolov6004273@gmail.com");
//        userEntity.setPassword(passwordEncoder.encode("12345678"));
//        userEntity.setPhoneNumber("+998936004273");
//        userEntity.setRoleEnumList(Collections.singletonList(RoleEnum.USER));
//        userRepository.save(userEntity);
//
//        UserLoginDTO userLoginDTO = new UserLoginDTO();
//        userLoginDTO.setEmail("abdulatifjalolov6004273@gmail.com");
//        userLoginDTO.setPassword("12345678");
//
//        final MockHttpServletRequestBuilder request = post(PATH_LOGIN)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userLoginDTO));
//
//        return mockMvc.perform(request);
//    }
//
//    private ResultActions callAdminAdd() throws Exception {
//        UserRequestDTO userRequestDTO = new UserRequestDTO();
//        userRequestDTO.setEmail("abdulatifjalolov6004273@gmail.com");
//        userRequestDTO.setPassword("123456789");
//        userRequestDTO.setRoles(Collections.singletonList(RoleEnum.ADMIN));
//
//        final MockHttpServletRequestBuilder request = post(PATH_REGISTER)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userRequestDTO));
//
//        return mockMvc.perform(request);
//    }
//
//    private ResultActions callAdd() throws Exception {
//        UserRequestDTO userRequestDTO = new UserRequestDTO();
//        userRequestDTO.setEmail("abdulatifjalolov6004273@gmail.com");
//        userRequestDTO.setPassword("123456789");
//        userRequestDTO.setRoles(Collections.singletonList(RoleEnum.USER));
//
//        final MockHttpServletRequestBuilder request = post(PATH_REGISTER)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(userRequestDTO));
//
//        return mockMvc.perform(request);
//    }
//}