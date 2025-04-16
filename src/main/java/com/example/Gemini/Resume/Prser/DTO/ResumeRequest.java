package com.example.Gemini.Resume.Prser.DTO;

import lombok.Data;

@Data
public class ResumeRequest {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
