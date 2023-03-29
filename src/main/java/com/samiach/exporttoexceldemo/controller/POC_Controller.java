package com.samiach.exporttoexceldemo.controller;

import com.samiach.exporttoexceldemo.service.POC_Service;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/poc")
@AllArgsConstructor
public class POC_Controller {

    private POC_Service pocService;

    @GetMapping("/export-to-excel-poc-2")
    public void exportToExcel_ReleaseMilestoneSubmilestone(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Release_Milestone_Submilestone.xlsx";

        response.setHeader(headerKey, headerValue);
        pocService.exportReleaseMilestoneSubmilestoneToExcel(response);
    }
}
