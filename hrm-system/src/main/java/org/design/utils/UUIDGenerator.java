package org.design.utils;

import java.util.UUID;

public class UUIDGenerator {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            UUID uuid = UUID.randomUUID();
            System.out.println(uuid);
        }
    }
}
