package com.example.demo.controller;


import com.example.demo.DTO.GheDTO;
import com.example.demo.service.GheService;
import com.example.demo.service.PhimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ghe")
@CrossOrigin(origins = "http://localhost:4200")
public class GheController {
    @Autowired
    private GheService gheService;

    @GetMapping("/{scheduleId}/rooms/{roomId}/movies/{phimId}/seat-info")
    public ResponseEntity<GheDTO> getSeatInfo(
            @PathVariable Long scheduleId,
            @PathVariable Long roomId,
            @PathVariable Long phimId) {

        GheDTO gheDTO = gheService.getSeatInfo(scheduleId, roomId, phimId);
        return ResponseEntity.ok(gheDTO);
    }
}
