package org.kt.parttime.work.controller;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.kt.parttime.common.annotation.AuthenticatedUser;
import org.kt.parttime.common.dto.TimeQuery;
import org.kt.parttime.user.dto.AuthenticatedUserDto;
import org.kt.parttime.work.service.WorkService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wage")
public class WageApiController {
    private final WorkService workService;

    @GetMapping
    public ResponseEntity<byte[]> createExcel(
            @AuthenticatedUser AuthenticatedUserDto user,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            HttpServletResponse response
    ){
        if(Objects.isNull(user) || !user.isAdmin()) return null;
        TimeQuery timeQuery = TimeQuery.of(year, month);

        try {
            Workbook workbook = workService.makeExcel(timeQuery);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] excelContent = outputStream.toByteArray();

            String encodedFileName = URLEncoder.encode(
                    String.format("생협근로장학생급여(%d.%d).xlsx", timeQuery.getYear(), timeQuery.getMonth()),
                    StandardCharsets.UTF_8
            ).replaceAll("\\+", "%20");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData(
                    "attachment", encodedFileName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(excelContent);


        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
