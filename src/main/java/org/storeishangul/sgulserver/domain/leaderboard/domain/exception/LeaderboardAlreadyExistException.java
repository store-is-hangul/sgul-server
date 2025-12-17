package org.storeishangul.sgulserver.domain.leaderboard.domain.exception;

import static org.storeishangul.sgulserver.common.exception.ApplicationExceptionType.LEADERBOARD_ALREADY_EXIST_EXCEPTION;

import org.storeishangul.sgulserver.common.exception.CustomException;

public class LeaderboardAlreadyExistException extends CustomException {

    public LeaderboardAlreadyExistException() {

        super(LEADERBOARD_ALREADY_EXIST_EXCEPTION);
    }
}
