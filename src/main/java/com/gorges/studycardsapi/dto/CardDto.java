package com.gorges.studycardsapi.dto;

import com.gorges.studycardsapi.models.CardCollectionEntity;

import lombok.Data;

public class CardDto {
    @Data
    public static class Card {
        String question;
        String answer;
        CardCollectionEntity collection;
        boolean isPublic;
        boolean isAnswarable;
        boolean isMultipleChoice;
        boolean isDissertative;
    }

    @Data
    public static class CardCollection {
        String subject;
        String description;
        String owner;
    }

    @Data
    public static class CardRequest {
        String question;
        String answer;
        CardCollectionEntity collection;
        boolean isPublic;
        boolean isAnswarable;
        boolean isMultipleChoice;
        boolean isDissertative;
    }
}
