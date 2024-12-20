package reindeer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reindeer.model.ReindeerResult;
import reindeer.model.ReindeerToCreateRequest;
import reindeer.service.ReindeerService;

import java.util.UUID;

@RestController
@RequestMapping("/reindeer")
public class ReindeerController {
    private final ReindeerService reindeerService;

    public ReindeerController(ReindeerService reindeerService) {
        this.reindeerService = reindeerService;
    }

    @PostMapping
    public ResponseEntity<?> createReindeer(@RequestBody ReindeerToCreateRequest request) {
        return reindeerService.create(request.toDomain())
                .fold(
                        error -> ResponseEntity.status(HttpStatus.CONFLICT).body(error),
                        success -> ResponseEntity.status(HttpStatus.CREATED).body(success)
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReindeer(@PathVariable("id") UUID id) {
        return reindeerService.get(id)
                .fold(
                        error -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error),
                        success -> ResponseEntity.ok(ReindeerResult.from(success))
                );
    }
}
