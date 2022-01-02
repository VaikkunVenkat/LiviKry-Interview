package com.example.backend;
import com.example.backend.controller.ServicesController;
import com.example.backend.model.Services;
import com.example.backend.repository.ServicesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.net.URL;
import java.security.Provider.Service;
import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ServicesControllerStandaloneTest {

    private MockMvc mvc;


    @Mock
    private ServicesRepository servicesRepository;

    @Mock
    private Services services;

    @InjectMocks
    private ServicesController servicesController;

    // This object will be magically initialized by the initFields method below.
    private JacksonTester<Services> jsonServices;

    @BeforeEach
    public void setup() {
        // We would need this line if we would not use the MockitoExtension
        // MockitoAnnotations.initMocks(this);
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(servicesController).build();
    }

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        // given
        Services service = new Services(1, "name", new URL("http://goodapi.com"), "good");
        given(servicesRepository.findById(1))
          .willReturn(Optional.of(service));

        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/services/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
          jsonServices.write(service).getJson()
        );
    }

    @Test
    public void canRetrieveByIdWhenDoesNotExist() throws Exception {
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/services/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).contains("No data found");
    }

   @Test
    public void canRetrieveAllServices() throws Exception {
      Services service1 = new Services(1, "name", new URL("http://goodapi.com"), "good");
      Services service2 = new Services(2, "happy", new URL("http://happyAPI.com"), "good");
      given(servicesRepository.findAll())
        .willReturn(new ArrayList<Services>(Arrays.asList(service1, service2)));
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/services/")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo("application/json");
        assertThat(new ObjectMapper().readValue(response.getContentAsString(), ArrayList.class).size()).isEqualTo(2);
    }

    @Test
    public void canCreateANewService() throws Exception {
        // when
        Services service = new Services(1, "name", new URL("http://goodapi.com"), "good");
        MockHttpServletResponse response = mvc.perform(
                post("/api/services").contentType(MediaType.APPLICATION_JSON).content(
                        jsonServices.write(service).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }

    @Test
    public void canDeleteAService() throws Exception {
        // given
        Services service = new Services(1, "name", new URL("http://goodapi.com"), "good");
        given(servicesRepository.findById(1))
          .willReturn(Optional.of(service));

        // when
        MockHttpServletResponse response = mvc.perform(
                delete("/api/services/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        assertThat(response.getContentAsString()).contains("services has been deleted successfully.");
    }
  
    @Test
    public void canUpdateAService() throws Exception {

      Services service = new Services(1, "name", new URL("http://goodapi.com"), "good");
      Services serviceNew = new Services(1, "updatedName", new URL("http://updatedGoodAPI.com"), "good");
      given(servicesRepository.findById(1))
        .willReturn(Optional.of(service));
        // when
        MockHttpServletResponse response = mvc.perform(
                put("/api/services/1").contentType(MediaType.APPLICATION_JSON).content(
                        jsonServices.write(serviceNew).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    }
}
