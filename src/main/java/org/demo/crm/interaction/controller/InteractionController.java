package org.demo.crm.interaction.controller;

import org.demo.crm.auth.model.ApiResponse;
import org.demo.crm.interaction.dto.InteractionRequest;
import org.demo.crm.interaction.dto.InteractionResponse;
import org.demo.crm.interaction.service.InteractionService;
import org.demo.crm.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api/interactions")
public class InteractionController {

    @Autowired
    private InteractionService interactionService;

    @Autowired
    private JwtUtils jwtUtils;

    @GetMapping
    public ApiResponse<List<InteractionResponse>> getAllInteractions(HttpServletRequest httpRequest) {
        validateRequest(httpRequest);
        List<InteractionResponse> interactions = interactionService.getAllInteractions();
        return ApiResponse.success(interactions, "Interactions retrieved successfully");
    }

    @GetMapping("/{id}")
    public ApiResponse<InteractionResponse> getInteractionById(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {
        validateRequest(httpRequest);
        InteractionResponse interaction = interactionService.getInteractionById(id);
        return ApiResponse.success(interaction, "Interaction retrieved successfully");
    }

    @PostMapping
    public ApiResponse<InteractionResponse> createInteraction(
            @RequestBody InteractionRequest request,
            HttpServletRequest httpRequest) {
        validateRequestForAdmin(httpRequest);
        InteractionResponse createdInteraction = interactionService.createInteraction(request);
        return ApiResponse.success(createdInteraction, "Interaction created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<InteractionResponse> updateInteraction(
            @PathVariable Long id,
            @RequestBody InteractionRequest request,
            HttpServletRequest httpRequest) {
        validateRequestForAdmin(httpRequest);
        InteractionResponse updatedInteraction = interactionService.updateInteraction(id, request);
        return ApiResponse.success(updatedInteraction, "Interaction updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteInteraction(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {
        validateRequest(httpRequest);
        interactionService.deleteInteraction(id);
        return ApiResponse.success("Interaction deleted successfully", "Interaction deleted successfully");
    }

    private void validateRequest(HttpServletRequest httpRequest) {
        String token = jwtUtils.extractToken(httpRequest);
        if (token == null || !jwtUtils.validateToken(token)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }
    }

    private void validateRequestForAdmin(HttpServletRequest httpRequest) {
        String token = jwtUtils.extractToken(httpRequest);
        if (token == null || !jwtUtils.validateToken(token)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }
        String role = jwtUtils.extractClaims(token).get("role", String.class);
        if (!"Admin".equals(role)) {
            throw new SecurityException("Access denied: Admin role required");
        }
    }
}
