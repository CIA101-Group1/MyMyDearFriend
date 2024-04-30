package com.tibame.group1.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfigProperties {

    /** 是否使用local環境運行 */
    private Boolean useLocal;

    /** 跨域允許網域 */
    private String corsAllowOrigin;
}
