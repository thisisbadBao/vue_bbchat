package com.bbchat.dao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thisisbadBao
 * @Date 2021--25-10:03 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    String old_name;
    String new_name;
}
