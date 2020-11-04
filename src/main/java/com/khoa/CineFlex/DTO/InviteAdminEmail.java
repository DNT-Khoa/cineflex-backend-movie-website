package com.khoa.CineFlex.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InviteAdminEmail {
    private String subject;
    private String recipient;
    private String body;
    private String joinLink;
}
