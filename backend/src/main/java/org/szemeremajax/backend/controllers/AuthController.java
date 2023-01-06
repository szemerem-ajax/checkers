package org.szemeremajax.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.szemeremajax.backend.models.Alliance;
import org.szemeremajax.backend.services.AuthService;

/**
 * Provides endpoints for authenticating into a game.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(originPatterns = "*")
public class AuthController {
    @Autowired
    private AuthService authService;

    /**
     * Joins the game with the given id as the given side.
     * @param gameId The game's id to join
     * @param side The side to join as.
     * @return The auth id.
     */
    @PutMapping("/join/{gameId}/{side}")
    String join(@PathVariable String gameId, @PathVariable Alliance side) {
        return authService.allocateForGame(gameId, side).orElseThrow();
    }

    /**
     * Gets whether the given authId is authorized to make moves as the given side in the given game.
     * @param gameId The game's id.
     * @param side The side.
     * @param body The auth id.
     * @return Whether we are allowed to make moves.
     */
    @PostMapping("/isAuthorized/{gameId}/{side}")
    boolean isAuthorized(@PathVariable String gameId, @PathVariable Alliance side, @RequestBody String body) {
        return authService.isAuthorized(gameId, body, side);
    }

    /**
     * Gets what sides are still available to join.
     * @param gameId The game's id.
     * @return What sides are still available.
     */
    @GetMapping("/free/{gameId}")
    @ResponseBody
    FreeResult free(@PathVariable String gameId) {
        boolean whiteFree = authService.isFree(gameId, Alliance.WHITE);
        boolean blackFree = authService.isFree(gameId, Alliance.BLACK);

        return new FreeResult(whiteFree, blackFree);
    }

    /**
     * Determines whether the game can be played.
     * @param gameId The game's id.
     * @return Whether both sides have joined.
     */
    @GetMapping("/canStart/{gameId}")
    boolean canStart(@PathVariable String gameId) {
        return authService.canBePlayed(gameId);
    }

    private record FreeResult(boolean white, boolean black) {
    }
}
