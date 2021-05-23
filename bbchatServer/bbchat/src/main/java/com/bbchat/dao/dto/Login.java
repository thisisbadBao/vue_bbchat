package com.bbchat.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.internal.bytebuddy.implementation.bind.annotation.AllArguments;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Login {
    String accountId;
    String code;
}
