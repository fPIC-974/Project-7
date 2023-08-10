package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.BidList;
import jakarta.transaction.Transactional;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BidListControllerIT {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .defaultRequest(get("/"))
                .build();
    }

    @Test
    @WithMockUser(value = "fred")
    public void canGetBidLists() throws Exception {
        mockMvc.perform(get("/bidList/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Logged in user: <b class=\"user\">fred</b>")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canGetAddForm() throws Exception {
        mockMvc.perform(get("/bidList/add"))
                .andDo(print())
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canAddBidList() throws Exception {
        mockMvc.perform(post("/bidList/validate")
                        .param("account", "Test Account")
                        .param("type", "Test Type")
                        .param("bidQuantity", "10.0"))
                .andDo(print())
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canNotAddBidListNullFields() throws Exception {
        mockMvc.perform(post("/bidList/validate"))
                .andDo(print())
                .andExpect(view().name("bidList/add"))
                .andExpect(content().string(containsString("Account is mandatory")))
                .andExpect(content().string(containsString("Type is mandatory")))
                .andExpect(content().string(containsString("Bid quantity is mandatory")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canGetUpdateForm() throws Exception {
        mockMvc.perform(get("/bidList/update/{id}", 802))
                .andDo(print())
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canUpdateBidList() throws Exception {
        mockMvc.perform(post("/bidList/update/{id}", 802)
                        .param("account", "Test Account")
                        .param("type", "Test Type")
                        .param("bidQuantity", "10.0"))
                .andDo(print())
                .andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canNotUpdateBidListNullFields() throws Exception {
        mockMvc.perform(post("/bidList/update/{id}", 802))
                .andDo(print())
                .andExpect(view().name("bidList/update"))
                .andExpect(content().string(containsString("Account is mandatory")))
                .andExpect(content().string(containsString("Type is mandatory")))
                .andExpect(content().string(containsString("Bid quantity is mandatory")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canDeleteBidList() throws Exception {
        mockMvc.perform(get("/bidList/delete/{id}", 802))
                .andDo(print())
                .andExpect(view().name("redirect:/bidList/list"));
    }
}