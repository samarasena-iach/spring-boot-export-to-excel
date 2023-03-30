package com.samiach.exporttoexceldemo.model.POC;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Release {
    private String release;
    private List<Milestone> milestones;
}
