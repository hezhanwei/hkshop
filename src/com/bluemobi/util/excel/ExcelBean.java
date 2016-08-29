package com.bluemobi.util.excel;

import java.io.UnsupportedEncodingException;
import java.util.List;



public class ExcelBean {

    /**
     * 生产excel文件 不需要合并单元格的表格
     * @author HeWeiwen
     * 2015-11-11
     * @param excelTO 带入数据对象 
     * @throws UnsupportedEncodingException
     */
    public void exportDataToExcel(ExcelTO excelTO) throws UnsupportedEncodingException{
        ExcelList el = new ExcelList();
        el.outExcelFile(excelTO.getTitleName(), excelTO.getResultList(), excelTO.getResultName() + ".xls");
    }
    
 
    
    /**
     * 生产excel文件 不需要合并单元格的表格
     * @author HeWeiwen
     * 2015-11-10
     * @param title 表标题
     * @param resultList 遍历数据
     * @param name 表名称
     * @throws UnsupportedEncodingException
     */
    public void exportDataToExcelAll(List<String> title, List<Object[]> resultList, String name) throws UnsupportedEncodingException{
        ExcelList el = new ExcelList();
        el.outExcelFile(title, resultList, name + ".xls");
    }
    
    /**
     * 生产excel文件 有需要合并单元格的表格
     * @param title1
     * @param title2
     * @param title3
     * @param resultList
     * @param name
     * @throws UnsupportedEncodingException
     */
    public void exportDataToExcelCells(ExcelTO excelTO) throws UnsupportedEncodingException{
        ExcelList el = new ExcelList();
        el.outExcelFile(excelTO.getTitleName(), excelTO.getTitleName2(), excelTO.getTitleName3(), excelTO.getResultList(), excelTO.getResultName() + ".xls");
    }
}
