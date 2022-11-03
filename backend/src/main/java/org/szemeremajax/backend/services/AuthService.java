package org.szemeremajax.backend.services;

import org.szemeremajax.backend.models.Alliance;

import java.util.Optional;

public interface AuthService {
    Optional<String> allocateForGame(String gameId, Alliance alliance);
    boolean isFree(String gameId, Alliance alliance);
    boolean canBePlayed(String gameId);
    void dropAuth(String gameId);
    void dropAuth(String gameId, Alliance alliance);
}
