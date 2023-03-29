package com.samiach.exporttoexceldemo.utility;

import com.samiach.exporttoexceldemo.model.Customer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ExcelExportUtil {

    private XSSFWorkbook workbook;
    private XSSFSheet sheet, sheet01, sheet02;
    private List<Customer> customerList;

    public ExcelExportUtil(List<Customer> customerList) {
        this.customerList = customerList;
        workbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);

        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue((String) value);
        }

        cell.setCellStyle(style);
    }

    private void createHeaderRow() {
        // Sheet Creation
        sheet = workbook.createSheet("Customer Information");
        sheet01 = workbook.createSheet("Product Information");
        sheet02 = workbook.createSheet("Order Information");

        // Conditional Formatting
        SheetConditionalFormatting sheetCF = sheet.getSheetConditionalFormatting();
        ConditionalFormattingRule rule1 = sheetCF.createConditionalFormattingRule(ComparisonOperator.EQUAL, "1");
        FontFormatting fontFmt = rule1.createFontFormatting();
        fontFmt.setFontStyle(true, false);
        fontFmt.setFontColorIndex(IndexedColors.DARK_RED.index);

        PatternFormatting patternFmt = rule1.createPatternFormatting();
        patternFmt.setFillBackgroundColor(IndexedColors.YELLOW.index);

        ConditionalFormattingRule [] cfRules = {rule1};
        CellRangeAddress[] regions = {CellRangeAddress.valueOf("E3:E100")};
        sheetCF.addConditionalFormatting(regions, cfRules);


        // Row Creation
        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        createCell(row, 0, "Customer Information", style);

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
        font.setFontHeightInPoints((short) 10);

        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "First Name", style);
        createCell(row, 2, "Last Name", style);
        createCell(row, 3, "Email", style);
        createCell(row, 4, "Status", style);
        createCell(row, 5, "Country", style);
        createCell(row, 6, "State", style);
        createCell(row, 7, "City", style);
        createCell(row, 8, "Address", style);
    }

    private void writeCustomerData() {
        int rowCount = 2;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Customer customer : customerList) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, customer.getId(), style);
            createCell(row, columnCount++, customer.getFirstName(), style);
            createCell(row, columnCount++, customer.getLastName(), style);
            createCell(row, columnCount++, customer.getEmail(), style);
            createCell(row, columnCount++, customer.getStatus(), style);
            createCell(row, columnCount++, customer.getAddress().getCountry(), style);
            createCell(row, columnCount++, customer.getAddress().getState(), style);
            createCell(row, columnCount++, customer.getAddress().getCity(), style);
            createCell(row, columnCount++, customer.getAddress().getAddress(), style);
        }
    }

    public void exportDataToExcel(HttpServletResponse response) throws IOException {
        createHeaderRow();
        writeCustomerData();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
