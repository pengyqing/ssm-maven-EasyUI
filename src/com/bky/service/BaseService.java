package com.bky.service;

import java.util.List;
import java.util.Map;

import com.bky.model.Classes;
import com.bky.model.Dormitory;
import com.bky.model.Score;
import com.bky.model.Student;

public interface BaseService {
	
	List<Map<String, Object>> getAll(String department,String stuNumber,int page,int rows);
	
	List<Map<String, Object>> getAll1(String department,int page,int rows);
	
	List<Map<String, Object>> getAll2(String stuNumber,String subject,int page,int rows);
	
	Integer count(String department,String stuNumber);
    
    Integer count1(String department);
    
    Integer count2(String stuNumber,String subject);
	
	int deleteByPrimaryKey(Integer id);
	
	int deleteByPrimaryKey1(Integer id);
	
	int deleteByPrimaryKey2(Integer id);
	
	int insert1(Student stu);
	
	int insert2(Classes cla);
	
	int insert3(Dormitory dor);
	
	int insert4(Score score);
	
	Classes selectByClassesCard(String classesCard);
	
	Dormitory selectBydormNum(String dormNum);
	
	Score selectBySubject(String subject,Integer stuId);
	
	Student selectByStuId(Integer id);
	
	int update(Score score);
	
	int update1(Classes cla);
    
    int update2(Dormitory dor);
    
    int update3(Student stu);

}
