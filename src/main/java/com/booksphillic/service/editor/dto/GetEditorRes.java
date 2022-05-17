package com.booksphillic.service.editor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class GetEditorRes {

    private String editorName;
    private String description;
    private long postCount;
}
