package org.zhouhy.springsecurity.domain;

import lombok.Data;

@Data
public class SysRole {
    private Integer id;
    private String roleName;
    private String roleDesc;
}
