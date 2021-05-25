package com.bbchat.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thisisbadBao
 * @Date 2021--25-12:34 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    String from_who;

    String to_who;
}
