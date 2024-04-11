package org.example.clickstack.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.clickstack.Entity.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(
            Set.of(
                    PHOTO_DELETE,
                    PHOTO_CREATE,
                    PHOTO_UPDATE,
                    PHOTO_READ,
                    ALBUM_DELETE,
                    ALBUM_CREATE,
                    ALBUM_UPDATE,
                    ALBUM_READ
            )
    );
    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
