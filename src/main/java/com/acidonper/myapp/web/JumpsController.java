package com.acidonper.myapp.web;

import com.acidonper.myapp.dtos.JumpDto;
import com.acidonper.myapp.entities.Jump;
import com.acidonper.myapp.entities.Response;
import com.acidonper.myapp.mappers.JumpMapper;
import com.google.gson.Gson;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class JumpsController {

    @GetMapping("/jump")
    Response jumpGet(){
        System.out.println("Received GET /jump");
        Response response = new Response("/jump - Greetings from Spring Boot!",200 );
        System.out.println("Sending GET Response /jump - " + response.toString());
        return response;
    }

    @PostMapping("/jump")
    Response jumpPost(@Valid @RequestBody @RequestHeader MultiValueMap<String, String> headers, JumpDto newJump) throws IOException {

        Jump jump = JumpMapper.INSTANCE.jumpDTOtoJump(newJump);
        Response response = new Response("/jump - Farewell from Spring Boot! Error by default",400 );

        if (jump.jumps.length == 0) {
            response.message = "/jump - Farewell from Spring Boot! Bad Request!";
            response.code = 400;
        } else if (jump.jumps.length == 1) {
            System.out.println("Received POST /jump with 1 JUMP.jumps -" + newJump.toStringCustom());

            // Perform GET connection to the last jump
            URL url = new URL(jump.jumps[0] + jump.last_path);

            // Make connection
            HttpURLConnection con = create(url);

            // Perform GET
            con.setRequestMethod("GET");

            // handler Headers
            PropageteXb3(con, headers);

            // Handle Response
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {

                StringBuilder getResponse = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    getResponse.append(responseLine.trim());
                }

                // Generate Response
                response = new Gson().fromJson(getResponse.toString(), Response.class);
            } catch (Exception error) {
                response.message = "/jump - Farewell from Spring Boot! Error jumping";
                response.code = 400;
            }
        } else if (jump.jumps.length > 1) {
            System.out.println("Received POST /jump with multi JUMP.jumps -" + newJump.toStringCustom());

            // Perform GET connection to the last jump
            URL url = new URL(jump.jumps[0] + jump.jump_path);

            // Make connection
            HttpURLConnection con = create(url);

            // Generate the new Jump Object first position of jumps array
            String[] jumpsPost = Arrays.copyOfRange(jump.jumps, 1, jump.jumps.length);
            Jump jumpPost = jump;
            jumpPost.jumps = jumpsPost;

            // handler Headers
            PropageteXb3(con, headers);

            // Perform POST
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = new Gson().toJson(jumpPost);
            try (OutputStream os = con.getOutputStream()) {
                System.out.println(con.getOutputStream());
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Handle Response
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder getResponse = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    getResponse.append(responseLine.trim());
                }

                // Generate Response
                response = new Gson().fromJson(getResponse.toString(), Response.class);
            } catch (Exception error) {
                response.message = "/jump - Farewell from Spring Boot! Error jumping";
                response.code = 400;
            }
        }

        // Send Response
        System.out.println("Sending POST Response /jump - " + response.toString());
        return response;
    }

    HttpURLConnection create(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    /**
     * PropageteXb3
     */
    void PropageteXb3(HttpURLConnection con, MultiValueMap<String, String> headers) {
        headers.forEach((key, value) -> {
            System.out.println(key);
            System.out.println(value);
            if (key.contains("X-B3-")){
              con.setRequestProperty(key, value.stream().collect(Collectors.joining("|")));
            };
        });
    }

}