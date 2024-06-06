package com.acidonper.myapp.web;


import com.acidonper.myapp.dtos.JumpDto;
import com.acidonper.myapp.entities.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JumpsControllerTest {

    @Autowired
    private MockMvc mvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Mock
    HttpURLConnection connection;

    @Mock
    HttpURLConnection connectionMulti;

    @Spy
    JumpsController app;

    @Spy
    JumpsController appMulti;

    @Test
    public void getJump() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/jump").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"message\":\"/jump - Greetings from Spring Boot!\",\"code\":200}")));
    }
    @Test
    public void postJumpSingle() throws Exception {

        // Variables
        String url = "http://localhost:8443";
        URL urlTest = new URL(url + "/");

        // Generate Expected response mocking connection
        Response responseExpected = new Response("/jump - Greetings from Spring Boot!",200);
        InputStream input = new ByteArrayInputStream(new Gson().toJson(responseExpected).getBytes("UTF-8"));
        Mockito.doReturn(connection).when(app).create(urlTest);
        Mockito.doReturn(input).when(connection).getInputStream();

        // Make call
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("X-B3-TraceId", url);
        map.add("X-B3-ParentSpanId", "value2");
        map.add("X-B3-SpanId", "value3");
        map.add("X-B3-Sampled", "value3");
        JumpDto jumpDto = new JumpDto("test","/", "/jump", new String[] {url});

        Response status = app.jumpPost(map, jumpDto);

        // Expect value
        Assertions.assertEquals(responseExpected.toString(), status.toString());
    }

    @Test
    public void postJumpMulti() throws Exception {

        // Variables
        String url1 = "http://localhost:8444";
        String url2 = "http://localhost:8442";

        URL urlTest = new URL(url1 + "/jump");

        // Generate Expected response mocking connection
        Response responseExpected = new Response("/jump - Greetings from Spring Boot!",200);
        InputStream input = new ByteArrayInputStream(new Gson().toJson(responseExpected).getBytes("UTF-8"));
        JumpDto jumpDtoMultiExpected = new JumpDto("test","/", "/jump", new String[] {url2});
        OutputStream output = new ByteArrayOutputStream(new Gson().toJson(jumpDtoMultiExpected).length());
        Mockito.doReturn(connectionMulti).when(appMulti).create(urlTest);
        Mockito.doReturn(output).when(connectionMulti).getOutputStream();
        Mockito.doReturn(input).when(connectionMulti).getInputStream();

        // Make call
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("X-B3-TraceId", "value1");
        map.add("X-B3-ParentSpanId", "value2");
        map.add("X-B3-SpanId", "value3");
        map.add("X-B3-Sampled", "value3");
        JumpDto jumpDtoMulti = new JumpDto("test","/", "/jump", new String[] {url1, url2});
        
        Response status = appMulti.jumpPost(map, jumpDtoMulti);

        // Expect value
        Assertions.assertEquals(responseExpected.toString(), status.toString());
    }
}