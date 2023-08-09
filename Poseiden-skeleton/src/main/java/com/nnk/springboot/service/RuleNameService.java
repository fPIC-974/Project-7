package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.h2.bnf.Rule;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService implements IRuleNameService {
    private final RuleNameRepository ruleNameRepository;

    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }

    @Override
    public List<RuleName> getRuleNames() {
        return ruleNameRepository.findAll();
    }

    @Override
    public RuleName getRuleNameById(Integer id) {
        return ruleNameRepository.findById(id).orElseThrow(() -> new RuntimeException("RuleName not found"));
    }

    @Override
    public RuleName addRuleName(RuleName ruleName) {
        if (ruleName == null) {
            throw new IllegalArgumentException("Invalid RuleName");
        }

        return ruleNameRepository.save(ruleName);
    }

    @Override
    public RuleName updateRuleName(Integer id, RuleName ruleName) {
        if (ruleName == null) {
            throw new IllegalArgumentException("Invalid RuleName");
        }

        RuleName toUpdate = ruleNameRepository.findById(id).orElseThrow(() -> new RuntimeException("RuleName not found"));

        toUpdate.setName(ruleName.getName());
        toUpdate.setJson(ruleName.getJson());
        toUpdate.setDescription(ruleName.getDescription());
        toUpdate.setSqlStr(ruleName.getSqlStr());
        toUpdate.setSqlPart(ruleName.getSqlPart());
        toUpdate.setTemplate(ruleName.getTemplate());

        return ruleNameRepository.save(ruleName);
    }

    @Override
    public void deleteRuleName(Integer id) {
        if (!ruleNameRepository.existsById(id)) {
            throw new RuntimeException("RuleName not found");
        }

        ruleNameRepository.deleteById(id);
    }
}
