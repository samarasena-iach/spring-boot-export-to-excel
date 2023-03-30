package com.samiach.exporttoexceldemo.model.POC;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Milestone {
    private String milestone;
    private List<Submilestone> submilestones;
}
