package com.bky.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.bky.model.Add;
import com.bky.model.Classes;
import com.bky.model.Dormitory;
import com.bky.model.Result;
import com.bky.model.Score;
import com.bky.model.Student;
import com.bky.service.BaseService;

@Controller
public class BaseController {
	
	private BaseService baseService;
	
	public BaseService getBaseService() {
		return baseService;
	}
	@Autowired
	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}
	
	/**
	 * 添加学生信息
	 * <p>
	 */
	@RequestMapping(value = "addInfo",method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	public ModelAndView add(Add add,HttpServletRequest request){
		
		ModelAndView v = new ModelAndView();
		try {
			
			Student student = new Student();
			
			Classes classes = baseService.selectByClassesCard(add.getClassesCard());
			// 班级不存在，添加班级信息
			if(classes == null) {
				Classes cla = new Classes();
				cla.setDepartment(add.getDepartment());
				cla.setGrade(add.getGrade());
				cla.setClassesCard(add.getClassesCard());
				baseService.insert2(cla);
				
				student.setClassesId(cla.getId());
			}else {
				student.setClassesId(classes.getId());
			}
			Dormitory dormitory = baseService.selectBydormNum(add.getDormNum());
			// 宿舍不存在，添加宿舍信息
			if(dormitory == null) {
				Dormitory dor = new Dormitory();
				dor.setDepartment(add.getDepartment());
				dor.setBuild(add.getBuild());
				dor.setDormNum(add.getDormNum());
				baseService.insert3(dor);
				
				student.setDormitoryId(dor.getId());
			}else {
				student.setDormitoryId(dormitory.getId());
			}
			
			// 添加学生信息，成功返回学生列表页
			student.setName(add.getName());
			student.setSex(add.getSex());
			student.setStuNumber(add.getStuNumber());
			if(baseService.insert1(student) > 0) {
				//v.addObject("msg","添加成功");
				v.setViewName("showAll");
				return v;
			}
			// 失败返回添加页并显示失败信息
			v.addObject("msg","添加失败");
			v.setViewName("add");
			return v;
		} catch (Exception e) {
			e.printStackTrace();
			return v;
		}
	}
	
	/**
	 * 添加宿舍信息
	 * <p>
	 */
	@RequestMapping(value = "addInfo1",method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	public ModelAndView add1(Dormitory dor,HttpServletRequest request){
		
		ModelAndView v = new ModelAndView();
		try {
			
			Dormitory dormitory = baseService.selectBydormNum(dor.getDormNum());
			
			if(dormitory == null) {
				Dormitory d = new Dormitory();
				d.setDepartment(dor.getDepartment());
				d.setBuild(dor.getBuild());
				d.setDormNum(dor.getDormNum());
				baseService.insert3(dor);
				v.setViewName("showAll2");
				return v;
			}else {
				v.addObject("msg","该宿舍已存在");
				v.setViewName("add2");
				return v;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return v;
		}
	}
	
	/**
	 * 添加或者修改成绩信息
	 * <p>
	 */
	@RequestMapping(value = "addInfo2",method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	public ModelAndView add2(Add add,HttpServletRequest request){
		
		ModelAndView v = new ModelAndView();
		try {
			
			Score score = baseService.selectBySubject(add.getSubject(), add.getStuId());
			
			Score s = new Score();
			// 判断该学生次科目有没有成绩，没有就添加。有成绩则修改成绩
			if(score == null) {
				s.setMark(add.getMark());
				s.setSubject(add.getSubject());
				s.setStuId(add.getStuId());
				baseService.insert4(s);
			}else {
				s.setId(add.getId());
				s.setMark(add.getMark());
				s.setSubject(add.getSubject());
				//s.setStuId(add.getStuId());
				baseService.update(s);
			}
			v.setViewName("showAll3");
			return v;
		} catch (Exception e) {
			e.printStackTrace();
			return v;
		}
	}
	
	/**
	 * 查询学生信息
	 * <p>
	 */
	@RequestMapping(value = "getAll",method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getAddInfoAll(HttpServletRequest request,
			@RequestParam(value = "department", defaultValue = "") String department,
			@RequestParam(value = "stuNumber", defaultValue = "") String stuNumber,int page,int rows){
		try {			
			List<Map<String, Object>> list = baseService.getAll(department,stuNumber,(page-1)*rows,rows);
			 return JSON.toJSONString(new Result(list,baseService.count(department, stuNumber)));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "信息载入失败！具体异常信息：" + e.getMessage());
			return "result";
		}
	}
	
	/**
	 * 删除学生信息
	 * <p>
	 */
	@RequestMapping(value ="del",method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String del(Integer id,HttpServletRequest request){
		try {			
			if(baseService.deleteByPrimaryKey(id) > 0) {
				return JSON.toJSONString(new Result("删除学生信息成功"));
			}
			return JSON.toJSONString(new Result("删除学生信息失败"));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "删除信息失败！具体异常信息：" + e.getMessage());
			return "result";
		} 
	}
	
	/**
	 * 查询宿舍信息
	 * <p>
	 */
	@RequestMapping(value = "getAll1",method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getAddInfoAll1(HttpServletRequest request,
			@RequestParam(value = "department", defaultValue = "") String department,int page,int rows){
		try {			
			List<Map<String, Object>> list = baseService.getAll1(department,(page-1)*rows,rows);
			 return JSON.toJSONString(new Result(list,baseService.count1(department)));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "信息载入失败！具体异常信息：" + e.getMessage());
			return "result";
		}
	}
	
	/**
	 * 删除宿舍信息
	 * <p>
	 */
	@RequestMapping(value ="del1",method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String del1(Integer id,HttpServletRequest request){
		try {			
			if(baseService.deleteByPrimaryKey1(id) > 0) {
				return JSON.toJSONString(new Result("删除宿舍信息成功"));
			}
			return JSON.toJSONString(new Result("删除宿舍信息失败"));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "删除信息失败！具体异常信息：" + e.getMessage());
			return "result";
		} 
	}
	
	/**
	 * 查询学生成绩信息
	 * <p>
	 */
	@RequestMapping(value = "getAll2",method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String getAddInfoAll2(HttpServletRequest request,
			@RequestParam(value = "stuNumber", defaultValue = "") String stuNumber,
			@RequestParam(value = "subject", defaultValue = "") String subject,int page,int rows){
		try {			
			List<Map<String, Object>> list = baseService.getAll2(stuNumber, subject,(page-1)*rows,rows);
			 return JSON.toJSONString(new Result(list,baseService.count2(stuNumber, subject)));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "信息载入失败！具体异常信息：" + e.getMessage());
			return "result";
		}
	}	
	
	/**
	 * 删除学生成绩信息
	 * <p>
	 */
	@RequestMapping(value ="del2",method = RequestMethod.POST, produces = "text/json;charset=UTF-8")
	@ResponseBody
	public String del2(Integer id,HttpServletRequest request){
		try {			
			if(baseService.deleteByPrimaryKey2(id) > 0) {
				return JSON.toJSONString(new Result("删除成绩信息成功"));
			}
			return JSON.toJSONString(new Result("删除成绩信息失败"));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "删除信息失败！具体异常信息：" + e.getMessage());
			return "result";
		} 
	}
	
	/**
	 * 修改学生信息
	 * <p>
	 */
	@RequestMapping("update")
	public ModelAndView update(Add add,HttpServletRequest request){
		ModelAndView v = new ModelAndView();
		try {			
			Student student = baseService.selectByStuId(add.getId());
			
			Classes classes = baseService.selectByClassesCard(add.getClassesCard());
			
			Classes cla = new Classes();
			cla.setId(classes.getId());
			cla.setDepartment(add.getDepartment());
			cla.setGrade(add.getGrade());
			cla.setClassesCard(add.getClassesCard());
			// 班级不存在，添加班级信息
			if(classes == null) {
				
				baseService.insert2(cla);
				
				student.setClassesId(cla.getId());
			}else {
				baseService.update1(cla);
			}
			Dormitory dormitory = baseService.selectBydormNum(add.getDormNum());
			
			Dormitory dor = new Dormitory();
			dor.setId(dormitory.getId());
			dor.setDepartment(add.getDepartment());
			dor.setBuild(add.getBuild());
			dor.setDormNum(add.getDormNum());
			// 宿舍不存在，添加宿舍信息
			if(dormitory == null) {
				
				baseService.insert3(dor);
				
				student.setDormitoryId(dor.getId());
			}else {
				baseService.update2(dor);
			}
			
			student.setId(add.getId());
			student.setName(add.getName());
			student.setSex(add.getSex());
			student.setStuNumber(add.getStuNumber());
			// 修改学生信息，成功返回列表页
			if(baseService.update3(student) > 0) {
				v.setViewName("showAll");
				return v;
			}
			// 失败返回修改页，返回错误信息
			v.addObject("msg","修改失败");
			v.setViewName("modify");
			return v;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "更新信息失败！具体异常信息：" + e.getMessage());
			return v;
		} 
	}
	
	/**
	 * 修改宿舍信息
	 * <p>
	 */
	@RequestMapping("update1")
	public ModelAndView update1(Dormitory dormitory,HttpServletRequest request){
		ModelAndView v = new ModelAndView();
		try {			
				Dormitory dor = new Dormitory();
				dor.setId(dormitory.getId());
				dor.setDepartment(dormitory.getDepartment());
				dor.setBuild(dormitory.getBuild());
				dor.setDormNum(dormitory.getDormNum());
		
			if(baseService.update2(dor) > 0) {
				v.setViewName("showAll2");
				return v;
			}
			v.addObject("msg","修改失败");
			v.setViewName("modify2");
			return v;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("InfoMessage", "更新信息失败！具体异常信息：" + e.getMessage());
			return v;
		} 
	}
	
	/**
	 * 学生信息导出
	 * <p>
	 */
	@RequestMapping(value = "exportToExcel")
	public void exportExcel(HttpServletResponse resp, @RequestParam(value = "department", defaultValue = "") String department,
			@RequestParam(value = "stuNumber", defaultValue = "") String stuNumber) throws IOException {
		// 要导出的数据
		List<Map<String, Object>> list = baseService.getAll(department,stuNumber,0,99999);
		if (list.isEmpty()) {
			resp.getWriter().print("<script>alert('没有需要导出的项！')</script>");
		}
		
		// 批量导出数据
		HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个Excel文件
		HSSFSheet sheet = workbook.createSheet("用户基本信息表");// 创建一个Excel的Sheet
		sheet.setDefaultColumnWidth(12);
		
		// 创建查询条件行
		HSSFRow row1 = sheet.createRow((int) 0);// 创建第一行
		HSSFCell cell1 = row1.createCell((int) 0);// 创建第一列
		
		cell1.setCellValue("院系：");
		cell1 = row1.createCell((int) 1);// 创建第二列
		cell1.setCellValue(department);
		
		cell1 = row1.createCell((int) 2);
		cell1.setCellValue("学号：");
		cell1 = row1.createCell((int) 3);
		cell1.setCellValue(stuNumber);
		
		
		// 标题行
		HSSFRow row2 = sheet.createRow((int) 1);// 创建第二行
		row2.setHeight((short) 650);
		
		HSSFCell cell2 = row2.createCell((int) 0);// 创建第一列
		cell2.setCellValue("编号");
		
		cell2 = row2.createCell((int) 1);// 创建第二列
		cell2.setCellValue("学号");
		
		cell2 = row2.createCell((int) 2);// 创建第三列
		cell2.setCellValue("姓名");
		
		cell2 = row2.createCell((int) 3);// 创建第四列
		cell2.setCellValue("性别");
		
		cell2 = row2.createCell((int) 4);// 创建第五列
		cell2.setCellValue("院系");
		
		cell2 = row2.createCell((int) 5);// 创建第六列
		cell2.setCellValue("年级");
		
		cell2 = row2.createCell((int) 6);// 创建第七列
		cell2.setCellValue("班级");
		
		cell2 = row2.createCell((int) 7);// 创建第八列
		cell2.setCellValue("宿舍楼栋");
		
		cell2 = row2.createCell((int) 8);
		cell2.setCellValue("宿舍编号");
		
		cell2 = row2.createCell((int) 9);
		cell2.setCellValue("创建时间");
		
		cell2 = row2.createCell((int) 10);
		cell2.setCellValue("修改时间");
		
		int t = 2;
		for (Map<String, Object> m : list) {
			System.out.println(m);
			HSSFRow row = sheet.createRow((int) t);// 创建一行
			HSSFCell cell = row.createCell((int) 0);// 创建第一列
			cell.setCellValue(m.get("id")+"");
			
			cell = row.createCell((int) 1);// 创建第二列
			cell.setCellValue(m.get("stuNumber")+"");
			
			cell = row.createCell((int) 2);// 创建第三列
			cell.setCellValue(m.get("name")+"");
			
			cell = row.createCell((int) 3);// 创建第四列
			cell.setCellValue(m.get("sex")+"");
			
			cell = row.createCell((int) 4);// 创建第五列
			cell.setCellValue(m.get("department")+"");
			
			cell = row.createCell((int) 5);// 创建第六列
			cell.setCellValue(m.get("grade")+"");
			
			cell = row.createCell((int) 6);// 创建第七列
			cell.setCellValue(m.get("classesCard")+"");
			
			cell = row.createCell((int) 7);
			cell.setCellValue(m.get("build")+"");
			
			cell = row.createCell((int) 8);
			cell.setCellValue(m.get("dormNum")+"");
			
			cell = row.createCell((int) 9);
			cell.setCellValue(m.get("createTime")+"");
			
			cell = row.createCell((int) 10);
			cell.setCellValue(m.get("updateTime")+"");
			
			t++;
		}

		String fileName = "用户基本信息-" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()) + ".xls";
		
		OutputStream out = null;
		try {
			out = resp.getOutputStream();
			resp.setContentType("application/ms-excel;charset=UTF-8");
			resp.setHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}
