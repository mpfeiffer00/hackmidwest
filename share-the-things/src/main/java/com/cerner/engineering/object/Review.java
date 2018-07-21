package com.cerner.engineering.object;

import com.cerner.common.collection.ListUtils;
import com.cerner.common.core.HashCodeAssistant;
import com.cerner.common.core.builder.AbstractExpressionBuilder;
import com.cerner.system.util.lang.EqualityAssistant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.joda.time.LocalDateTime;

/**
 * A review on Crucible.
 * @author Aaron McGinn (am025347)
 */
// /CLOVER:OFF
// This class has been manually modified to add the getUrl method.
public class Review
{
    /**
     * The unique id of the review.
     */
    final String permaId;

    /**
     * The name/title of the review.
     */
    final String name;

    /**
     * The {@link User} that is the author of the review.
     */
    final User author;

    /**
     * The {@link User} that is the moderator of the review.
     */
    final User moderator;

    /**
     * A List of {@link User}s that are included as reviewers.
     */
    final List<Reviewer> reviewers;

    /**
     * The {@link CrucibleInstance} instance that this review is located on.
     */
    final CrucibleInstance crucibleInstance;

    /**
     * True if all reviewers on the review have marked it as complete.
     */
    final boolean allReviewersComplete;

    /**
     * The {@link LocalDateTime} that the review was created.
     */
    final LocalDateTime dateCreated;

    /**
     * Gets the URL for the review.
     * @return The URL for the review.
     */
    public String getUrl()
    {
        return crucibleInstance.getBaseURL().concat("cru/").concat(permaId);
    }

    /**
     * @return The unique id of the review.
     */
    public String getPermaId()
    {
        return permaId;
    }

    /**
     * @return The name/title of the review.
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return The {@link User} that is the author of the review.
     */
    public User getAuthor()
    {
        return author;
    }

    /**
     * @return The {@link User} that is the moderator of the review.
     */
    public User getModerator()
    {
        return moderator;
    }

    /**
     * @return Non-null A List of {@link User}s that are included as reviewers. May be empty and immutable.
     */
    public List<Reviewer> getReviewers()
    {
        return reviewers;
    }

    /**
     * @return The {@link CrucibleInstance} instance that this review is located on.
     */
    public CrucibleInstance getCrucibleInstance()
    {
        return crucibleInstance;
    }

    /**
     * @return True if all reviewers on the review have marked it as complete.
     */
    public boolean isAllReviewersComplete()
    {
        return allReviewersComplete;
    }

