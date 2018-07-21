package com.cerner.engineering.object;

import com.cerner.common.core.HashCodeAssistant;
import com.cerner.common.core.builder.AbstractExpressionBuilder;
import com.cerner.system.util.lang.EqualityAssistant;

import org.joda.time.DateTime;

/**
 * A reviewer of a Crucible review.
 * @author Aaron McGinn (am025347)
 */
// /CLOVER:OFF
public class Reviewer
{
    /**
     * The {@link User} who is a reviewer on the review.
     */
    private final User user;

    /**
     * True if the user has completed the review, false otherwise.
     */
    private final boolean completed;

    /**
     * The {@link DateTime} that the reviewer completed the review.
     */
    private final DateTime completedDateTime;

    /**
     * @return The {@link User} who is a reviewer on the review.
     */
    public User getUser()
    {
        return user;
    }

    /**
     * @return True if the user has completed the review, false otherwise.
     */
    public boolean isCompleted()
    {
        return completed;
    }

    /**
     * @return The {@link DateTime} that the reviewer completed the review.
     */
    public DateTime getCompletedDateTime()
    {
        return completedDateTime;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return HashCodeAssistant.combineHashCodes(HashCodeAssistant.hashObject(user), HashCodeAssistant.hashBoolean(completed),
                HashCodeAssistant.hashObject(completedDateTime));
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

        if (!(object instanceof Reviewer))
        {
            return false;
        }

        final Reviewer other = (Reviewer) object;
        if (EqualityAssistant.notEqual(user, other.user))
        {
            return false;
        }

        if (completed != other.completed)
        {
            return false;
        }

        if (EqualityAssistant.notEqual(completedDateTime, other.completedDateTime))
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
        return new StringBuilder(200).append("Reviewer[").append("user=").append(user).append(", completed=").append(completed)
                .append(", completedDateTime=").append(completedDateTime).append("]").toString();
    }

    /**
     * Constructs a {@link Reviewer}. Declared private to prevent direct instantiation by external consumers.
     * @param builder
     *            Builder whose contents are used to construct this class (cannot be null).
     * @throws AssertionError
     *             if the Builder is null and assertions are enabled.
     */
    private Reviewer(final Builder builder)
    {
        assert builder != null : "builder:null";

        user = builder.user;
        completed = builder.completed;
        completedDateTime = builder.completedDateTime;
    }

    /**
     * Builder for {@link Reviewer}.
     */
    public static final class Builder extends AbstractExpressionBuilder implements com.cerner.common.core.builder.Builder<Reviewer>
    {
        private User user;
        private boolean completed;
        private DateTime completedDateTime;

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
         * @param user
         *            The {@link User} who is a reviewer on the review.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withUser(@SuppressWarnings("hiding") final User user)
        {
            verifyBuildingState();
            this.user = user;
            return this;
        }

        /**
         * @param completed
         *            True if the user has completed the review, false otherwise.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withCompleted(@SuppressWarnings("hiding") final boolean completed)
        {
            verifyBuildingState();
            this.completed = completed;
            return this;
        }

        /**
         * True if the user has completed the review, false otherwise.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder completed()
        {
            verifyBuildingState();
            completed = true;
            return this;
        }

        /**
         * True if the user has completed the review, false otherwise.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder notCompleted()
        {
            verifyBuildingState();
            completed = false;
            return this;
        }

        /**
         * @param completedDateTime
         *            The {@link DateTime} that the reviewer completed the review.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withCompletedDateTime(@SuppressWarnings("hiding") final DateTime completedDateTime)
        {
            verifyBuildingState();
            this.completedDateTime = completedDateTime;
            return this;
        }

        /**
         * Completes the building of the {@link Reviewer}. <strong>This builder cannot be used to modify the object after this method is
         * called.</strong>
         * @return Non-null {@link Reviewer} built by this builder.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Reviewer build()
        {
            setBuildingComplete();
            return new Reviewer(this);
        }
    }
}
// /CLOVER:ON