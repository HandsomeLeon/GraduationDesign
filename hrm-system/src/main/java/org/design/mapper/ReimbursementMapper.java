package org.design.mapper;

import org.apache.ibatis.annotations.Param;
import org.design.model.Reimbursement;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ReimbursementMapper {

    Integer save(Reimbursement reimbursement);

    List<Reimbursement> findAll();

    Integer delete(Integer id);

    Reimbursement get(Integer id);

    Integer updateState(@Param("state") String state, @Param("id") String id);

    List<Reimbursement> findByState(Integer state);

}
