package org.example.clickstack.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public enum Permission {

    PHOTO_READ("photo:read"),
    PHOTO_UPDATE("photo:update"),
    PHOTO_CREATE("photo:create"),
    PHOTO_DELETE("photo:delete"),

    ALBUM_READ("album:read"),
    ALBUM_UPDATE("album:update"),
    ALBUM_CREATE("album:create"),
    ALBUM_DELETE("album:delete")

    ;
    @Getter
    private final String permission;
}
