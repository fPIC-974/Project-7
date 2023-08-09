package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;

import java.util.List;

public interface IRuleNameService {
    List<RuleName> getRuleNames();

    RuleName getRuleNameById(Integer id);

    RuleName addRuleName(RuleName ruleName);

    RuleName updateRuleName(Integer id, RuleName ruleName);

    void deleteRuleName(Integer id);
}
