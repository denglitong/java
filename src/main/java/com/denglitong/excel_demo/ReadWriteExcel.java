package com.denglitong.excel_demo;

import com.denglitong.aws_s3_demo.UploadObjects;
import com.denglitong.aws_textract_demo.ClientBuilder;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.util.Objects;

/**
 * @author litong.deng@foxmail.com
 * @date 2021/9/10
 */
public class ReadWriteExcel {

    public static void main(String[] args) throws IOException {
        String fileName = "/Users/litong.deng/workspace/java/src/main/resources/excel/example.xlsx";
        FileInputStream input = new FileInputStream(fileName);
        Workbook workbook = WorkbookFactory.create(input);

        // TODO: it's wield the excel file content will be broke if copied and read from the resources dir
        // InputStream inputStream = ReadWriteExcel.class.getClassLoader().getResourceAsStream("excel/example.xlsx");
        // assert inputStream != null;
        // Workbook workbook = WorkbookFactory.create(inputStream);

        Sheet sheet = workbook.getSheetAt(0);

        // ① 打印 被审计单位
        System.out.println(sheet.getRow(2).getCell(0).getStringCellValue());

        // ② TODO 判断 被审计单位 是 初次审计 还是 连续审计

        // ③ 打印 资产总额 数据
        System.out.printf("%s 未审数 %.2f 重要性水平-比例 %f 重要性水平-金额 %.2f\n",
            sheet.getRow(24).getCell(0).getStringCellValue(),
            sheet.getRow(24).getCell(4).getNumericCellValue(),
            sheet.getRow(24).getCell(5).getNumericCellValue(),
            sheet.getRow(24).getCell(7).getNumericCellValue()
        );

        // ④ 填充 计划的重要性水平 单元格
        sheet.getRow(28).getCell(2)
            .setCellValue(sheet.getRow(24).getCell(7).getNumericCellValue());

        // ⑤ 填充 理由 单元格
        sheet.getRow(29).getCell(1).setCellValue("连续接受审计，风险水平较低");

        // ⑥ 保存到原文件
        FileOutputStream output = new FileOutputStream(fileName);
        workbook.write(output);
        output.close();
    }
}
