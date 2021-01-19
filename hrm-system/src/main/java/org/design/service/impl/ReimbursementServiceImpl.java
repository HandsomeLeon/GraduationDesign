package org.design.service.impl;

import org.design.mapper.ReimbursementMapper;
import org.design.model.Reimbursement;
import org.design.service.ReimbursementService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class ReimbursementServiceImpl implements ReimbursementService {

    @Resource
    private ReimbursementMapper reimbursementMapper;

    @Override
    public void save(Reimbursement model) {
        Integer result = reimbursementMapper.save(model);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Reimbursement model) {

    }

    @Override
    public Reimbursement get(Integer integer) {
        return null;
    }

    @Override
    public Map<String, Object> findAll(Integer page, Integer limit) {
        return null;
    }

    @Override
    public Map<String, Object> findExample(Reimbursement model, Integer page, Integer limit) {
        return null;
    }
}
