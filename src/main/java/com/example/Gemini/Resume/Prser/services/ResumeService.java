package com.example.Gemini.Resume.Prser.services;

import com.example.Gemini.Resume.Prser.Entity.Resume;
import com.example.Gemini.Resume.Prser.Repo.ResumeRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ResumeService {
    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private GeminiClient geminiClient;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public Resume processResume(MultipartFile file) {
        try {
            String content = PdfTextExtractor.extractText(file);
            String responseJson = geminiClient.extractDetails(content);

            // Step 1: Parse as JsonNode
            JsonNode root = objectMapper.readTree(responseJson);

            // Step 2: Extract the inner 'output' node
            JsonNode resumeNode = root
                    .path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");
            String rawText = resumeNode.asText()
                    .replaceAll("```json", "")
                    .replaceAll("```", "")
                    .trim();

            // Step 3: Map to Resume entity
            Resume resume = objectMapper.readValue(rawText, Resume.class);

            return resumeRepository.save(resume);
        } catch (Exception e) {
            throw new RuntimeException("Resume processing failed", e);
        }
    }
}
