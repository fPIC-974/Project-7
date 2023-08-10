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
class RuleNameControllerIT {
    @Autowired
    public MockMvc mockMvc;

    @Test
    @WithMockUser(value = "fred")
    public void canGetRules() throws Exception {
        mockMvc.perform(get("/ruleName/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Logged in user: <b class=\"user\">fred</b>")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canGetAddForm() throws Exception {
        mockMvc.perform(get("/ruleName/add"))
                .andDo(print())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canAddRule() throws Exception {
        mockMvc.perform(post("/ruleName/validate")
                        .param("name", "Test name")
                        .param("description", "Test description")
                        .param("json", "Test json")
                        .param("template", "Test template")
                        .param("sqlStr", "Test sqlStr")
                        .param("sqlPart", "Test sqlPart"))
                .andDo(print())
                .andExpect(view().name("ruleName/list"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canNotAddRuleNullFields() throws Exception {
        mockMvc.perform(post("/ruleName/validate"))
                .andDo(print())
                .andExpect(view().name("ruleName/add"))
                .andExpect(content().string(containsString("Name is mandatory")))
                .andExpect(content().string(containsString("Description is mandatory")))
                .andExpect(content().string(containsString("JSON is mandatory")))
                .andExpect(content().string(containsString("Template is mandatory")))
                .andExpect(content().string(containsString("SQL String is mandatory")))
                .andExpect(content().string(containsString("SQL Part is mandatory")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canGetUpdateForm() throws Exception {
        mockMvc.perform(get("/ruleName/update/{id}", 502))
                .andDo(print())
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canUpdateRating() throws Exception {
        mockMvc.perform(post("/ruleName/update/{id}", 502)
                        .param("name", "Test name")
                        .param("description", "Test description")
                        .param("json", "Test json")
                        .param("template", "Test template")
                        .param("sqlStr", "Test sqlStr")
                        .param("sqlPart", "Test sqlPart"))
                .andDo(print())
                .andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canNotUpdateRuleNullFields() throws Exception {
        mockMvc.perform(post("/ruleName/update/{id}", 502))
                .andDo(print())
                .andExpect(view().name("ruleName/update"))
                .andExpect(content().string(containsString("Name is mandatory")))
                .andExpect(content().string(containsString("Description is mandatory")))
                .andExpect(content().string(containsString("JSON is mandatory")))
                .andExpect(content().string(containsString("Template is mandatory")))
                .andExpect(content().string(containsString("SQL String is mandatory")))
                .andExpect(content().string(containsString("SQL Part is mandatory")));
    }

    @Test
    @WithMockUser(value = "admin")
    @Transactional
    public void canDeleteRule() throws Exception {
        mockMvc.perform(get("/ruleName/delete/{id}", 502))
                .andDo(print())
                .andExpect(view().name("redirect:/ruleName/list"));
    }
}