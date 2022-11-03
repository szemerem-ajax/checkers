package org.szemeremajax.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.szemeremajax.backend.models.Alliance;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Component
public class AuthServiceImpl extends HashMap<String, AuthServiceImpl.AuthRecord> implements AuthService {
    @Autowired
    BoardService boardService;

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

    @Override
    public void dropAuth(String gameId) {
        remove(gameId);
    }

    @Override
    public void dropAuth(String gameId, Alliance alliance) {
        var record = get(gameId);
        if (alliance == Alliance.WHITE) {
            record.setWhiteId(null);
        } else {
            record.setBlackId(null);
        }
    }

    private AuthRecord getRecord(String id) {
        if (containsKey(id))
            return get(id);

        var record = new AuthRecord();
        put(id, record);
        return record;
    }

    public static class AuthRecord {
        private String whiteId, blackId;

        public String getWhiteId() {
            return whiteId;
        }

        public void setWhiteId(String whiteId) {
            this.whiteId = whiteId;
        }

        public String getBlackId() {
            return blackId;
        }

        public void setBlackId(String blackId) {
            this.blackId = blackId;
        }

        public boolean isPlayable() {
            return whiteId != null && blackId != null;
        }
    }
}
