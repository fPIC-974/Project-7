package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.h2.bnf.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RuleNameServiceTest {
    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;

    @Test
    public void getValidListOfRuleNames() {
        List<RuleName> ruleNames = new ArrayList<>();
        ruleNames.add(new RuleName());
        ruleNames.add(new RuleName());

        when(ruleNameRepository.findAll()).thenReturn(ruleNames);

        List<RuleName> toCheck = ruleNameService.getRuleNames();

        assertEquals(2, toCheck.size());
    }

    @Test
    public void getNullListOfRuleNames() {
        when(ruleNameRepository.findAll()).thenReturn(new ArrayList<>());

        List<RuleName> toCheck = ruleNameService.getRuleNames();

        assertTrue(toCheck.isEmpty());
    }

    @Test
    public void getValidRuleName() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(new RuleName()));

        RuleName toCheck = ruleNameService.getRuleNameById(1);

        assertNotNull(toCheck);
        assertDoesNotThrow(() -> {
        });
    }

    @Test
    public void cantGetRuleNameNotFound() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> ruleNameService.getRuleNameById(1));

        assertTrue(runtimeException.getMessage().contains("RuleName not found"));
    }

    @Test
    public void addValidRuleName() {
        RuleName ruleName = new RuleName("Test", "Test", "Test", "Test", "Test", "Test");
        ruleName.setId(1);

        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName toCheck = ruleNameService.addRuleName(ruleName);

        assertDoesNotThrow(() -> {});
        assertEquals("Test", toCheck.getName());
        assertEquals("Test", toCheck.getDescription());
        assertEquals("Test", toCheck.getJson());
        assertEquals("Test", toCheck.getTemplate());
        assertEquals("Test", toCheck.getSqlPart());
        assertEquals("Test", toCheck.getSqlStr());
    }

    @Test
    public void cantAddRuleNameNull() {

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> ruleNameService.addRuleName(null));

        assertTrue(runtimeException.getMessage().contains("Invalid RuleName"));
    }

    @Test
    public void updateValidBidList() {
        RuleName toUpdate = new RuleName("Test", "Test", "Test", "Test", "Test", "Test");
        toUpdate.setId(1);

        RuleName ruleName = new RuleName("Updated", "Updated", "Updated", "Updated", "Updated", "Updated");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(toUpdate));
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(ruleName);

        RuleName toCheck = ruleNameService.updateRuleName(1, ruleName);

        assertDoesNotThrow(() -> {});
        assertEquals("Updated", toCheck.getName());
        assertEquals("Updated", toCheck.getDescription());
        assertEquals("Updated", toCheck.getJson());
        assertEquals("Updated", toCheck.getTemplate());
        assertEquals("Updated", toCheck.getSqlPart());
        assertEquals("Updated", toCheck.getSqlStr());
    }

    @Test
    public void cantUpdateRuleNameNull() {
        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> ruleNameService.updateRuleName(1, null));

        assertTrue(runtimeException.getMessage().contains("Invalid RuleName"));
    }

    @Test
    public void cantUpdateRuleNameNotFound() {
        RuleName ruleName = new RuleName("Test", "Test", "Test", "Test", "Test", "Test");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> ruleNameService.updateRuleName(1, ruleName));

        assertTrue(runtimeException.getMessage().contains("RuleName not found"));
    }

    @Test
    public void deleteValidRuleNameById() {
        when(ruleNameRepository.existsById(anyInt())).thenReturn(true);

        assertDoesNotThrow(() -> ruleNameService.deleteRuleName(1));
    }

    @Test
    public void cantDeleteRuleNameByIdNotFound() {
        when(ruleNameRepository.existsById(anyInt())).thenReturn(false);

        RuntimeException runtimeException = assertThrows(RuntimeException.class,
                () -> ruleNameService.deleteRuleName(1));

        assertTrue(runtimeException.getMessage().contains("RuleName not found"));
    }
}