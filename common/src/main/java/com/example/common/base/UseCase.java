package com.example.common.base;

public interface UseCase<Rq, Rp> {
    Rp execute(Rq request);
}
