package com.samiach.exporttoexceldemo.service;

import com.samiach.exporttoexceldemo.model.POC.*;
import com.samiach.exporttoexceldemo.utility.POC_ExcelExportUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class POC_ServiceImpl implements POC_Service {
    @Override
    public boolean exportReleaseMilestoneSubmilestoneToExcel(HttpServletResponse response) throws IOException {
        Map<String, Release> data = new HashMap<String, Release>();

        Release release01 = new Release("Release_01", Arrays.asList(
                new Milestone("Milestone_01", Arrays.asList(
                        new Submilestone("Submilestone_01"),
                        new Submilestone("Submilestone_02"),
                        new Submilestone("Submilestone_03")
                )),
                new Milestone("Milestone_02", Arrays.asList(
                        new Submilestone("Submilestone_04"),
                        new Submilestone("Submilestone_05"),
                        new Submilestone("Submilestone_06")
                ))
        ));

        Release release02 = new Release("Release_02", Arrays.asList(
                new Milestone("Milestone_03", Arrays.asList(
                        new Submilestone("Submilestone_07"),
                        new Submilestone("Submilestone_08")
                )),
                new Milestone("Milestone_04", Arrays.asList(
                        new Submilestone("Submilestone_09"),
                        new Submilestone("Submilestone_10"),
                        new Submilestone("Submilestone_11"),
                        new Submilestone("Submilestone_12")
                )),
                new Milestone("Milestone_05", Arrays.asList(
                        new Submilestone("Submilestone_13"),
                        new Submilestone("Submilestone_14")
                ))
        ));

        Release release03 = new Release("Release_03", Arrays.asList(
                new Milestone("Milestone_06", Arrays.asList(
                        new Submilestone("Submilestone_15")
                )),
                new Milestone("Milestone_07", Arrays.asList(
                        new Submilestone("Submilestone_16"),
                        new Submilestone("Submilestone_17"),
                        new Submilestone("Submilestone_18")
                ))
        ));
        data.put("Release_01", release01);
        data.put("Release_02", release02);
        data.put("Release_03", release03);

        POC_ExcelExportUtil pocExcelExportUtil = new POC_ExcelExportUtil(data);
        pocExcelExportUtil.exportDataToExcel(response);
        // pocExcelExportUtil.testExport();

//        Country country = new Country("CountryA", Arrays.asList(
//                new State("StateA", Arrays.asList(
//                        new City("cityA", Arrays.asList(
//                                new Town("townA", 4),
//                                new Town("townB", 5),
//                                new Town("townC", 42),
//                                new Town("townD", 67)
//                        )),
//                        new City("cityB", Arrays.asList(
//                                new Town("townE", 65),
//                                new Town("townF", 14),
//                                new Town("townG", 24),
//                                new Town("townH", 33)
//                        )))),
//                new State("StateB", Arrays.asList(
//                        new City("cityC", Arrays.asList(
//                                new Town("townI", 9),
//                                new Town("townJ", 5),
//                                new Town("townK", 12),
//                                new Town("townL", 4)
//                        )),
//                        new City("cityD", Arrays.asList(
//                                new Town("townM", 5),
//                                new Town("townN", 7),
//                                new Town("townO", 24),
//                                new Town("townP", 6)
//                        ))))));
//
//        Map<State, Map<City, List<Town>>> stateToCitiesToTowns =
//                country.getStates().stream()
//                        .collect(Collectors.toMap(state -> state, state -> state.getCities().stream()
//                                .collect(Collectors.toMap(city -> city, City::getTowns))));
//
//        System.out.println(stateToCitiesToTowns);

        return true;
    }
}
