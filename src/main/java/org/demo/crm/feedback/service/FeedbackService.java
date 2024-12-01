package org.demo.crm.feedback.service;

import org.demo.crm.auth.repository.UserRepository;
import org.demo.crm.customer.repository.CustomerRepository;
import org.demo.crm.feedback.dto.FeedbackRequestDTO;
import org.demo.crm.feedback.dto.FeedbackResponseDTO;
import org.demo.crm.feedback.entity.Feedback;
import org.demo.crm.feedback.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    public FeedbackResponseDTO addFeedback(FeedbackRequestDTO feedbackRequestDTO) {
        // Validate if customer exists
        if (!customerRepository.existsById(feedbackRequestDTO.getCustomerId())) {
            throw new IllegalArgumentException(
                    "Customer with ID " + feedbackRequestDTO.getCustomerId() + " does not exist."
            );
        }

        // Validate if salesperson exists and has the role "Salesperson"
        if (!userRepository.existsByUserIdAndRole(feedbackRequestDTO.getSalespersonId(), "Salesperson")) {
            throw new IllegalArgumentException(
                    "Salesperson with ID " + feedbackRequestDTO.getSalespersonId() + " does not exist or is not a salesperson."
            );
        }

        // Validate rating range
        if (feedbackRequestDTO.getRating() < 1 || feedbackRequestDTO.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        Feedback feedback = new Feedback();
        feedback.setCustomerId(feedbackRequestDTO.getCustomerId());
        feedback.setSalespersonId(feedbackRequestDTO.getSalespersonId());
        feedback.setRating(feedbackRequestDTO.getRating());
        feedback.setComments(feedbackRequestDTO.getComments());
        feedback.setDate(feedbackRequestDTO.getDate());

        Feedback savedFeedback = feedbackRepository.save(feedback);

        return toResponseDTO(savedFeedback);
    }

    public List<FeedbackResponseDTO> getFeedbackByCustomer(Long customerId) {
        // Validate if customer exists
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException(
                    "Customer with ID " + customerId + " does not exist."
            );
        }

        List<Feedback> feedbacks = feedbackRepository.findByCustomerId(customerId);
        return feedbacks.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public List<FeedbackResponseDTO> getFeedbackBySalesperson(Long salespersonId) {
        // Validate if salesperson exists and has the role "Salesperson"
        if (!userRepository.existsByUserIdAndRole(salespersonId, "Salesperson")) {
            throw new IllegalArgumentException(
                    "Salesperson with ID " + salespersonId + " does not exist or is not a salesperson."
            );
        }

        List<Feedback> feedbacks = feedbackRepository.findBySalespersonId(salespersonId);
        return feedbacks.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    private FeedbackResponseDTO toResponseDTO(Feedback feedback) {
        FeedbackResponseDTO response = new FeedbackResponseDTO();
        response.setFeedbackId(feedback.getFeedbackId());
        response.setCustomerId(feedback.getCustomerId());
        response.setSalespersonId(feedback.getSalespersonId());
        response.setRating(feedback.getRating());
        response.setComments(feedback.getComments());
        response.setDate(feedback.getDate());
        return response;
    }
}
