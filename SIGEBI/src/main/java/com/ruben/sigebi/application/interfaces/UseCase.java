package com.ruben.sigebi.application.interfaces;

public interface UseCase<RESPONSE, COMMAND> {
    RESPONSE execute(COMMAND commandRequest);

}
