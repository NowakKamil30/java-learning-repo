package com.nowak.kamil.hibernatejavamapping.listener;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PreInsertListener implements PreInsertEventListener {
    @Override
    public boolean onPreInsert(PreInsertEvent preInsertEvent) {
        log.info("PreInsertListener onPreInsert");
        return false;
    }
}
