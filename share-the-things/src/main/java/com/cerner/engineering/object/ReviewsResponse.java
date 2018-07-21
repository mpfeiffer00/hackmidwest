package com.cerner.engineering.object;

import com.cerner.common.collection.ListUtils;
import com.cerner.common.core.HashCodeAssistant;
import com.cerner.common.core.builder.AbstractExpressionBuilder;
import com.cerner.system.util.lang.EqualityAssistant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The response from retrieving all reviews across instances.
 * @author Aaron McGinn (am025347)
 */
// /CLOVER:OFF
public class ReviewsResponse
{
    /**
     * The {@link CrucibleInstance} from which these reviews were retrieved.
     */
    private final CrucibleInstance instance;

    /**
     * list of reviews retrieved.
     */
    private final List<Review> reviews;

    /**
     * The possibly null error information. Will be populated if an error occurred contacting a Crucible instance.
     */
    private final String errorInformation;

    /**
     * @return The {@link CrucibleInstance} from which these reviews were retrieved.
     */
    public CrucibleInstance getInstance()
    {
        return instance;
    }

    /**
     * @return Non-null list of reviews retrieved. May be empty and immutable.
     */
    public List<Review> getReviews()
    {
        return reviews;
    }

    /**
     * @return The possibly null error information. Will be populated if an error occurred contacting a Crucible instance.
     */
    public String getErrorInformation()
    {
        return errorInformation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return HashCodeAssistant.combineHashCodes(HashCodeAssistant.hashObject(instance), HashCodeAssistant.hashObject(reviews),
                HashCodeAssistant.hashObject(errorInformation));
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

        if (!(object instanceof ReviewsResponse))
        {
            return false;
        }

        final ReviewsResponse other = (ReviewsResponse) object;
        if (EqualityAssistant.notEqual(instance, other.instance))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(reviews, other.reviews))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(errorInformation, other.errorInformation))
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
        return new StringBuilder(200).append("ReviewsResponse[").append("instance=").append(instance).append(", reviews=").append(reviews)
                .append(", errorInformation=").append(errorInformation).append("]").toString();
    }

    /**
     * Constructs a {@link ReviewsResponse}. Declared private to prevent direct instantiation by external consumers.
     * @param builder
     *            Builder whose contents are used to construct this class (cannot be null).
     * @throws AssertionError
     *             if the Builder is null and assertions are enabled.
     */
    private ReviewsResponse(final Builder builder)
    {
        assert builder != null : "builder:null";

        instance = builder.instance;
        reviews = ListUtils.nonNullList(builder.reviews);
        errorInformation = builder.errorInformation;
    }

    /**
     * Builder for {@link ReviewsResponse}.
     */
    public static final class Builder extends AbstractExpressionBuilder implements com.cerner.common.core.builder.Builder<ReviewsResponse>
    {
        private CrucibleInstance instance;
        private List<Review> reviews;
        private String errorInformation;

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
         * @param instance
         *            The {@link CrucibleInstance} from which these reviews were retrieved.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withInstance(@SuppressWarnings("hiding") final CrucibleInstance instance)
        {
            verifyBuildingState();
            this.instance = instance;
            return this;
        }

        /**
         * @param reviews
         *            List of reviews retrieved.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withReviews(@SuppressWarnings("hiding") final List<Review> reviews)
        {
            verifyBuildingState();
            this.reviews = reviews;
            return this;
        }

        /**
         * @param reviews
         *            List of reviews retrieved.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withReviews(@SuppressWarnings("hiding") final Review... reviews)
        {
            verifyBuildingState();
            this.reviews = reviews == null || reviews.length == 0 ? Collections.<Review> emptyList() : Arrays.asList(reviews);
            return this;
        }

        /**
         * @param errorInformation
         *            The possibly null error information. Will be populated if an error occurred contacting a Crucible instance.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withErrorInformation(@SuppressWarnings("hiding") final String errorInformation)
        {
            verifyBuildingState();
            this.errorInformation = errorInformation;
            return this;
        }

        /**
         * Completes the building of the {@link ReviewsResponse}. <strong>This builder cannot be used to modify the object after this method is
         * called.</strong>
         * @return Non-null {@link ReviewsResponse} built by this builder.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        @Override
        public ReviewsResponse build()
        {
            setBuildingComplete();
            return new ReviewsResponse(this);
        }
    }
}
// /CLOVER:ON