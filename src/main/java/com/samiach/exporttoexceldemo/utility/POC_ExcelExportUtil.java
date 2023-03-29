package com.samiach.exporttoexceldemo.utility;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class POC_ExcelExportUtil {

    private XSSFWorkbook workbook;
    private Map<String, String[]> data;

    public POC_ExcelExportUtil(Map<String, String[]> data) {
        this.data = data;
        workbook = new XSSFWorkbook();
    }

    public void exportDataToExcel(HttpServletResponse response) throws IOException {
        //hidden sheet for list values
        Sheet sheet = workbook.createSheet("ListSheet");

        Row row;
        Name namedRange;
        String colLetter;
        String reference;

        int c = 0;

        //put the data in
        for (String key : data.keySet()) {
            int r = 0;
            row = sheet.getRow(r);
            if (row == null) row = sheet.createRow(r);
            r++;
            row.createCell(c).setCellValue(key);
            String[] items = data.get(key);
            for (String item : items) {
                row = sheet.getRow(r);
                if (row == null) row = sheet.createRow(r);
                r++;
                row.createCell(c).setCellValue(item);
            }
            //create names for the item list constraints, each named from the current key
            colLetter = CellReference.convertNumToColString(c);
            namedRange = workbook.createName();
            namedRange.setNameName(key);
            reference = "ListSheet!$" + colLetter + "$2:$" + colLetter + "$" + r;
            namedRange.setRefersToFormula(reference);
            c++;
        }

        //create name for Categories list constraint
        colLetter = CellReference.convertNumToColString((c - 1));
        namedRange = workbook.createName();
        namedRange.setNameName("Categories");
        reference = "ListSheet!$A$1:$" + colLetter + "$1";
        namedRange.setRefersToFormula(reference);

        sheet.setSelected(false);
        sheet = workbook.createSheet("SMART PMWB");
        sheet.createRow(0).createCell(0).setCellValue("Release");
        sheet.getRow(0).createCell(1).setCellValue("Milestone");
        sheet.setActiveCell(new CellAddress("A2"));
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        //data validations
        DataValidationHelper dvHelper = sheet.getDataValidationHelper();

        //data validation for categories in A2:
        DataValidationConstraint dvConstraint = dvHelper.createFormulaListConstraint("Categories");
        CellRangeAddressList addressList = new CellRangeAddressList(1, 1, 0, 0);
        DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
        sheet.addValidationData(validation);

        //data validation for items of the selected category in B2:
        dvConstraint = dvHelper.createFormulaListConstraint("INDIRECT($A$2)");
        addressList = new CellRangeAddressList(1, 1, 1, 1);
        validation = dvHelper.createValidation(dvConstraint, addressList);
        sheet.addValidationData(validation);

        workbook.setSheetHidden(0, true);
        workbook.setActiveSheet(1);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
