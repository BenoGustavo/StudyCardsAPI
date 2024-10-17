package com.gorges.studycardsapi.dto;

import java.util.UUID;

import com.gorges.studycardsapi.utils.enums.CardTypes;

import lombok.Data;

public class CardDto {
    @Data
    public static class Card {
        String question;
        String answer;
        UUID collection;
        boolean isPublic;
        CardTypes cardAnswerType;
    }

    @Data
    public static class CardCollection {
        String subject;
        String description;
    }

    @Data
    public static class CardRequest {
        String question;
        String answer;
        UUID collection;
        boolean isPublic;
        boolean isAnswarable;
        boolean isMultipleChoice;
        boolean isDissertative;
    }
}
