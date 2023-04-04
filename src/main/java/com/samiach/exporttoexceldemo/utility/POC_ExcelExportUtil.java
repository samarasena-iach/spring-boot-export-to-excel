package com.samiach.exporttoexceldemo.utility;

import com.samiach.exporttoexceldemo.model.POC.Milestone;
import com.samiach.exporttoexceldemo.model.POC.Release;
import com.samiach.exporttoexceldemo.model.POC.Submilestone;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class POC_ExcelExportUtil {

    private XSSFWorkbook workbook;
    private Map<String, Release> data;

    public POC_ExcelExportUtil(Map<String, Release> data) {
        this.data = data;
        workbook = new XSSFWorkbook();
    }

    public void exportDataToExcel(HttpServletResponse response) throws IOException {
        createDataSheet01();
        createDataSheet02();

        // VISIBLE SHEET
        Sheet sheet = workbook.createSheet("SMART PMWB");
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(14);
        style.setFont(font);

        Cell cell00 = sheet.createRow(0).createCell(0);
        cell00.setCellValue("Release");
        cell00.setCellStyle(style);

        Cell cell01 = sheet.getRow(0).createCell(1);
        cell01.setCellValue("Milestone");
        cell01.setCellStyle(style);

        Cell cell02 = sheet.getRow(0).createCell(2);
        cell02.setCellValue("Sub Milestone");
        cell02.setCellStyle(style);

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);

        sheet.setActiveCell(new CellAddress("A2"));

        // DATA VALIDATIONS
        DataValidationHelper dvHelper = sheet.getDataValidationHelper();
        int lastRow = workbook.getSpreadsheetVersion().getLastRowIndex();

        // DATA VALIDATIONS FOR 'RELEASE' RECORDS IN A2
        DataValidationConstraint dvConstraint = dvHelper.createFormulaListConstraint("Releases");
        CellRangeAddressList addressList = new CellRangeAddressList(1, lastRow, 0, 0);
        DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
        sheet.addValidationData(validation);

        // DATA VALIDATIONS FOR 'MILESTONE' RECORDS OF THE SELECTED RELEASE IN B2
        // dvConstraint = dvHelper.createFormulaListConstraint("INDIRECT($A$2)");
        dvConstraint = dvHelper.createFormulaListConstraint("INDIRECT(SUBSTITUTE(A2,\" \",\"\"))");
        addressList = new CellRangeAddressList(1, lastRow, 1, 1);
        validation = dvHelper.createValidation(dvConstraint, addressList);
        sheet.addValidationData(validation);

        // DATA VALIDATIONS FOR 'SUBMILESTONE' RECORDS OF THE SELECTED MILESTONE IN C2
        // dvConstraint = dvHelper.createFormulaListConstraint("INDIRECT($A$2)");
        dvConstraint = dvHelper.createFormulaListConstraint("INDIRECT(SUBSTITUTE(B2,\" \",\"\"))");
        addressList = new CellRangeAddressList(1, lastRow, 2, 2);
        validation = dvHelper.createValidation(dvConstraint, addressList);
        sheet.addValidationData(validation);

        // HIDE THE 'DataSheet' SHEET
        workbook.setSheetHidden(0, true);
        workbook.setSheetHidden(1, true);

        // SET 'SMART PMWB' SHEET AS ACTIVE
        workbook.setActiveSheet(2);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    public void createDataSheet01() {
        // HIDDEN SHEET 01 (TO POPULATE 'RELEASE' & 'MILESTONE' LIST VALUES)
        Sheet sheet = workbook.createSheet("DataSheet");

        Row row;
        Name namedRange;
        String colLetter;
        String reference;

        int c = 0;

        // POPULATE THE DATA FOR HIDDEN SHEET 01
        for (String key : data.keySet()) {
            Release release = data.get(key);

            int r = 0;

            row = sheet.getRow(r);
            if (row == null) row = sheet.createRow(r);
            r++;
            row.createCell(c).setCellValue(release.getRelease());

            for (Milestone milestone : release.getMilestones()) {
                row = sheet.getRow(r);
                if (row == null) row = sheet.createRow(r);
                r++;
                row.createCell(c).setCellValue(milestone.getMilestone());
            }

            // CREATE NAMED_RANGES FOR THE 'MILESTONE' LIST CONSTRAINTS, EACH NAMED FROM THE CURRENT KEY
            colLetter = CellReference.convertNumToColString(c);
            namedRange = workbook.createName();
            namedRange.setNameName(release.getRelease());
            reference = "DataSheet!$" + colLetter + "$2:$" + colLetter + "$" + r;
            namedRange.setRefersToFormula(reference);
            c++;
        }

        // CREATE NAMED_RANGES FOR THE 'RELEASE' LIST CONSTRAINT
        colLetter = CellReference.convertNumToColString((c - 1));
        namedRange = workbook.createName();
        namedRange.setNameName("Releases");
        reference = "DataSheet!$A$1:$" + colLetter + "$1";
        namedRange.setRefersToFormula(reference);

        // UNSELECT THAT SHEET BECAUSE WE WILL HIDE IT LATER
        sheet.setSelected(false);
    }

    public void createDataSheet02() {
        // HIDDEN SHEET 02 (TO POPULATE 'MILESTONE' & 'SUBMILESTONE' LIST VALUES)
        Sheet sheet = workbook.createSheet("DataSheet_01");

        Row row;
        Name namedRange;
        String colLetter;
        String reference;

        int c = 0;

        // POPULATE THE DATA FOR HIDDEN SHEET 02
        for (String key : data.keySet()) {
            Release release = data.get(key);

            for (Milestone milestone : release.getMilestones()) {
                int r = 0;

                row = sheet.getRow(r);
                if (row == null) row = sheet.createRow(r);
                r++;
                row.createCell(c).setCellValue(milestone.getMilestone());

                for (Submilestone submilestone : milestone.getSubmilestones()) {
                    row = sheet.getRow(r);
                    if (row == null) row = sheet.createRow(r);
                    r++;
                    row.createCell(c).setCellValue(submilestone.getSubmilestone());
                }

                // CREATE NAMED_RANGES FOR THE 'SUBMILESTONE' LIST CONSTRAINTS, EACH NAMED FROM THE CURRENT KEY
                colLetter = CellReference.convertNumToColString(c);
                namedRange = workbook.createName();
                namedRange.setNameName(milestone.getMilestone());
                reference = "DataSheet_01!$" + colLetter + "$2:$" + colLetter + "$" + r;
                namedRange.setRefersToFormula(reference);
                c++;
            }
        }

        // CREATE NAME FOR 'RELEASE' LIST CONSTRAINT
        colLetter = CellReference.convertNumToColString((c - 1));
        namedRange = workbook.createName();
        namedRange.setNameName("Milestones");
        reference = "DataSheet_01!$A$1:$" + colLetter + "$1";
        namedRange.setRefersToFormula(reference);

        // UNSELECT THAT SHEET BECAUSE WE WILL HIDE IT LATER
        sheet.setSelected(false);
    }

    public void testExport() throws IOException {
        Workbook wb = new XSSFWorkbook();
        XSSFSheet sheet = (XSSFSheet) wb.createSheet("Example");
        sheet.setColumnWidth(0, 2);
        //sheet.setColumnWidth(0, 2);

        Row headerRow = sheet.createRow(0);
        int lastRowNum = sheet.getLastRowNum();

        for (int i = 0; i < 10; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue("Heading " + i);
        }
        for (int i = 1; i <= 1000; i++) {
            Row row = sheet.createRow(i);
            for (int j = 0; j < 10; j++) {
                Cell cell = row.createCell(j);
                // cell.setCellValue("SELECT");
            }
        }
        for (int i = 0; i < 10; i++) {
            sheet.autoSizeColumn(i);
        }

        DataValidation dataValidation = null;
        DataValidationConstraint constraint = null;
        DataValidationHelper validationHelper = null;

        validationHelper = new XSSFDataValidationHelper(sheet);
        CellRangeAddressList addressList = new CellRangeAddressList(1, 1001, 0, 9);
        constraint = validationHelper.createExplicitListConstraint(new String[]{"10", "20", "30"});
        dataValidation = validationHelper.createValidation(constraint, addressList);
        dataValidation.setSuppressDropDownArrow(true);
        sheet.addValidationData(dataValidation);


        File file = new File("C:\\Users\\AU256UR\\Downloads\\" + System.currentTimeMillis() + ".xlsx");
        file.createNewFile();
        FileOutputStream outputStream = new FileOutputStream(file, false);
        wb.write(outputStream);
    }
}
