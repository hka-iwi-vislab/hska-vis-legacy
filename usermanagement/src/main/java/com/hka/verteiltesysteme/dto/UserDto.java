package com.hka.verteiltesysteme.dto;

import org.springframework.lang.NonNull;

public record UserDto(@NonNull String username,@NonNull String password1,@NonNull String password2, @NonNull String firstname,@NonNull String lastname) {
}
