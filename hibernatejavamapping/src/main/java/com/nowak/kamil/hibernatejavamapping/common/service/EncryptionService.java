package com.nowak.kamil.hibernatejavamapping.common.service;

public interface EncryptionService {

    String encrypt(String freeText);

    String decrypt(String encryptedText);
}
