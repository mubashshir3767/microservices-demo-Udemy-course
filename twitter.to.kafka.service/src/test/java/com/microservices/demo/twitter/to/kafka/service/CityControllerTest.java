///*
//package com.example.weather_service.controller;
//
//
//import com.example.weather_service.dto.request.CityRequestDTO;
//import com.example.weather_service.entity.CityEntity;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.testcontainers.lifecycle.Startables;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//class CityControllerTest extends BaseTest {
//
//    private static final String PATH_CITY = "/api/city/";
//
//    @BeforeAll
//    static void beforeAll() {
//        Startables.deepStart(postgres).join();
//    }
//
//
//    @AfterEach
//    void afterAll() {
//        cityRepository.deleteAll();
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    @DisplayName("shouldGetCityListOkStatus")
//    void getList() throws Exception {
//        addMockCity();
//        callList().andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    @DisplayName("shouldAddCityCreateStatus")
//    void addCity() throws Exception {
//        callAdd().andExpect(status().isCreated());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    @DisplayName("shouldAddCityBadRequestStatus")
//    void addCityThrow() throws Exception {
//        callAdd();
//        callAdd().andExpect(status().isBadRequest());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    @DisplayName("shouldUpdateWeatherReturnedOkStatus")
//    void updateWeather() throws Exception {
//        CityEntity cityEntity = addMockCity();
//        callUpdateWeather(cityEntity.getId()).andExpect(status().isOk());
//    }
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    @DisplayName("shouldUpdateWeatherReturnedNotFoundStatus")
//    void updateWeatherThrow() throws Exception {
//        callUpdateWeather(5L).andExpect(status().isNotFound());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    @DisplayName("shouldUpdateCityCreatedStatus")
//    void updateCity() throws Exception {
//        CityEntity Tashkent = addMockCity();
//        callUpdate(Tashkent.getId()).andExpect(status().isCreated());
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    @DisplayName("shouldUpdateCityNotFoundStatus")
//    void updateCityThrow() throws Exception {
//        callUpdate(3L).andExpect(status().isNotFound());
//    }
//    private ResultActions callUpdate(Long id) throws Exception {
//        CityRequestDTO cityRequestDTO = new CityRequestDTO();
//        cityRequestDTO.setTempC(10.0);
//        cityRequestDTO.setVisible(false);
//        cityRequestDTO.setName("Tokio");
//
//        final MockHttpServletRequestBuilder request =
//                put(PATH_CITY + "{id}",id)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(cityRequestDTO));
//        return mockMvc.perform(request);
//    }
//
//    private ResultActions callUpdateWeather(Long id) throws Exception {
//        Double tempC = 30.0;
//        final MockHttpServletRequestBuilder request =
//                put(PATH_CITY + "update-weather/{id}",id)
//                        .param("tempC", tempC.toString());
//        return mockMvc.perform(request);
//    }
//
//
//
//    private ResultActions callAdd() throws Exception {
//        CityRequestDTO cityRequestDTO = new CityRequestDTO();
//        cityRequestDTO.setName("Tashkent");
//        cityRequestDTO.setVisible(true);
//        cityRequestDTO.setTempC(20.0);
//
//        final MockHttpServletRequestBuilder request =
//                post(PATH_CITY)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(cityRequestDTO));
//        return mockMvc.perform(request);
//    }
//
//
//    private ResultActions callList() throws Exception {
//        final MockHttpServletRequestBuilder request =
//                get(PATH_CITY);
//        return mockMvc.perform(request);
//    }
//
//    private CityEntity addMockCity() {
//        CityEntity city = new CityEntity();
//        city.setName("Tashkent");
//        city.setTempC(20.0);
//        city.setVisible(true);
//        return cityRepository.save(city);
//    }
//}
//*/
