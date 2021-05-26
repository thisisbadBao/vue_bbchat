package com.bbchat.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thisisbadBao
 * @Date 2021--25-10:14 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Code {
    String name;
    String old_code;
    String new_code;
}
