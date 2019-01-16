package com.bky.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bky.model.Classes;
import com.bky.model.Dormitory;
import com.bky.model.Score;
import com.bky.model.Student;

public interface informationMapper {
	
    int deleteByPrimaryKey(Integer id);
    
    int deleteByPrimaryKey1(Integer id);
    
    int deleteByPrimaryKey2(Integer id);

    int insert1(Student student);
    
    int insert2(Classes classes);
    
    int insert3(Dormitory dormitory);
    
    int insert4(Score score);
    
    List<Map<String, Object>> getAll(@Param("department") String department,@Param("stuNumber") String stuNumber,
    		@Param("page") int page,@Param("rows") int rows);
    
    List<Map<String, Object>> getAll1(@Param("department") String department,@Param("page") int page,@Param("rows") int rows);
    
    List<Map<String, Object>> getAll2(@Param("stuNumber") String stuNumber,@Param("subject") String subject,
    		@Param("page") int page,@Param("rows") int rows);
    
    Integer count(@Param("department") String department,@Param("stuNumber") String stuNumber);
    
    Integer count1(@Param("department") String department);
    
    Integer count2(@Param("stuNumber") String stuNumber,@Param("subject") String subject);
    
    Classes selectByClassesCard(@Param("classesCard") String classesCard);
    
    Dormitory selectBydormNum(@Param("dormNum") String dormNum);
    
    Score selectBySubject(@Param("subject") String subject, @Param("stuId") Integer stuId);
    
    Student selectByStuId(@Param("id") Integer id);

    int update(Score score);
    
    int update1(Classes cla);
    
    int update2(Dormitory dor);
    
    int update3(Student stu);
}