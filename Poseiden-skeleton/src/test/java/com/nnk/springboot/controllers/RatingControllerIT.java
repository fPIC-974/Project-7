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
class RatingControllerIT {
    @Autowired
    public MockMvc mockMvc;

    @Test
    @WithMockUser(value = "fred")
    public void canGetRatings() throws Exception {
        mockMvc.perform(get("/rating/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Logged in user: <b class=\"user\">fred</b>")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canGetAddForm() throws Exception {
        mockMvc.perform(get("/rating/add"))
                .andDo(print())
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canAddRating() throws Exception {
        mockMvc.perform(post("/rating/validate")
                        .param("orderNumber", "10")
                        .param("moodysRating", "Test moodys")
                        .param("fitchRating", "Test fitch")
                        .param("sandPRating", "test sand"))
                .andDo(print())
                .andExpect(view().name("rating/list"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canNotAddCurveNullFields() throws Exception {
        mockMvc.perform(post("/rating/validate"))
                .andDo(print())
                .andExpect(view().name("rating/add"))
                .andExpect(content().string(containsString("Order Number is mandatory")))
                .andExpect(content().string(containsString("Moody&#39;s rating mandatory")))
                .andExpect(content().string(containsString("Fitch rating mandatory")))
                .andExpect(content().string(containsString("Standard &amp; Poor rating mandatory")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canGetUpdateForm() throws Exception {
        mockMvc.perform(get("/rating/update/{id}", 152))
                .andDo(print())
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canUpdateRating() throws Exception {
        mockMvc.perform(post("/rating/update/{id}", 152)
                        .param("orderNumber", "10")
                        .param("moodysRating", "Test moodys")
                        .param("fitchRating", "Test fitch")
                        .param("sandPRating", "test sand"))
                .andDo(print())
                .andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canNotUpdateRatingNullFields() throws Exception {
        mockMvc.perform(post("/rating/update/{id}", 152))
                .andDo(print())
                .andExpect(view().name("rating/update"))
                .andExpect(content().string(containsString("Order Number is mandatory")))
                .andExpect(content().string(containsString("Moody&#39;s rating mandatory")))
                .andExpect(content().string(containsString("Fitch rating mandatory")))
                .andExpect(content().string(containsString("Standard &amp; Poor rating mandatory")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canDeleteRating() throws Exception {
        mockMvc.perform(get("/rating/delete/{id}", 152))
                .andDo(print())
                .andExpect(view().name("redirect:/rating/list"));
    }
}