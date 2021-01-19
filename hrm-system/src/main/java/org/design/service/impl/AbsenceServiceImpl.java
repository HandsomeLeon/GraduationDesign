package org.design.service.impl;

import org.design.model.Absence;
import org.design.service.AbsenceService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AbsenceServiceImpl implements AbsenceService {

    @Override
    public void save(Absence model) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Absence model) {

    }

    @Override
    public Absence get(Integer integer) {
        return null;
    }

    @Override
    public Map<String, Object> findAll(Integer page, Integer limit) {
        return null;
    }

    @Override
    public Map<String, Object> findExample(Absence model, Integer page, Integer limit) {
        return null;
    }
}
