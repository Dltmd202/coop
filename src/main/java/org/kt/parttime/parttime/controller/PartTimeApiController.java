package org.kt.parttime.parttime.controller;

import lombok.RequiredArgsConstructor;
import org.kt.parttime.parttime.dto.PartTimeGroupDto;
import org.kt.parttime.parttime.service.PartTimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PartTimeApiController {
    private final PartTimeService partTimeService;

    @GetMapping("/api/group-parttimes")
    public ResponseEntity<List<PartTimeGroupDto>> getPartTimeGroupList(
            @RequestParam Integer year,
            @RequestParam Integer semester
    ){
        return ResponseEntity
                .status(200)
                .body(partTimeService.getPartTimeGroupList(year, semester));
    }
}
