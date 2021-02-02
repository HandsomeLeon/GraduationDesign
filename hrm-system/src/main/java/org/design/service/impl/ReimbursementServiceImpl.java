package org.design.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.design.mapper.ReimbursementMapper;
import org.design.model.Reimbursement;
import org.design.service.ReimbursementService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
    public void delete(Integer id) {
        Integer result = reimbursementMapper.delete(id);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public void update(Reimbursement model) {

    }

    @Override
    public Reimbursement get(Integer integer) {
        Reimbursement reimbursement = reimbursementMapper.get(integer);
        if (reimbursement == null) {
            throw new ServiceException("获取数据失败");
        }
        return reimbursement;
    }

    @Override
    public Map<String, Object> findAll(Integer page, Integer limit) {

        PageHelper.startPage(page, limit);
        List<Reimbursement> reimbursementList = reimbursementMapper.findAll();
        if (reimbursementList == null) {
            throw new ServiceException("获取数据失败");
        }
        PageInfo<Reimbursement> pageInfo = new PageInfo<>(reimbursementList);

        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", pageInfo.getTotal());
        data.put("data", reimbursementList);
        return data;
    }

    @Override
    public Map<String, Object> findExample(Reimbursement model, Integer page, Integer limit) {
        return null;
    }

    @Override
    public void updateState(String reimbursementId, Integer state) {
        Integer result = reimbursementMapper.updateState(reimbursementId, state.toString());
        if (result == null) {
            throw new ServiceException("操作失败");
        }
    }

    @Override
    public Map<String, Object> findByState(Integer page, Integer limit, Integer state) {
        PageHelper.startPage(page, limit);
        List<Reimbursement> reimbursementList = reimbursementMapper.findByState(state);
        if (reimbursementList == null) {
            throw new ServiceException("获取数据失败");
        }
        PageInfo<Reimbursement> pageInfo = new PageInfo<>(reimbursementList);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", pageInfo.getTotal());
        data.put("data", reimbursementList);
        return data;
    }
}
