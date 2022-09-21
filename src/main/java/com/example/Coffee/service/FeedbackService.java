package com.example.Coffee.service;

import com.example.Coffee.entities.Feedback;
import com.example.Coffee.repository.FeedbackRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;

    public Object findSortingPage(Integer currentPage, String sortingField, String sortingDirection) {
        Sort sort = Sort.by(Sort.Direction.valueOf(sortingDirection), sortingField);
        Pageable pageable = PageRequest.of(currentPage - 1, 10, sort);
        Page<Feedback> feedbackPage = feedbackRepository.findAll(pageable);
        log.info("get feedback page. page: {}, field: {}, direction: {}", currentPage - 1, sortingField, sortingDirection);
        return feedbackPage;
    }

    public Feedback update(Long id, Feedback feedbackForm) {
        log.info("update feedback. id: {}, feedback: {}", id, feedbackForm);
        Feedback feedback = feedbackRepository.findById(id).orElseThrow();
        feedback.setName(feedbackForm.getName());
        feedback.setPhone(feedbackForm.getPhone());
        feedback.setEmail(feedbackForm.getEmail());
        feedback.setStatus(feedbackForm.getStatus());
        feedbackRepository.save(feedback);
        log.info("success");
        return feedback;
    }

    public void deleteById(Long id) {
        feedbackRepository.deleteById(id);
        log.info("delete feedback by id: {}", id);
    }

    public Feedback findById(Long id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow();
        log.info("get feedback by id: {}", id);
        return feedback;
    }
}
