package com.nnk.springboot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CurveControllerIT {
    @Autowired
    public MockMvc mockMvc;

    @Test
    @WithMockUser(value = "fred")
    public void canGetCurves() throws Exception {
        mockMvc.perform(get("/curvePoint/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Logged in user: <b class=\"user\">fred</b>")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canGetAddForm() throws Exception {
        mockMvc.perform(get("/curvePoint/add"))
                .andDo(print())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canAddCurve() throws Exception {
        mockMvc.perform(post("/curvePoint/validate")
                        .param("curveId", "10")
                        .param("term", "10.0")
                        .param("value", "10.0"))
                .andDo(print())
                .andExpect(view().name("curvePoint/list"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canNotAddCurveNullFields() throws Exception {
        mockMvc.perform(post("/curvePoint/validate"))
                .andDo(print())
                .andExpect(view().name("curvePoint/add"))
                .andExpect(content().string(containsString("Curve Id is mandatory")))
                .andExpect(content().string(containsString("Term is mandatory")))
                .andExpect(content().string(containsString("Value is mandatory")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canGetUpdateForm() throws Exception {
        mockMvc.perform(get("/curvePoint/update/{id}", 602))
                .andDo(print())
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canUpdateBidList() throws Exception {
        mockMvc.perform(post("/curvePoint/update/{id}", 602)
                        .param("curveId", "10")
                        .param("term", "10.0")
                        .param("value", "10.0"))
                .andDo(print())
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canNotUpdateBidListNullFields() throws Exception {
        mockMvc.perform(post("/curvePoint/update/{id}", 602))
                .andDo(print())
                .andExpect(view().name("curvePoint/update"))
                .andExpect(content().string(containsString("Curve Id is mandatory")))
                .andExpect(content().string(containsString("Term is mandatory")))
                .andExpect(content().string(containsString("Value is mandatory")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canDeleteBidList() throws Exception {
        mockMvc.perform(get("/curvePoint/delete/{id}", 602))
                .andDo(print())
                .andExpect(view().name("redirect:/curvePoint/list"));
    }
}