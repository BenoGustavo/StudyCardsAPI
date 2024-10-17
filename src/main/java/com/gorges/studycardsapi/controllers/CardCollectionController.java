package com.gorges.studycardsapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gorges.studycardsapi.dto.CardDto.CardCollection;
import com.gorges.studycardsapi.models.CardCollectionEntity;
import com.gorges.studycardsapi.responses.Response;
import com.gorges.studycardsapi.services.CardCollectionService;

@RestController
@RequestMapping("/api/card-collection")
public class CardCollectionController {

    @Autowired
    private CardCollectionService cardCollectionService;

    @PostMapping("/create")
    public ResponseEntity<Response<CardCollectionEntity>> create(@RequestBody CardCollection cardCollection) {
        CardCollectionEntity result = cardCollectionService.create(cardCollection);

        Response<CardCollectionEntity> response = new Response.Builder<CardCollectionEntity>()
                .message("Success")
                .status(200)
                .data(result)
                .build();

        return ResponseEntity.ok(response);
    }
}

// REALIZEI ALGUMAS MUDANÇAS NO JWT UTIL PARA CONSEGUIR O EMAIL DO USUARIO E
// REFATOREI O CARD SERVICE, AGR O CARD COLLECTION CREATE TA REDONDO