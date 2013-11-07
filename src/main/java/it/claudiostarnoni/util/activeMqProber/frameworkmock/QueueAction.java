package it.claudiostarnoni.util.activeMqProber.frameworkmock;


import intentfactory.core.domain.actions.Action;

import java.util.UUID;

public class QueueAction implements Action {

    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public QueueAction() {
    }

    @Override
    public String getUuid() {
        return UUID.randomUUID().toString();
    }
}
