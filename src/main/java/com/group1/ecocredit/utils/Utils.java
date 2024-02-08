package com.group1.ecocredit.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class Utils {

    public static String hash(String stringToHash) {

        return Hashing.sha256()
                .hashString(stringToHash, StandardCharsets.UTF_8)
                .toString();

    }
}
