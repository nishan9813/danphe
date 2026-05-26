package com.example.common.utils;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class StringUtils {
    public static boolean isBlankOrNull(String str) {
        return str == null || str.isBlank();
    }

    public static String generateUUID() {
        long timestamp = System.currentTimeMillis();
        long random = ThreadLocalRandom.current().nextLong();
        long randA = random & 0xFFFL;
        long randB = ThreadLocalRandom.current().nextLong() & 0x3FFFFFFFFFFFFFFFL;
        long mostSigBits = (timestamp << 16) | 0x7000L | randA;
        long leastSigBits = 0x8000000000000000L | randB;
        return new UUID(mostSigBits, leastSigBits).toString().replaceAll("-", "");
    }

    public static String capitalize(String value) {
        if (isBlankOrNull(value)) {
            return "";
        } else {
            String[] values = value.split(" ");
            StringBuilder builder = new StringBuilder();
            Arrays.stream(values)
                    .forEach(
                            (val) -> {
                                builder.append(capitalize(val.toLowerCase()));
                                builder.append(" ");
                            });
            return builder.toString().trim();
        }
    }
}
