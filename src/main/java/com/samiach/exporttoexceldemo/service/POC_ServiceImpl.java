package com.samiach.exporttoexceldemo.service;

import com.samiach.exporttoexceldemo.utility.POC_ExcelExportUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class POC_ServiceImpl implements POC_Service {
    @Override
    public boolean exportReleaseMilestoneSubmilestoneToExcel(HttpServletResponse response) throws IOException {
        Map<String, String[]> data = new HashMap<String, String[]>();
        data.put("Release_01", new String[]{"Milestone 01", "Milestone 02", "Milestone 03"});
        data.put("Release_02", new String[]{"Milestone 04", "Milestone 05", "Milestone 06"});
        data.put("Release_03", new String[]{"Milestone 07", "Milestone 08", "Milestone 09", "Milestone 10"});

        POC_ExcelExportUtil pocExcelExportUtil = new POC_ExcelExportUtil(data);
        pocExcelExportUtil.exportDataToExcel(response);
        return true;
    }
}
