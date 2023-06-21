package com.nashss.se.beefy.activities.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

/**
 * The type Create goal request.
 */
@JsonDeserialize(builder = CreateGoalRequest.Builder.class)
public class CreateGoalRequest {

    private final String userId;
    private final String name;
    private final String category;
    private final String description;
    private final Double goalAmount;
    private final String priority;
    private final Boolean completionStatus;

    /**
     * Instantiates a new Create goal request.
     *
     * @param userId           the user id
     * @param name             the name
     * @param category         the category
     * @param description      the description
     * @param goalAmount       the goal amount
     * @param priority         the priority
     * @param completionStatus the completion status
     */
    public CreateGoalRequest(String userId, String name, String category, String description, Double goalAmount,
                             String priority,Boolean completionStatus) {
        this.userId = userId;
        this.name = name;
        this.category = category;
        this.description = description;
        this.goalAmount = goalAmount;
        this.priority = priority;
        this.completionStatus = completionStatus;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets goal amount.
     *
     * @return the goal amount
     */
    public Double getGoalAmount() {
        return goalAmount;
    }

    /**
     * Gets priority.
     *
     * @return the priority
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Gets completion status.
     *
     * @return the completion status
     */
    public Boolean getCompletionStatus() {
        return completionStatus;
    }

    @Override
    public String toString() {
        return "CreateGoalRequest{" +
                "userId='" + userId + '\'' +
                ", goalName='" + name + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", goalAmount=" + goalAmount +
                ", priority='" + priority + '\'' +
                ", completionStatus='" + completionStatus + '\'' +
                '}';
    }

    /**
     * Builder builder.
     *
     * @return the builder
     */
//CHECKSTYLE:OFF: Builder
    public static Builder builder() {
        return new Builder();
    }

    /**
     * The type Builder.
     */
    @JsonPOJOBuilder
    public static class Builder {
        private String name;
        private String category;
        private String description;
        private Double goalAmount;
        private String priority;
        private String userId;
        private Boolean completionStatus;

        /**
         * With name builder.
         *
         * @param name the name
         * @return the builder
         */
        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * With user id builder.
         *
         * @param userId the user id
         * @return the builder
         */
        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        /**
         * With category builder.
         *
         * @param category the category
         * @return the builder
         */
        public Builder withCategory(String category) {
            this.category = category;
            return this;
        }

        /**
         * With description builder.
         *
         * @param description the description
         * @return the builder
         */
        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        /**
         * With goal amount builder.
         *
         * @param goalAmount the goal amount
         * @return the builder
         */
        public Builder withGoalAmount(Double goalAmount) {
            this.goalAmount = goalAmount;
            return this;
        }

        /**
         * With priority builder.
         *
         * @param priority the priority
         * @return the builder
         */
        public Builder withPriority(String priority) {
            this.priority = priority;
            return this;
        }

        /**
         * With completion status builder.
         *
         * @param completionStatus the completion status
         * @return the builder
         */
        public Builder withCompletionStatus(Boolean completionStatus) {
            this.completionStatus = completionStatus;
            return this;
        }

        /**
         * Build create goal request.
         *
         * @return the create goal request
         */
        public CreateGoalRequest build() {
            return new CreateGoalRequest(userId, name, category, description, goalAmount, priority, completionStatus);
        }
    }

}
