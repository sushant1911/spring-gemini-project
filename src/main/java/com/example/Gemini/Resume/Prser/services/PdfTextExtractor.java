package com.example.Gemini.Resume.Prser.services;



import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public class PdfTextExtractor {

    public static String extractText(MultipartFile file) {
        try (InputStream input = file.getInputStream();
             PDDocument document = PDDocument.load(input)) {

            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to extract PDF text: " + e.getMessage();
        }
    }
}