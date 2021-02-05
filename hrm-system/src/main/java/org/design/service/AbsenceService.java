package org.design.service;

import org.design.model.Absence;

import java.util.Map;

public interface AbsenceService extends BaseService<Absence, Integer> {
    Map<String, Object> findByState(Integer page, Integer limit, Integer i);

    void updateState(String applicationId, Integer state);
}
