package com.bky.util;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * POI工具类
 * <p>
 * 创建人：彭益清      博客：https://blog.csdn.net/pyq666 <br>
 * 创建时间：2018年12月20日 下午5:04:25 <br>
 * <p>
 * 修改人： <br>
 * 修改时间： <br>
 * 修改备注： <br>
 * </p>
 */
public class ExportExcelUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ExportExcelUtil.class);

	/** Excel日期类型格式 */
	private static Short DATE_TYPE = null;
	/** 日期类型格式 */
	private static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	/** Excel时间类型格式 */
	private static Short DATE_TIME_TYPE = null;
	/** 时间类型格式 */
	private static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 导出数据到Excel <br>
	 * <br>
	 * 创建人： 彭益清 <br>
	 * 创建时间： 2017年12月14日 下午5:30:32 <br>
	 *
	 * @param fns
	 *            字段名，多个字段名用逗号连接（字符串内可以有空格），例如：code, name, age ...
	 * @param fcs
	 *            字段注释（显示为Excel的标题），例如：编号, 姓名, 年龄
	 *            ...，必须和字段名一一对应，否则会导致Excel列和标题栏对应不起来
	 * @param data
	 *            显示到Excel上面的数据
	 * @param converter
	 *            数据格式转换器，key为字段名，value为格式串，例如：true=启用;false=禁用;
	 *            ，代表字段值要转换成什么字符串填充到Excel单元格
	 * @param fileName
	 *            导出文件名（不用写后缀）
	 * @param resp
	 *            响应对象
	 */
	public static <T> void exportExcel(String fns, String fcs, final Collection<T> data, Map<String, String> converter,
			final String fileName, final HttpServletResponse resp) {
		// 声明一个工作薄
		final SXSSFWorkbook workbook = new SXSSFWorkbook();
		// 初始化数据格式
		if (DATE_TYPE == null) {
			DATE_TYPE = workbook.createDataFormat().getFormat("yyyy-MM-dd");
		}
		if (DATE_TIME_TYPE == null) {
			DATE_TIME_TYPE = workbook.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss");
		}

		// 生成一个表格。XSSFSheet生成的是.xlsx，在office下正常打开，在wps下打开样式有问题；SXSSFSheet生成的是.xls，没有这个兼容性问题
		final SXSSFSheet sheet = workbook.createSheet(fileName);
		// 设置表格默认列宽度为n个字节
		// sheet.setDefaultColumnWidth(30);
		// 设置表格自适应列宽度
		// sheet.autoSizeColumn(1, true);
		// sheet.autoSizeColumn(1);

		/** 填充标题 ==================================================================== */
		// 创建表格标题行
		SXSSFRow row = sheet.createRow(0);

		// 设置标题格式
		CellStyle headerStyle = workbook.createCellStyle();
		// headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);// 设置背景色
		//headerStyle.setBorderBottom(BorderStyle.THIN); // 下边框
		//headerStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 13);// 设置字体大小
		font.setBold(true);// 粗体显示
		headerStyle.setFont(font);// 选择需要用到的字体格式

		// 提取标题栏
		final String[] headers = fcs.replaceAll("\\s+", "").split(",");
		// 循环填充标题数据
		for (int i = 0; i < headers.length; i++) {
			sheet.setColumnWidth(i, 30 * 256);
			final SXSSFCell cell = row.createCell(i);
			// final SXSSFRichTextString text = new SXSSFRichTextString(headers[i]);
			// cell.setCellValue(text);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerStyle);
		}

		/** 填充表体 ==================================================================== */
		try {
			// 提取数据列的属性名集合
			String[] fieldNames = fns.replaceAll("\\s+", "").split(",");

			// 遍历数据，按行创建并填充数据
			int index = 0;
			for (T t : data) {
				index++;
				// 创建Excel行
				row = sheet.createRow(index);

				// 循环按列填充表格
				for (int j = 0; j < headers.length; j++) {
					final SXSSFCell cell = row.createCell(j);
					// 要填充到单元格的列字段值
					Object fieldValue = null;
					// 数据行可能是Map或其他类型，分开处理
					if (t instanceof Map) {
						fieldValue = ((Map) t).get(fieldNames[j]);
					} else {
						final String getMethodName = "get" + fieldNames[j].substring(0, 1).toUpperCase()
								+ fieldNames[j].substring(1);
						final Class<? extends Object> tCls = t.getClass();
						final Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
						fieldValue = getMethod.invoke(t, new Object[] {});
					}

					// 设置单元格格式
					CellStyle cellStyle = workbook.createCellStyle();
					//cellStyle.setAlignment(HorizontalAlignment.CENTER);
					cell.setCellStyle(cellStyle);
					String conv = null;
					if (converter != null) {
						conv = converter.get(fieldNames[j]);
					}
					convertToCell(cell, cellStyle, fieldNames[j], fieldValue, conv);
				}
			}
			writeToResponse(workbook, fileName, resp);
		} catch (final Exception e) {
			logger.error(null, e);
		}
	}

	/**
	 * 把数据转换并填充到Excel单元格 <br>
	 * <br>
	 * 创建人：彭益清 <br>
	 * 创建时间： 2017年12月14日 下午6:07:40 <br>
	 *
	 * @param cell
	 *            单元格
	 * @param cellStyle
	 *            单元格格式
	 * @param fieldName
	 *            字段名
	 * @param fieldValue
	 *            字段值
	 * @param conv
	 *            转换器格式串，例如：true=启用;false=禁用; ，代表字段值要转换成什么字符串填充到Excel单元格
	 * @throws ParseException
	 */
	private static void convertToCell(SXSSFCell cell, CellStyle cellStyle, String fieldName, Object fieldValue,
			String conv) throws ParseException {

		// 要转换的值不为空才转换
		if (fieldValue != null) {
			if (StringUtils.isNotBlank(conv)) {
				// 获取对应的格式化值，比如 conv 为 true=启用;false=禁用; ，当 fieldValue 是true时，在Excel单元格里填 启用
				// 而不是true
				Matcher m = Pattern.compile(fieldValue + "\\s*=(.*?);").matcher(conv);
				if (m.find()) {
					// 截取括号内的值
					fieldValue = m.group(1);
				}
				cell.setCellValue(fieldValue.toString());
			} else {
				// 如果是日期类型，需要格式化值
				if (fieldValue instanceof Date) {
					// 单元格值（保存为字符串）
					String cellValue = "";
					// Date temp = null;
					// 如果是时间戳类型（Timestamp是Date的子类），或者字段名包含了time，就转换字段值为时间格式
					if (fieldValue instanceof Timestamp || fieldName.toLowerCase().contains("time")) {
						// cellStyle.setDataFormat(DATE_TIME_TYPE);
						// temp = DATE_TIME_FORMAT.parse(fieldValue.toString());
						cellValue = DATE_TIME_FORMAT.format(fieldValue);
					} else {
						// cellStyle.setDataFormat(DATE_TYPE);
						// temp = DATE_FORMAT.parse(fieldValue.toString());
						cellValue = DATE_TIME_FORMAT.format(fieldValue);
					}
					// cell.setCellType(CellType.STRING);
					// cell.setCellValue(temp);
					cell.setCellValue(cellValue);
				} else if (fieldValue instanceof BigDecimal) {
					BigDecimal bd = (BigDecimal) fieldValue;
					// BigDecimal一般用来保存金额，保留两位小数
					double d = bd.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
					cell.setCellValue(d);
				} else if (fieldValue instanceof Double) {
					cell.setCellValue((Double) fieldValue);
				} else if (fieldValue instanceof Long) {
					cell.setCellValue((Long) fieldValue);
				} else if (fieldValue instanceof Integer) {
					cell.setCellValue((Integer) fieldValue);
				} else if (fieldValue instanceof Short) {
					cell.setCellValue((Short) fieldValue);
				} else if (fieldValue instanceof Byte) {
					cell.setCellValue((Byte) fieldValue);
				} else if (fieldValue instanceof Boolean) {
					cell.setCellValue((Boolean) fieldValue);
				} else {
					// 其他情况统一保存为字符串
					cell.setCellValue(fieldValue.toString());
				}
			}
		} else {
			// 没有值时，设置为空字符串
			cell.setCellValue("");
		}

	}

	/**
	 * 把文件写到响应流中 <br>
	 * <br>
	 * 创建人： 彭益清 <br>
	 * 创建时间： 2017年10月30日 下午8:54:42 <br>
	 *
	 * @param workbook
	 *            表格实体
	 * @param name
	 *            文件名
	 * @param response
	 * @throws Exception
	 */
	public static void writeToResponse(final SXSSFWorkbook workbook, final String name,
			final HttpServletResponse response) throws Exception {
		BufferedOutputStream fos = null;
		try {
			final String fileName = name + ".xls";
			// response.setContentType("application/x-msdownload");
			// response.setHeader("Content-Disposition",
			// "attachment;filename=" + new String(fileName.getBytes("UTF-8"),
			// "ISO8859-1"));
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String((name + ".xlsx").getBytes(), "iso-8859-1"));
			fos = new BufferedOutputStream(response.getOutputStream());
			workbook.write(fos);
		} catch (final Exception e) {
			logger.error(null, e);
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	/**
	 * Excel读取 操作
	 * <br><br>
	 * 创建人： 彭益清 <br>
	 * 创建时间：  2018年1月9日 下午10:08:51 <br>
	 *
	 * @param filePath
	 * @return
	 * @throws InvalidFormatException 
	 * @throws EncryptedDocumentException 
	 * @throws IOException
	 */
	public static List<List<String>> readExcel(String filePath) throws EncryptedDocumentException, InvalidFormatException, IOException {
		Workbook wb = null;
		InputStream is;
		is = new FileInputStream(filePath);
		wb = WorkbookFactory.create(is);

		/** 得到第一个sheet */
		Sheet sheet = wb.getSheetAt(0);
		/** 得到Excel的行数 */
		int totalRows = sheet.getPhysicalNumberOfRows();

		/** 得到Excel的列数 */
		int totalCells = 0;
		if (totalRows >= 1 && sheet.getRow(0) != null) {
			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}

		List<List<String>> dataLst = new ArrayList<List<String>>();
		/** 循环Excel的行 */
		for (int r = 0; r < totalRows; r++) {
			Row row = sheet.getRow(r);
			if (row == null)
				continue;
			List<String> rowLst = new ArrayList<String>();
			/** 循环Excel的列 */
			for (int c = 0; c < totalCells; c++) {
				Cell cell = row.getCell(c);
				String cellValue = "";
				if (null != cell) {
					HSSFDataFormatter hSSFDataFormatter = new HSSFDataFormatter();
					cellValue = hSSFDataFormatter.formatCellValue(cell);

					// 以下是判断数据的类型
					/*
					 * 
					 * switch (cell.getCellType()) { case Cell.CELL_TYPE_NUMERIC: // 数字 cellValue =
					 * cell.getNumericCellValue() + ""; break; case Cell.CELL_TYPE_STRING: // 字符串
					 * cellValue = cell.getStringCellValue(); break; case Cell.CELL_TYPE_BOOLEAN: //
					 * Boolean cellValue = cell.getBooleanCellValue() + ""; break; case
					 * Cell.CELL_TYPE_FORMULA: // 公式 cellValue = cell.getCellFormula() + ""; break;
					 * case Cell.CELL_TYPE_BLANK: // 空值 cellValue = ""; break; case
					 * Cell.CELL_TYPE_ERROR: // 故障 cellValue = "非法字符"; break; default: cellValue =
					 * "未知类型"; break; }
					 */
				}
				rowLst.add(cellValue);
			}
			/** 保存第r行的第c列 */
			dataLst.add(rowLst);
		}
		return dataLst;
	}

}
