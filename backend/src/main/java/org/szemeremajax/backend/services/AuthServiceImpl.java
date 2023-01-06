package org.szemeremajax.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.szemeremajax.backend.models.Alliance;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

/**
 * Provides an implementation of {@link AuthService}.
 */
@Component
public class AuthServiceImpl extends HashMap<String, AuthServiceImpl.AuthRecord> implements AuthService {
    @Autowired
    private BoardService boardService;

    @Override
    public boolean isAuthorized(String gameId, String authId, Alliance alliance) {
        boardService.lookupBoard(gameId).orElseThrow();
        var record = getRecord(gameId);
        if (alliance == Alliance.WHITE) {
            return record.whiteId != null && record.whiteId.equals(authId);
        } else {
            return record.whiteId != null && record.blackId.equals(authId);
        }
    }

    @Override
    public synchronized Optional<String> allocateForGame(String gameId, Alliance alliance) {
        boardService.lookupBoard(gameId).orElseThrow(); // Check if we have a game with that id
        var record = getRecord(gameId);
        var id = UUID.randomUUID().toString();
        if (record.getWhiteId() == null && alliance == Alliance.WHITE) {
            record.setWhiteId(id);
            return Optional.of(id);
        } else if (record.getBlackId() == null && alliance == Alliance.BLACK) {
            record.setBlackId(id);
            return Optional.of(id);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public synchronized boolean isFree(String gameId, Alliance alliance) {
        var record = getRecord(gameId);
        if (alliance == Alliance.WHITE) {
            return record.getWhiteId() == null;
        } else {
            return record.getBlackId() == null;
        }
    }

    @Override
    public synchronized boolean canBePlayed(String gameId) {
        var record = get(gameId);
        return record.isPlayable();
    }

    private AuthRecord getRecord(String id) {
        if (containsKey(id))
            return get(id);

        var record = new AuthRecord();
        put(id, record);
        return record;
    }

    static class AuthRecord {
        private String whiteId, blackId;

        String getWhiteId() {
            return whiteId;
        }

        void setWhiteId(String whiteId) {
            this.whiteId = whiteId;
        }

        String getBlackId() {
            return blackId;
        }

        void setBlackId(String blackId) {
            this.blackId = blackId;
        }

        boolean isPlayable() {
            return whiteId != null && blackId != null;
        }
    }
}
