package org.design.mapper;

import org.design.model.Reimbursement;

import java.util.List;

public interface ReimbursementMapper {

    Integer save(Reimbursement reimbursement);

    List<Reimbursement> findAll();

    Integer delete(Integer id);
}
