package com.samiach.exporttoexceldemo.service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface POC_Service {
    boolean exportReleaseMilestoneSubmilestoneToExcel(HttpServletResponse response) throws IOException;
}
