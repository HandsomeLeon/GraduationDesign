package org.design.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.design.mapper.AbsenceMapper;
import org.design.model.Absence;
import org.design.model.Reimbursement;
import org.design.service.AbsenceService;
import org.design.utils.ServiceException;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AbsenceServiceImpl implements AbsenceService {

    @Resource
    private AbsenceMapper absenceMapper;

    @Override
    public void save(Absence model) {
        Integer result = absenceMapper.insert(model);

        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public void delete(Integer id) {
        Integer result = absenceMapper.deleteByPrimaryKey(id);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public void update(Absence model) {
        Integer result = absenceMapper.updateByPrimaryKey(model);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }

    @Override
    public Absence get(Integer id) {
        Absence absence = absenceMapper.selectByPrimaryKey(id);
        if (absence == null) {
            throw new ServiceException("获取数据失败");
        }
        return absence;
    }

    @Override
    public Map<String, Object> findAll(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Absence> absenceList = absenceMapper.selectAll();
        if (absenceList == null) {
            throw new ServiceException("获取数据失败");
        }
        PageInfo<Absence> pageInfo = new PageInfo<>(absenceList);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", pageInfo.getTotal());
        data.put("data", absenceList);
        return data;
    }

    @Override
    public Map<String, Object> findExample(Absence model, Integer page, Integer limit) {
        return null;
    }

    @Override
    public Map<String, Object> findByState(Integer page, Integer limit, Integer state) {
        PageHelper.startPage(page, limit);
        Example example = new Example(Absence.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("state", state);
        List<Absence> absenceList = absenceMapper.selectByExample(example);
        if (absenceList == null) {
            throw new ServiceException("获取数据失败");
        }
        PageInfo<Absence> pageInfo = new PageInfo<>(absenceList);
        Map<String, Object> data = new HashMap<>();
        data.put("code", 0);
        data.put("msg", "");
        data.put("count", pageInfo.getTotal());
        data.put("data", absenceList);
        return data;
    }

    @Override
    public void updateState(String applicationId, Integer state) {
        Absence absence = new Absence();
        absence.setId(Integer.parseInt(applicationId));
        absence.setState(state);
        Integer result = absenceMapper.updateByPrimaryKeySelective(absence);
        if (result <= 0) {
            throw new ServiceException("操作数据库失败");
        }
    }
}
