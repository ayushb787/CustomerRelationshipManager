package org.demo.crm.interaction.service;

import org.demo.crm.interaction.dto.InteractionRequest;
import org.demo.crm.interaction.dto.InteractionResponse;
import org.demo.crm.interaction.entity.Interaction;
import org.demo.crm.interaction.repository.InteractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InteractionService {

    @Autowired
    private InteractionRepository interactionRepository;

    public List<InteractionResponse> getAllInteractions() {
        return interactionRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public InteractionResponse getInteractionById(Long interactionId) {
        Interaction interaction = interactionRepository.findById(interactionId)
                .orElseThrow(() -> new RuntimeException("Interaction not found"));
        return mapToResponse(interaction);
    }

    public InteractionResponse createInteraction(InteractionRequest request) {
        Interaction interaction = mapToEntity(request);
        Interaction savedInteraction = interactionRepository.save(interaction);
        return mapToResponse(savedInteraction);
    }

    public InteractionResponse updateInteraction(Long interactionId, InteractionRequest request) {
        Interaction interaction = interactionRepository.findById(interactionId)
                .orElseThrow(() -> new RuntimeException("Interaction not found"));

        interaction.setCustomerId(request.getCustomerId());
        interaction.setSalespersonId(request.getSalespersonId());
        interaction.setInteractionType(request.getInteractionType());
        interaction.setDate(request.getDate());
        interaction.setDetails(request.getDetails());
        interaction.setAttachments(request.getAttachments());

        Interaction updatedInteraction = interactionRepository.save(interaction);
        return mapToResponse(updatedInteraction);
    }

    public void deleteInteraction(Long interactionId) {
        Interaction interaction = interactionRepository.findById(interactionId)
                .orElseThrow(() -> new RuntimeException("Interaction not found"));
        interactionRepository.delete(interaction);
    }

    private Interaction mapToEntity(InteractionRequest request) {
        Interaction interaction = new Interaction();
        interaction.setCustomerId(request.getCustomerId());
        interaction.setSalespersonId(request.getSalespersonId());
        interaction.setInteractionType(request.getInteractionType());
        interaction.setDate(request.getDate());
        interaction.setDetails(request.getDetails());
        interaction.setAttachments(request.getAttachments());
        return interaction;
    }

    private InteractionResponse mapToResponse(Interaction interaction) {
        InteractionResponse response = new InteractionResponse();
        response.setInteractionId(interaction.getInteractionId());
        response.setCustomerId(interaction.getCustomerId());
        response.setSalespersonId(interaction.getSalespersonId());
        response.setInteractionType(interaction.getInteractionType());
        response.setDate(interaction.getDate());
        response.setDetails(interaction.getDetails());
        response.setAttachments(interaction.getAttachments());
        return response;
    }
}
