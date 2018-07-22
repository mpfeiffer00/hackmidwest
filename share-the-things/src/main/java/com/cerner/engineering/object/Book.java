package com.cerner.engineering.object;

import com.cerner.common.core.HashCodeAssistant;
import com.cerner.common.core.builder.AbstractExpressionBuilder;
import com.cerner.system.util.lang.EqualityAssistant;

/**
 * Subset of book information returned by Goodreads.
 * @author Katie Huang (KH018233)
 */
// /CLOVER:OFF
public class Book
{
    private final long goodReadsId;

    private final String title;

    private final String isbn;

    private final String isbn13;

    private final String imageUrl;

    private final String smallImageUrl;

    private final Long publicationYear;

    private final Long publicationMonth;

    private final Long publicationDay;

    private final String publisher;

    /**
     * @return
     */
    public long getGoodReadsId()
    {
        return goodReadsId;
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
    public String getIsbn()
    {
        return isbn;
    }

    /**
     * @return
     */
    public String getIsbn13()
    {
        return isbn13;
    }

    /**
     * @return
     */
    public String getImageUrl()
    {
        return imageUrl;
    }

    /**
     * @return
     */
    public String getSmallImageUrl()
    {
        return smallImageUrl;
    }

    /**
     * @return
     */
    public Long getPublicationYear()
    {
        return publicationYear;
    }

    /**
     * @return
     */
    public Long getPublicationMonth()
    {
        return publicationMonth;
    }

    /**
     * @return
     */
    public Long getPublicationDay()
    {
        return publicationDay;
    }

    /**
     * @return
     */
    public String getPublisher()
    {
        return publisher;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        return HashCodeAssistant.combineHashCodes(HashCodeAssistant.hashLong(goodReadsId), HashCodeAssistant.hashObject(title),
                HashCodeAssistant.hashObject(isbn), HashCodeAssistant.hashObject(isbn13), HashCodeAssistant.hashObject(imageUrl),
                HashCodeAssistant.hashObject(smallImageUrl), HashCodeAssistant.hashObject(publicationYear),
                HashCodeAssistant.hashObject(publicationMonth), HashCodeAssistant.hashObject(publicationDay),
                HashCodeAssistant.hashObject(publisher));
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

        if (!(object instanceof Book))
        {
            return false;
        }

        final Book other = (Book) object;
        if (goodReadsId != other.goodReadsId)
        {
            return false;
        }

        if (EqualityAssistant.notEqual(title, other.title))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(isbn, other.isbn))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(isbn13, other.isbn13))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(imageUrl, other.imageUrl))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(smallImageUrl, other.smallImageUrl))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(publicationYear, other.publicationYear))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(publicationMonth, other.publicationMonth))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(publicationDay, other.publicationDay))
        {
            return false;
        }

        if (EqualityAssistant.notEqual(publisher, other.publisher))
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
        return new StringBuilder(700).append("Book[").append("goodReadsId=").append(goodReadsId).append(", title=").append(title).append(", isbn=")
                .append(isbn).append(", isbn13=").append(isbn13).append(", imageUrl=").append(imageUrl).append(", smallImageUrl=")
                .append(smallImageUrl).append(", publicationYear=").append(publicationYear).append(", publicationMonth=").append(publicationMonth)
                .append(", publicationDay=").append(publicationDay).append(", publisher=").append(publisher).append("]").toString();
    }

    /**
     * Constructs a {@link Book}. Declared private to prevent direct instantiation by external consumers.
     * @param builder
     *            Builder whose contents are used to construct this class (cannot be null).
     * @throws AssertionError
     *             if the Builder is null and assertions are enabled.
     */
    private Book(final Builder builder)
    {
        assert builder != null : "builder:null";

        goodReadsId = builder.goodReadsId;
        title = builder.title;
        isbn = builder.isbn;
        isbn13 = builder.isbn13;
        imageUrl = builder.imageUrl;

        smallImageUrl = builder.smallImageUrl;
        publicationYear = builder.publicationYear;
        publicationMonth = builder.publicationMonth;
        publicationDay = builder.publicationDay;
        publisher = builder.publisher;
    }

    /**
     * Builder for {@link Book}.
     */
    public static final class Builder extends AbstractExpressionBuilder implements com.cerner.common.core.builder.Builder<Book>
    {
        private long goodReadsId;
        private String title;
        private String isbn;
        private String isbn13;
        private String imageUrl;
        private String smallImageUrl;
        private Long publicationYear;
        private Long publicationMonth;
        private Long publicationDay;
        private String publisher;

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
         * @param goodReadsId
         *            .
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withGoodReadsId(@SuppressWarnings("hiding") final long goodReadsId)
        {
            verifyBuildingState();
            this.goodReadsId = goodReadsId;
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
         * @param isbn
         *            .
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withIsbn(@SuppressWarnings("hiding") final String isbn)
        {
            verifyBuildingState();
            this.isbn = isbn;
            return this;
        }

        /**
         * @param isbn13
         *            .
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withIsbn13(@SuppressWarnings("hiding") final String isbn13)
        {
            verifyBuildingState();
            this.isbn13 = isbn13;
            return this;
        }

        /**
         * @param imageUrl
         *            .
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withImageUrl(@SuppressWarnings("hiding") final String imageUrl)
        {
            verifyBuildingState();
            this.imageUrl = imageUrl;
            return this;
        }

        /**
         * @param smallImageUrl
         *            .
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withSmallImageUrl(@SuppressWarnings("hiding") final String smallImageUrl)
        {
            verifyBuildingState();
            this.smallImageUrl = smallImageUrl;
            return this;
        }

        /**
         * @param publicationYear
         *            .
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withPublicationYear(@SuppressWarnings("hiding") final Long publicationYear)
        {
            verifyBuildingState();
            this.publicationYear = publicationYear;
            return this;
        }

        /**
         * @param publicationMonth
         *            .
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withPublicationMonth(@SuppressWarnings("hiding") final Long publicationMonth)
        {
            verifyBuildingState();
            this.publicationMonth = publicationMonth;
            return this;
        }

        /**
         * @param publicationDay
         *            .
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withPublicationDay(@SuppressWarnings("hiding") final Long publicationDay)
        {
            verifyBuildingState();
            this.publicationDay = publicationDay;
            return this;
        }

        /**
         * @param publisher
         *            .
         * @return Non-null Builder used to continue building the object.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Builder withPublisher(@SuppressWarnings("hiding") final String publisher)
        {
            verifyBuildingState();
            this.publisher = publisher;
            return this;
        }

        /**
         * Completes the building of the {@link Book}. <strong>This builder cannot be used to modify the object after this method is called.</strong>
         * @return Non-null {@link Book} built by this builder.
         * @throws IllegalStateException
         *             if this builder is finished building the object.
         */
        public Book build()
        {
            setBuildingComplete();
            return new Book(this);
        }
    }
}
// /CLOVER:ON