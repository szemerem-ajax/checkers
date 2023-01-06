package org.szemeremajax.backend.services;

import org.szemeremajax.backend.models.Alliance;

import java.util.Optional;

/**
 * Provides a contract for a service which can manage authentication for ongoing games.
 */
public interface AuthService {
    /**
     * Determines whether the given auth id is authorized to play as the given side in the given game.
     * @param gameId The game's id.
     * @param authId The auth id.
     * @param alliance The side to play as.
     * @return Whether the auth id is authorized.
     */
    boolean isAuthorized(String gameId, String authId, Alliance alliance);

    /**
     * Allocates an auth id for the given game as the given side.
     * @param gameId The game's id.
     * @param alliance The side.
     * @return The auth id if the allocation was successful.
     */
    Optional<String> allocateForGame(String gameId, Alliance alliance);

    /**
     * Determines whether the given side is still available for allocation in the given game.
     * @param gameId The game's id.
     * @param alliance The side.
     * @return Whether the side is still available for allocation.
     */
    boolean isFree(String gameId, Alliance alliance);

    /**
     * Determines whether the given game can be played.
     * @param gameId The game's id.
     * @return Whether the game can be played.
     */
    boolean canBePlayed(String gameId);
}
