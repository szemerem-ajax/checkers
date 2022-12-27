package org.szemeremajax.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.szemeremajax.backend.models.Alliance;
import org.szemeremajax.backend.services.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(originPatterns = "*")
public class AuthController {
    @Autowired
    AuthService authService;

    @PutMapping("/join/{gameId}/{side}")
    String join(@PathVariable String gameId, @PathVariable Alliance side) {
        var result = authService.allocateForGame(gameId, side);
        return result.orElseThrow();
    }

    @GetMapping("/free/{gameId}")
    @ResponseBody
    FreeResult free(@PathVariable String gameId) {
        boolean whiteFree = authService.isFree(gameId, Alliance.WHITE);
        boolean blackFree = authService.isFree(gameId, Alliance.BLACK);

        return new FreeResult(whiteFree, blackFree);
    }

    @GetMapping("/canStart/{gameId}")
    boolean canStart(@PathVariable String gameId) {
        return authService.canBePlayed(gameId);
    }

    @DeleteMapping("/drop/{gameId}/{side}")
    void drop(@PathVariable String gameId, @PathVariable Alliance side) {
        authService.dropAuth(gameId, side);
    }

    public record FreeResult(boolean white, boolean black) {
    }
}
