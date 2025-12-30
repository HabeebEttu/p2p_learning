package com.habeeb.p2plearn.models;


import lombok.Getter;

@Getter
public enum ArticleCategory {
    WEB_DEV("Web Development"),
    MOBILE_DEV("Mobile Development"),
    DATA_SCIENCE("Data Science"),
    GAME_DEV("Game Development"),
    AI("Artificial Intelligence"),
    BLOCKCHAIN("Blockchain"),
    CYBER_SECURITY("Cyber Security"),
    CLOUD_COMPUTING("Cloud Computing"),
    DEVOPS("DevOps");

    private final String displayName;

    ArticleCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getValue() {
        return this.name();
    }
}
