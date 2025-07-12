package com.jagt.reader.shared.i18n.domain.service;

public interface MessageProvider {
    String getMessage(String key);
    
    String getMessage(String key, Object[] args);
}