    /**
     * @return The {@link LocalDateTime} that the review was created.
     */
    public LocalDateTime getDateCreated()
    {
        return dateCreated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return HashCodeAssistant.combineHashCodes(HashCodeAssistant.hashObject(permaId), HashCodeAssistant.hashObject(name),
                HashCodeAssistant.hashObject(author), HashCodeAssistant.hashObject(moderator), HashCodeAssistant.hashObject(reviewers),
                HashCodeAssistant.hashObject(crucibleInstance), HashCodeAssistant.hashBoolean(allReviewersComplete),
                HashCodeAssistant.hashObject(dateCreated));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object object)
    {
        if (this == object)
        {
            return true;
        }

        if (!(object instanceof Review))
        {
            return false;
        }

        final Review other = (Review) object;
        if (EqualityAssistant.notEqual(permaId, other.permaId))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(name, other.name))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(author, other.author))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(moderator, other.moderator))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(reviewers, other.reviewers))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(crucibleInstance, other.crucibleInstance))
        {
            return false;
        }

        if (allReviewersComplete != other.allReviewersComplete)
        {
            return false;
        }

        if (EqualityAssistant.notEqual(dateCreated, other.dateCreated))
        {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return new StringBuilder(500).append("Review[").append("permaId=").append(permaId).append(", name=").append(name).append(", author=")
                .append(author).append(", moderator=").append(moderator).append(", reviewers=").append(reviewers).append(", crucibleInstance=")
                .append(crucibleInstance).append(", allReviewersComplete=").append(allReviewersComplete).append(", dateCreated=").append(dateCreated)
                .append("]").toString();
    }

    /**
     * Constructs a {@link Review}. Declared private to prevent direct instantiation by external consumers.
     * @param builder
     *            Builder whose contents are used to construct this class (cannot be null).
     * @throws AssertionError
     *             if the Builder is null and assertions are enabled.
     */
    private Review(final Builder builder)
    {
        assert builder != null : "builder:null";

        permaId = builder.permaId;
        name = builder.name;
        author = builder.author;
        moderator = builder.moderator;
        reviewers = ListUtils.nonNullList(builder.reviewers);

        crucibleInstance = builder.crucibleInstance;
        allReviewersComplete = builder.allReviewersComplete;
        dateCreated = builder.dateCreated;
    }

    /**
     * Builder for {@link Review}.
     */
    public static final class Builder extends AbstractExpressionBuilder implements com.cerner.common.core.builder.Builder<Review>
    {
        private String permaId;
        private String name;
        private User author;
        private User moderator;
        private List<Reviewer> reviewers;
        private CrucibleInstance crucibleInstance;
        private boolean allReviewersComplete;
        private LocalDateTime dateCreated;

        /**
         * Creates a new Builder.
         * @return Non-null Builder.
         */
        public static Builder create()
        {
            return new Builder();
        }

        /**
         * Creates a new Builder. Declared private to prevent direct instantiation by external consumers.
         */
        private Builder()
        {
        }

        /**
         * @param permaId
         *            The unique id of the review.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withPermaId(@SuppressWarnings("hiding") final String permaId)
        {
            verifyBuildingState();
            this.permaId = permaId;
            return this;
        }

        /**
         * @param name
         *            The name/title of the review.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withName(@SuppressWarnings("hiding") final String name)
        {
            verifyBuildingState();
            this.name = name;
            return this;
        }

        /**
         * @param author
         *            The {@link User} that is the author of the review.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withAuthor(@SuppressWarnings("hiding") final User author)
        {
            verifyBuildingState();
            this.author = author;
            return this;
        }

        /**
         * @param moderator
         *            The {@link User} that is the moderator of the review.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withModerator(@SuppressWarnings("hiding") final User moderator)
        {
            verifyBuildingState();
            this.moderator = moderator;
            return this;
        }

        /**
         * @param reviewers
         *            A List of {@link User}s that are included as reviewers.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withReviewers(@SuppressWarnings("hiding") final List<Reviewer> reviewers)
        {
            verifyBuildingState();
            this.reviewers = reviewers;
            return this;
        }

        /**
         * @param reviewers
         *            A List of {@link User}s that are included as reviewers.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withReviewers(@SuppressWarnings("hiding") final Reviewer... reviewers)
        {
            verifyBuildingState();
            this.reviewers = reviewers == null || reviewers.length == 0 ? Collections.<Reviewer> emptyList() : Arrays.asList(reviewers);
            return this;
        }

        /**
         * @param crucibleInstance
         *            The {@link CrucibleInstance} instance that this review is located on.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withCrucibleInstance(@SuppressWarnings("hiding") final CrucibleInstance crucibleInstance)
        {
            verifyBuildingState();
            this.crucibleInstance = crucibleInstance;
            return this;
        }

        /**
         * @param allReviewersComplete
         *            True if all reviewers on the review have marked it as complete.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withAllReviewersComplete(@SuppressWarnings("hiding") final boolean allReviewersComplete)
        {
            verifyBuildingState();
            this.allReviewersComplete = allReviewersComplete;
            return this;
        }

        /**
         * True if all reviewers on the review have marked it as complete.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder allReviewersComplete()
        {
            verifyBuildingState();
            allReviewersComplete = true;
            return this;
        }

        /**
         * True if all reviewers on the review have marked it as complete.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder notAllReviewersComplete()
        {
            verifyBuildingState();
            allReviewersComplete = false;
            return this;
        }

        /**
         * @param dateCreated
         *            The {@link LocalDateTime} that the review was created.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withDateCreated(@SuppressWarnings("hiding") final LocalDateTime dateCreated)
        {
            verifyBuildingState();
            this.dateCreated = dateCreated;
            return this;
        }

        /**
         * Completes the building of the {@link Review}. <strong>This builder cannot be used to modify the object after this method is
         * called.</strong>
         * @return Non-null {@link Review} built by this builder.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        @Override
        public Review build()
        {
            setBuildingComplete();
            return new Review(this);
        }
    }
}
// /CLOVER:ON