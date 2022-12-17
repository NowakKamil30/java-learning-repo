package com.nowak.kamil.hibernatejavamapping.listener;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PostLoadListener implements PostLoadEventListener {
    @Override
    public void onPostLoad(PostLoadEvent postLoadEvent) {
        log.info("PostLoadListener onPostLoad");
    }
}
