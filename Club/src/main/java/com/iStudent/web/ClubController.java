package com.iStudent.web;

import com.iStudent.model.DTOs.ClubDTO;
import com.iStudent.service.ClubService;
import com.iStudent.model.entity.Club;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clubs")
public class ClubController {

    private final ClubService clubService;

    @Autowired
    public ClubController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    public ResponseEntity<List<ClubDTO>> getAllClubs() {
        return ResponseEntity.
                ok(clubService.getAllClubs());
    }

    @GetMapping("/{id}")
        public ResponseEntity<ClubDTO> getClubById(@PathVariable("id") Long clubId) {
             return this.clubService.getClubById(clubId)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/club/{id}")
    private ResponseEntity<ClubDTO> getClubDetails(@PathVariable("id") Long id) {
        return this.clubService.getClubById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClubDTO> addClub(@Valid @RequestBody ClubDTO clubDTO,
                                           UriComponentsBuilder uriComponentsBuilder) {

        long newClubId = clubService.addClub(clubDTO);

        return ResponseEntity
                .created(uriComponentsBuilder.path("/clubs/{id}")
                        .build(newClubId))
                .build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ClubDTO> changeClubName(@Valid @RequestBody ClubDTO clubDTO,
                                                  @PathVariable("id") Long id) {

        ClubDTO club = clubService.changeClubName(id, clubDTO);

        if (club == null) {
            return ResponseEntity.
                    notFound().
                    build();
        } else {
            return ResponseEntity.
                    ok(club);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClubDTO> deleteClubById(@PathVariable("id") Long clubId) {
        this.clubService.deleteClubById(clubId);

        return ResponseEntity
                .noContent()
                .build();
    }
}
