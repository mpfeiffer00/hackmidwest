package com.cerner.engineering.object;

import com.cerner.common.core.HashCodeAssistant;
import com.cerner.common.core.builder.AbstractExpressionBuilder;
import com.cerner.system.util.lang.EqualityAssistant;

import org.joda.time.LocalDateTime;

/**
 * An Issue or Pull Request on GitHub.
 * @author Aaron McGinn (am025347)
 */
// /CLOVER:OFF
public class Issue
{
    private final String url;

    private final String title;

    private final User user;

    private final LocalDateTime dateCreated;

    private final String repoUrl;

    private final long number;

    /**
     * @return
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * @return
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @return
     */
    public User getUser()
    {
        return user;
    }

    /**
     * @return
     */
    public LocalDateTime getDateCreated()
    {
        return dateCreated;
    }

    /**
     * @return
     */
    public String getRepoUrl()
    {
        return repoUrl;
    }

    /**
     * @return
     */
    public long getNumber()
    {
        return number;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return HashCodeAssistant.combineHashCodes(HashCodeAssistant.hashObject(url), HashCodeAssistant.hashObject(title),
                HashCodeAssistant.hashObject(user), HashCodeAssistant.hashObject(dateCreated), HashCodeAssistant.hashObject(repoUrl),
                HashCodeAssistant.hashLong(number));
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

        if (!(object instanceof Issue))
        {
            return false;
        }

        final Issue other = (Issue) object;
        if (EqualityAssistant.notEqual(url, other.url))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(title, other.title))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(user, other.user))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(dateCreated, other.dateCreated))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(repoUrl, other.repoUrl))
        {
            return false;
        }

        if (number != other.number)
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
        return new StringBuilder(400).append("Issue[").append("url=").append(url).append(", title=").append(title).append(", user=").append(user)
                .append(", dateCreated=").append(dateCreated).append(", repoUrl=").append(repoUrl).append(", number=").append(number).append("]")
                .toString();
    }

    /**
     * Constructs a {@link Issue}. Declared private to prevent direct instantiation by external consumers.
     * @param builder
     *            Builder whose contents are used to construct this class (cannot be null).
     * @throws AssertionError
     *             if the Builder is null and assertions are enabled.
     */
    private Issue(final Builder builder)
    {
        assert builder != null : "builder:null";

        url = builder.url;
        title = builder.title;
        user = builder.user;
        dateCreated = builder.dateCreated;
        repoUrl = builder.repoUrl;

        number = builder.number;
    }

    /**
     * Builder for {@link Issue}.
     */
    public static final class Builder extends AbstractExpressionBuilder implements com.cerner.common.core.builder.Builder<Issue>
    {
        private String url;
        private String title;
        private User user;
        private LocalDateTime dateCreated;
        private String repoUrl;
        private long number;

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
         * @param url
         *            .
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withUrl(@SuppressWarnings("hiding") final String url)
        {
            verifyBuildingState();
            this.url = url;
            return this;
        }

        /**
         * @param title
         *            .
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withTitle(@SuppressWarnings("hiding") final String title)
        {
            verifyBuildingState();
            this.title = title;
            return this;
        }

        /**
         * @param user
         *            .
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
         * @param dateCreated
         *            .
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
         * @param repoUrl
         *            .
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withRepoUrl(@SuppressWarnings("hiding") final String repoUrl)
        {
            verifyBuildingState();
            this.repoUrl = repoUrl;
            return this;
        }

        /**
         * @param number
         *            .
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withNumber(@SuppressWarnings("hiding") final long number)
        {
            verifyBuildingState();
            this.number = number;
            return this;
        }

        /**
         * Completes the building of the {@link Issue}. <strong>This builder cannot be used to modify the object after this method is called.</strong>
         * @return Non-null {@link Issue} built by this builder.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Issue build()
        {
            setBuildingComplete();
            return new Issue(this);
        }
    }
}
// /CLOVER:ON