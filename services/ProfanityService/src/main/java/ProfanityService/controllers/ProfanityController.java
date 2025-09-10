package ProfanityService.controllers;

import ProfanityService.service.BannedWordService_I;
import ProfanityService.util.Result;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profanity")
public class ProfanityController {

    private final BannedWordService_I bannedWordService;

    public ProfanityController(BannedWordService_I bannedWordService) {
        this.bannedWordService = bannedWordService;
    }

    @PostMapping(path="/check",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isProfanity(@RequestBody String content) {
        Result results = bannedWordService.check(content);
        boolean profane = results.profane();
        return ResponseEntity.status(HttpStatus.OK).body(profane);
    }
}
