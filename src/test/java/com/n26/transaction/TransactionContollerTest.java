package com.n26.transaction;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.transactions.model.Transaction;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionContollerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Test
    public void when_empty_request_body() throws JsonProcessingException, Exception {
        ResultActions actions = mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(new Transaction())));
        actions.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void when_invalid_json_value_format() throws JsonProcessingException, Exception {
        Map<String, String> request = new HashMap<>();
        request.put("amount", "102.00");
        request.put("timestamp", "2018-08-12");
        ResultActions actions = mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(request)));
        actions.andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void when_invalid_json_format() throws JsonProcessingException, Exception {
        ResultActions actions =
                mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON).content("Hello World!!"));
        actions.andExpect(status().isBadRequest());
    }

    @Test
    public void when_transaction_timestamp_is_future() throws JsonProcessingException, Exception {
        Map<String, String> request = new HashMap<>();
        request.put("amount", "102.00");
        request.put("timestamp", DateTimeFormatter.ISO_INSTANT.format(Instant.now().plusSeconds(180)));
        ResultActions actions = mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(request)));
        actions.andExpect(status().isUnprocessableEntity());
    }
    @Test
    public void when_created_successfully() throws JsonProcessingException, Exception
    {
        Map<String, String> request = new HashMap<>();
        request.put("amount", "102.00");
        request.put("timestamp", DateTimeFormatter.ISO_INSTANT.format(Instant.now().minusSeconds(30)));
        ResultActions actions = mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(request)));
        actions.andExpect(status().isCreated());
    }
    
    @Test
    public void when_transaction_older_then_one_minute() throws JsonProcessingException, Exception
    {
        Map<String, String> request = new HashMap<>();
        request.put("amount", "102.00");
        request.put("timestamp", DateTimeFormatter.ISO_INSTANT.format(Instant.now().minusSeconds(61)));
        ResultActions actions = mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
                .content(MAPPER.writeValueAsString(request)));
        actions.andExpect(status().isNoContent());
    }

}
