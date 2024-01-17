package org.kt.parttime.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.kt.parttime.user.dto.StudentWageDetailDto;

import java.util.List;
import java.util.Objects;

public class WageExcelUtils {
    public static Workbook makeExcel(List<StudentWageDetailDto> students){
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        makeSchema(sheet);

        students = students.stream().filter(s -> s.getWage() > 0).toList();
        String prevPartTimeName = null;
        int jump = 0;
        for (int i = 0; i < students.size(); i++) {

            StudentWageDetailDto student = students.get(i);
            if(Objects.isNull(prevPartTimeName)) prevPartTimeName = student.getPartTimeGroupName();
            else if(!prevPartTimeName.equalsIgnoreCase(student.getPartTimeGroupName())){
                jump++;
                prevPartTimeName = student.getPartTimeGroupName();
            }

            Row row = sheet.createRow(i + 1 + jump);

            Cell cell = row.createCell(0);
            cell.setCellValue(i + 1);

            cell = row.createCell(1);
            cell.setCellValue(student.getPartTimeGroupName());

            cell = row.createCell(2);
            cell.setCellValue(student.getDepartment());

            cell = row.createCell(3);
            cell.setCellValue(student.getStudentId());

            cell = row.createCell(4);
            cell.setCellValue(student.getName());

            cell = row.createCell(5);
            cell.setCellValue(student.getWorkTime());

            cell = row.createCell(6);
            cell.setCellValue(student.getHourPrice());

            cell = row.createCell(7);
            cell.setCellValue(student.getWage());

            cell = row.createCell(8);
            cell.setCellValue(student.getBank());

            cell = row.createCell(9);
            cell.setCellValue(student.getAccount());

            cell = row.createCell(10);
            cell.setCellValue(student.getPhoneNumber());

            for (int j = 0; j < 10; j++) {
                sheet.autoSizeColumn(j);
                sheet.setColumnWidth(j, (sheet.getColumnWidth(j)) + 1024);
            }
        }
        return workbook;
    }

    private static void makeSchema(Sheet sheet){
        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = sheet.getWorkbook().createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        Font font = sheet.getWorkbook().createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(font);

        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("순번");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("근로지");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("학부");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("학번");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("성명");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(5);
        headerCell.setCellValue("근로시간");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(6);
        headerCell.setCellValue("시급");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(7);
        headerCell.setCellValue("지급금액");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(8);
        headerCell.setCellValue("은행");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(9);
        headerCell.setCellValue("계좌번호");
        headerCell.setCellStyle(headerStyle);

        headerCell = headerRow.createCell(10);
        headerCell.setCellValue("연락처");
        headerCell.setCellStyle(headerStyle);


    }
}
