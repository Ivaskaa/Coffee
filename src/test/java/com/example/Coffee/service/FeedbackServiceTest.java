package com.example.Coffee.service;

import com.example.Coffee.entities.Admin;
import com.example.Coffee.entities.Feedback;
import com.example.Coffee.repository.AdminRepository;
import com.example.Coffee.repository.FeedbackRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FeedbackServiceTest {
    @Autowired
    private FeedbackService feedbackService;
    @MockBean
    private FeedbackRepository feedbackRepository;

    @Test
    void findSortingPage() {
        feedbackService.findSortingPage(1, "id", "ASC");
        Sort sort = Sort.by(Sort.Direction.valueOf("ASC"), "id");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Mockito.verify(feedbackRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void update() {
        Feedback feedback = new Feedback();
        feedback.setEmail("email@gmail.com");
        feedback.setPhone("phone");
        feedback.setName("name");
        feedback.setStatus("status");

        Mockito.doReturn(Optional.of(new Feedback()))
                .when(feedbackRepository)
                .findById(1L);

        Feedback checkedFeedback = feedbackService.update(1L, feedback);
        Assertions.assertEquals(feedback, checkedFeedback);
        Mockito.verify(feedbackRepository, Mockito.times(1)).save(feedback);
        Mockito.verify(feedbackRepository, Mockito.times(1)).findById(1L);
    }

    @Test
    void deleteById() {
        feedbackService.deleteById(1L);
        Mockito.verify(feedbackRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void findById() {
        Mockito.doReturn(Optional.of(new Feedback()))
                .when(feedbackRepository)
                .findById(1L);
        feedbackService.findById(1L);
        Mockito.verify(feedbackRepository, Mockito.times(1)).findById(1L);
    }
}