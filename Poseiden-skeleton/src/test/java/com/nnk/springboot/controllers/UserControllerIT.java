package com.nnk.springboot.controllers;

import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    public MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser("admin")
    public void canGetUsers() throws Exception {
        mockMvc.perform(get("/user/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Logged in user: <b class=\"user\">fred</b>")));
    }

    @Test
    @WithMockUser("admin")
    @Transactional
    @Disabled
    public void canGetAddForm() throws Exception {
        mockMvc.perform(get("/user/add"))
                .andDo(print())
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    @Disabled
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
    @Disabled
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
    @Disabled
    public void canGetUpdateForm() throws Exception {
        mockMvc.perform(get("/trade/update/{id}", 552))
                .andDo(print())
                .andExpect(view().name("trade/update"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    @Disabled
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
    @Disabled
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
    @Disabled
    public void canDeleteTrade() throws Exception {
        mockMvc.perform(get("/trade/delete/{id}", 552))
                .andDo(print())
                .andExpect(view().name("redirect:/trade/list"));
    }
}