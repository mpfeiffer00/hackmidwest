package com.cerner.engineering.object;

import com.cerner.common.core.HashCodeAssistant;
import com.cerner.common.core.builder.AbstractExpressionBuilder;
import com.cerner.system.util.lang.EqualityAssistant;

/**
 * A user of the Crucible system.
 * @author Aaron McGinn (am025347)
 */
// /CLOVER:OFF
public class User
{
    /**
     * The username/login of the user.
     */
    final String username;

    /**
     * The display name of the user.
     */
    final String displayName;

    /**
     * The URL of the user's Crucible avatar.
     */
    final String avatarUrl;

    /**
     * The URL of the user's Crucible profile.
     */
    final String url;

    /**
     * @return The username/login of the user.
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @return The display name of the user.
     */
    public String getDisplayName()
    {
        return displayName;
    }

    /**
     * @return The URL of the user's Crucible avatar.
     */
    public String getAvatarUrl()
    {
        return avatarUrl;
    }

    /**
     * @return The URL of the user's Crucible profile.
     */
    public String getUrl()
    {
        return url;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return HashCodeAssistant.combineHashCodes(HashCodeAssistant.hashObject(username), HashCodeAssistant.hashObject(displayName),
                HashCodeAssistant.hashObject(avatarUrl), HashCodeAssistant.hashObject(url));
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

        if (!(object instanceof User))
        {
            return false;
        }

        final User other = (User) object;
        if (EqualityAssistant.notEqual(username, other.username))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(displayName, other.displayName))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(avatarUrl, other.avatarUrl))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(url, other.url))
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
        return new StringBuilder(300).append("User[").append("username=").append(username).append(", displayName=").append(displayName)
                .append(", avatarUrl=").append(avatarUrl).append(", url=").append(url).append("]").toString();
    }

    /**
     * Constructs a {@link User}. Declared private to prevent direct instantiation by external consumers.
     * @param builder
     *            Builder whose contents are used to construct this class (cannot be null).
     * @throws AssertionError
     *             if the Builder is null and assertions are enabled.
     */
    private User(final Builder builder)
    {
        assert builder != null : "builder:null";

        username = builder.username;
        displayName = builder.displayName;
        avatarUrl = builder.avatarUrl;
        url = builder.url;
    }

    /**
     * Builder for {@link User}.
     */
    public static final class Builder extends AbstractExpressionBuilder implements com.cerner.common.core.builder.Builder<User>
    {
        private String username;
        private String displayName;
        private String avatarUrl;
        private String url;

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
         * @param username
         *            The username/login of the user.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withUsername(@SuppressWarnings("hiding") final String username)
        {
            verifyBuildingState();
            this.username = username;
            return this;
        }

        /**
         * @param displayName
         *            The display name of the user.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withDisplayName(@SuppressWarnings("hiding") final String displayName)
        {
            verifyBuildingState();
            this.displayName = displayName;
            return this;
        }

        /**
         * @param avatarUrl
         *            The URL of the user's Crucible avatar.
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withAvatarUrl(@SuppressWarnings("hiding") final String avatarUrl)
        {
            verifyBuildingState();
            this.avatarUrl = avatarUrl;
            return this;
        }

        /**
         * @param url
         *            The URL of the user's Crucible profile.
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
         * Completes the building of the {@link User}. <strong>This builder cannot be used to modify the object after this method is called.</strong>
         * @return Non-null {@link User} built by this builder.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        @Override
        public User build()
        {
            setBuildingComplete();
            return new User(this);
        }
    }
}
// /CLOVER:ON