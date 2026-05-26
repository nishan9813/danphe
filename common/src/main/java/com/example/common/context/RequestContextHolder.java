package com.example.common.context;

public final class RequestContextHolder {
    private static final ThreadLocal<RequestContext> HOLDER = new ThreadLocal<>();

    private RequestContextHolder() {}

    private static void set(RequestContext user) {
        HOLDER.set(user);
    }

    public static RequestContext get() {
        RequestContext user = HOLDER.get();
        if (user == null){
            throw new IllegalStateException("No authenticated user in current thread local");
        }
        return user;
    }

    public static boolean isPresent() {
        return HOLDER.get() != null;
    }

    public static void clear() {
        HOLDER.remove();
    }
}
