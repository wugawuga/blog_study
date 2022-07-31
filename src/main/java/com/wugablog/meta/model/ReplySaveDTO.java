package com.wugablog.meta.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReplySaveDTO {

    private Long userId;
    private Long boardId;
    private String content;

}
