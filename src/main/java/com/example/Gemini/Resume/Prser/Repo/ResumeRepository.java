package com.example.Gemini.Resume.Prser.Repo;

import com.example.Gemini.Resume.Prser.Entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {}
