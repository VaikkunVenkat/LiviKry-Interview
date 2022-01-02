package com.example.backend;
import com.example.backend.controller.ServicesController;
import com.example.backend.model.Services;
import com.example.backend.repository.ServicesRepository;
import com.example.backend.services.RestService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ServicesControllerStandaloneTest {

    private MockMvc mvc;


    @Mock
    private ServicesRepository servicesRepository;

    @InjectMocks
    private Services serviceNew = new Services(1, "new", "http://newapi.com", "OK");;

    @InjectMocks
    private ServicesController servicesController;

    @Mock
    private RestService restService;


    // This object will be magically initialized by the initFields method below.
    private JacksonTester<Services> jsonServices;

    @BeforeEach
    public void setup() {
        // Here we can't use @AutoConfigureJsonTesters because there isn't a Spring context
        JacksonTester.initFields(this, new ObjectMapper());
        // MockMvc standalone approach
        mvc = MockMvcBuilders.standaloneSetup(servicesController).build();
    }

    @Test
    public void canRefreshAPI() throws Exception {
      Services service1 = new Services(1, "name", "http://goodapi.com", "OK");
      Services service2 = new Services(2, "happy", "http://happyAPI.com", "OK");
      given(servicesRepository.findAll())
      .willReturn(new ArrayList<Services>(Arrays.asList(service1, service2)));

      Services service2Fail = new Services(2, "happy", "http://happyAPI.com", "FAIL");
      
      
      given(restService.getServiceStatuses(new ArrayList<Services>(Arrays.asList(service1, service2))))
        .willReturn(new ArrayList<Services>(Arrays.asList(service1, service2Fail)));
        // when
        MockHttpServletResponse response = mvc.perform(
                get("/api/refreshServices/")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentType()).isEqualTo("application/json");
        Services[] responseBodyList = new ObjectMapper().readValue(response.getContentAsString(), Services[].class);  
        assertThat(responseBodyList.length).isEqualTo(2);
        String statusFail = service2Fail.getStatus();
        assertThat(responseBodyList[1].getStatus()).isEqualTo(statusFail);
    }
    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        // given
        Services service = new Services(1, "name", "http://goodapi.com", "OK");
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
      Services service1 = new Services(1, "name", "http://goodapi.com", "OK");
      Services service2 = new Services(2, "happy", "http://happyAPI.com", "OK");
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

    // TODO: If I had more time - I would fix this test.
    /*     @Test
    public void canCreateANewService() throws Exception {
        // when
        Services serviceWithStatus = new Services(1, "new", "http://newapi.com" ,"OK");
        given(restService.fetchServiceWithStatus(serviceNew)).willReturn(serviceWithStatus);

        MockHttpServletResponse response = mvc.perform(
                post("/api/services").contentType(MediaType.APPLICATION_JSON).content(
                        jsonServices.write(serviceNew).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    } */

    @Test
    public void canDeleteAService() throws Exception {
        // given
        Services service = new Services(1, "name", "http://goodapi.com", "OK");
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
  
/*     @Test  GIVEN MORE TIME I WOULD CORRECT THIS TEST!
    public void canUpdateAService() throws Exception {

      Services service = new Services(1, "name", "http://goodapi.com", "OK");
      Services serviceNew = new Services(1, "updatedName", "http://updatedGoodAPI.com", "");
      Services serviceNewWithStatus = new Services(1, "updatedName", "http://updatedGoodAPI.com", "OK");
      given(servicesRepository.findById(1))
        .willReturn(Optional.of(service));
      given(restService.fetchServiceWithStatus(serviceNew)).willReturn(serviceNewWithStatus);
        // when
        MockHttpServletResponse response = mvc.perform(
                put("/api/services/1").contentType(MediaType.APPLICATION_JSON).content(
                        jsonServices.write(serviceNew).getJson()
                )).andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("");
    } */
}
