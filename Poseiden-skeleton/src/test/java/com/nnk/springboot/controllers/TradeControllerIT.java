package com.nnk.springboot.controllers;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TradeControllerIT {
    @Autowired
    public MockMvc mockMvc;

    @Test
    @WithMockUser(value = "fred")
    public void canGetTrades() throws Exception {
        mockMvc.perform(get("/trade/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Logged in user: <b class=\"user\">fred</b>")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canGetAddForm() throws Exception {
        mockMvc.perform(get("/trade/add"))
                .andDo(print())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canAddTrade() throws Exception {
        mockMvc.perform(post("/trade/validate")
                        .param("account", "Test account")
                        .param("type", "Test type")
                        .param("buyQuantity", "10.0"))
                .andDo(print())
                .andExpect(view().name("trade/list"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canNotAddTradeNullFields() throws Exception {
        mockMvc.perform(post("/trade/validate"))
                .andDo(print())
                .andExpect(view().name("trade/add"))
                .andExpect(content().string(containsString("Account is mandatory")))
                .andExpect(content().string(containsString("Type is mandatory")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canGetUpdateForm() throws Exception {
        mockMvc.perform(get("/trade/update/{id}", 552))
                .andDo(print())
                .andExpect(view().name("trade/update"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canUpdateTrade() throws Exception {
        mockMvc.perform(post("/trade/update/{id}", 552)
                        .param("account", "Test account")
                        .param("type", "Test type")
                        .param("buyQuantity", "10.0"))
                .andDo(print())
                .andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canNotUpdateTradeNullFields() throws Exception {
        mockMvc.perform(post("/trade/update/{id}", 552))
                .andDo(print())
                .andExpect(view().name("trade/update"))
                .andExpect(content().string(containsString("Account is mandatory")))
                .andExpect(content().string(containsString("Type is mandatory")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canDeleteTrade() throws Exception {
        mockMvc.perform(get("/trade/delete/{id}", 552))
                .andDo(print())
                .andExpect(view().name("redirect:/trade/list"));
    }
}