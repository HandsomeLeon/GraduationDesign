package org.design.service;

import org.design.model.Reimbursement;

import java.util.Map;

public interface ReimbursementService extends BaseService<Reimbursement, Integer> {

    void updateState(String reimbursementId, Integer state);

    Map<String, Object> findByState(Integer page, Integer limit, Integer state);
}
