package com.bky.service.impl;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bky.dao.informationMapper;
import com.bky.model.Classes;
import com.bky.model.Dormitory;
import com.bky.model.Score;
import com.bky.model.Student;
import com.bky.service.BaseService;

@Service("baseService")
public class BaseServiceImpl implements BaseService {
	
	@Autowired
    private informationMapper infoMapper;

	@Override
	public List<Map<String, Object>> getAll(String department,String stuNumber,int page,int rows) {
		List<Map<String, Object>> liRet;
		liRet = infoMapper.getAll(department,stuNumber,page,rows);
		return liRet;
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return infoMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Map<String, Object>> getAll1(String department,int page,int rows) {
		return infoMapper.getAll1(department,page,rows);
	}

	@Override
	public int deleteByPrimaryKey1(Integer id) {
		return infoMapper.deleteByPrimaryKey1(id);
	}

	@Override
	public List<Map<String, Object>> getAll2(String stuNumber, String subject,int page,int rows) {
		return infoMapper.getAll2(stuNumber,subject,page,rows);
	}

	@Override
	public int deleteByPrimaryKey2(Integer id) {
		return infoMapper.deleteByPrimaryKey2(id);
	}

	@Override
	public int insert1(Student stu) {
		return infoMapper.insert1(stu);
	}

	@Override
	public int insert2(Classes cla) {
		return infoMapper.insert2(cla);
	}

	@Override
	public int insert3(Dormitory dor) {
		return infoMapper.insert3(dor);
	}

	@Override
	public Classes selectByClassesCard(String classesCard) {
		return infoMapper.selectByClassesCard(classesCard);
	}

	@Override
	public Dormitory selectBydormNum(String dormNum) {
		return infoMapper.selectBydormNum(dormNum);
	}

	@Override
	public Score selectBySubject(String subject, Integer stuId) {
		return infoMapper.selectBySubject(subject, stuId);
	}

	@Override
	public int insert4(Score score) {
		return infoMapper.insert4(score);
	}

	@Override
	public int update(Score score) {
		return infoMapper.update(score);
	}

	@Override
	public int update1(Classes cla) {
		return infoMapper.update1(cla);
	}

	@Override
	public int update2(Dormitory dor) {
		return infoMapper.update2(dor);
	}

	@Override
	public int update3(Student stu) {
		return infoMapper.update3(stu);
	}

	@Override
	public Student selectByStuId(Integer id) {
		return infoMapper.selectByStuId(id);
	}

	@Override
	public Integer count(String department, String stuNumber) {
		return infoMapper.count(department, stuNumber);
	}

	@Override
	public Integer count1(String department) {
		return infoMapper.count1(department);
	}

	@Override
	public Integer count2(String stuNumber, String subject) {
		return infoMapper.count2(stuNumber, subject);
	}
	
	

}
