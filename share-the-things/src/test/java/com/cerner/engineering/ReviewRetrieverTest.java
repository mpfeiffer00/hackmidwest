package com.cerner.engineering;

import com.cerner.engineering.object.ReviewsResponse;

import org.junit.Ignore;

/**
 * Description.
 * @author Aaron McGinn (am025347)
 */
public class ReviewRetrieverTest
{
    /**
     * Test issue when millis of days is greater than Integer.MAX_VALUE.
     */
    @Ignore
    public void test_mk8279_100DaysAgo()
    {
        final ReviewRetriever reviewRetriever = new ReviewRetriever();
        final ReviewsResponse reviews = reviewRetriever.getReviews("mk8279", 100L);
    }
}
