package com.cerner.engineering.controller;

import com.cerner.engineering.ReviewRetriever;
import com.cerner.engineering.auth.AuthorizationAssistant;
import com.cerner.engineering.github.GithubReviewRetrieverCallable;
import com.cerner.engineering.github.IssueHelper;
import com.cerner.engineering.metrics.HitCount;
import com.cerner.engineering.object.Crucible;
import com.cerner.engineering.object.Issue;
import com.cerner.engineering.object.Review;
import com.cerner.engineering.object.ReviewsResponse;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

/**
 * Base Controller for the Crucible aggregator web application.
 * @author Aaron McGinn (am025347)
 */
@Controller
public class BaseController
{
    private static final String VIEW_INDEX = "index";

    static
    {
        AuthorizationAssistant.setupCrucibleInstances();
        HitCount.ensureCountExists();
    }

    /**
     * The main search page for when a username is provided.
     * @param username
     *            The username to search for reviews for.
     * @param fromDate
     *            The from date to filter reviews by. Deprecated.
     * @param daysAgo
     *            The number of days ago to filter reviews by.
     * @param model
     *            The {@link ModelMap} for the page information.
     * @return The jsp page to load.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String retrieveReviews(@RequestParam(value = "username", required = false) final String username,
            @RequestParam(value = "fromDate", required = false) final String fromDate,
            @RequestParam(value = "daysAgo", required = false) final String daysAgo, final ModelMap model)
    {
        final long hitCount = HitCount.getCount();

        final String welcome = "Welcome!";
        model.addAttribute("headerText", welcome);
        model.addAttribute("script", getFormScript());

        if (username == null || username.trim().length() == 0)
        {
            System.out.println("Base url hit: " + hitCount);
            final StringBuilder message = new StringBuilder()
                    .append("To retrieve Crucible review information about a user, provide the username:<br>\n"
                            + "<form id=\"searchForm\">\n<label for=\"fromDate\">Only include reviews with last activity date within the last </label>"
                            + "<input id=\"daysAgo\" type=\"number\" min=\"0\" name=\"daysAgo\"\"> days.<br><br>\n<label for=\"username\">Username:</label>"
                            + "<input id=\"username\" name=\"username\" autofocus=\"autofocus\" pattern=\".{3,}\" required>&nbsp;\n"
                            + "<input id=\"submitBtn\" type=\"button\" onclick=\"submitForm();\" value=\"Go\"></form>");

            final Set<Crucible> unauthorizedInstances = AuthorizationAssistant.getUnauthorizedInstances();

            for (final Crucible unauthorizedInstance : unauthorizedInstances)
            {
                message.append("<br><strong>Could not authorize for Crucible instance: " + unauthorizedInstance.getBaseUrl() + "</strong>");
            }

            model.addAttribute("reviewInformation", message);
        }
        else
        {
            System.out.println("Search url hit with username=" + username + ": " + hitCount);

            Long actualDaysAgo = null;
            if (daysAgo != null && !daysAgo.trim().isEmpty())
            {
                try
                {
                    final Long longDaysAgo = Long.valueOf(daysAgo);
                    if (longDaysAgo >= 0)
                    {
                        actualDaysAgo = longDaysAgo;
                    }
                }
                catch (@SuppressWarnings("unused") final NumberFormatException e)
                {
                    // Just don't set it
                }
            }
            else if (fromDate != null && !fromDate.trim().isEmpty())
            {
                try
                {
                    final DateTime dateTime = DateTime.parse(fromDate);

                    final long fromMillis = dateTime.getMillis();
                    final long now = DateTime.now().getMillis();
                    if (fromMillis < now)
                    {
                        final long millisAgo = now - fromMillis;
                        actualDaysAgo = TimeUnit.MILLISECONDS.toDays(millisAgo);
                    }
                }
                catch (@SuppressWarnings("unused") final IllegalArgumentException | UnsupportedOperationException e)
                {
                    // Just don't use the from date
                }
            }

            final String trimmedUsername = username.trim();

            final String greeting = "To retrieve Crucible review information about a user, provide the username:<br>"
                    + "<form id=\"searchForm\"><label for=\"fromDate\">Only include reviews with last activity date within the last </label><input id=\"daysAgo\" type=\"number\" min=\"0\" name=\"daysAgo\""
                    + " value=\"" + actualDaysAgo
                    + "\"> days.<br><br><label for=\"username\">Username:</label><input id=\"username\"name=\"username\" autofocus=\"autofocus\" pattern=\".{3,}\" required value=\""
                    + trimmedUsername + "\">&nbsp;<input id=\"submitBtn\" type=\"button\" onclick=\"submitForm();\" value=\"Go\"></form>";

            final Set<String> invalidChars = Sets.newLinkedHashSet();
            for (final char character : trimmedUsername.toCharArray())
            {
                if (!Character.isLetterOrDigit(character))
                {
                    invalidChars.add(Character.toString(character));
                }
            }

            final String allInvalidCharacters = invalidChars.toString();
            if ("[]".equals(allInvalidCharacters))
            {
                final ExecutorService service = Executors.newSingleThreadExecutor();
                final GithubReviewRetrieverCallable githubCallable = GithubReviewRetrieverCallable.create(trimmedUsername, actualDaysAgo);
                final Future<List<Issue>> githubFuture = service.submit(githubCallable);

                final ReviewRetriever reviewRetriever = new ReviewRetriever();
                final ReviewsResponse reviewsResponse = reviewRetriever.getReviews(trimmedUsername, actualDaysAgo);

                final List<Review> reviews = reviewsResponse.getReviews();
                final String errorInformation = Strings.nullToEmpty(reviewsResponse.getErrorInformation());

                final String formattedReviewResponse = ReviewRetriever.formatResponse(trimmedUsername, reviews);
                final String reviewsHeader = "<h2><img src=\"https://marketplace.servicerocket.com/static/products/atlassian/logoCruciblePNG.png\" width=\"25px\">"
                        + "&nbsp;Review information for " + trimmedUsername + ":</h2>";

                model.addAttribute("reviewInformation", greeting + reviewsHeader + formattedReviewResponse + "<br>" + errorInformation);

                try
                {
                    final List<Issue> issues = githubFuture.get();
                    if (!issues.isEmpty())
                    {
                        model.addAttribute("githubHeader",
                                "<a href=\"https://github.cerner.com\" target=\"_blank\">"
                                        + "<img src=\"https://assets-cdn.github.com/images/modules/logos_page/GitHub-Mark.png\" width=\"25px\"></a>"
                                        + "&nbsp;GitHub Issues (" + issues.size() + ")");
                        model.addAttribute("githubIssues", IssueHelper.parseIssues(issues));
                    }

                }
                catch (InterruptedException | ExecutionException e)
                {
                    e.printStackTrace();
                    model.addAttribute("githubIssues", "Failed to retrieve GitHub issues.");
                }
            }
            else
            {
                final String invalidCharactersMessage = "<strong>Username must be valid format. Only alphanumeric characters are acceptable. Invalid characters: "
                        + allInvalidCharacters;
                model.addAttribute("reviewInformation", greeting + invalidCharactersMessage);
            }
        }

        model.addAttribute("hitCount", hitCount);

        return VIEW_INDEX;
    }

    private String getFormScript()
    {
        return "<script type=\"text/javascript\">\nfunction submitForm() {\n" + //
                "  var daysAgo = document.getElementById('daysAgo').value; \n" + //
                "  if (daysAgo === undefined || daysAgo === \"\") {\n" + //
                "    document.getElementById('daysAgo').setAttribute('disabled', 'true')" + //
                "  } else {\n" + //
                "    document.getElementById('daysAgo').setAttribute('name', 'daysAgo');\n" + //
                "  }\n" + //
                "  document.forms[\"searchForm\"].submit();\n" + //
                "};\n" + //
                "</script>\n\n" + //
                "<script>\n" + //
                "var input = document.getElementById(\"username\");\n" + //
                "input.addEventListener(\"keyup\", function(event) {\n" + //
                "    event.preventDefault();\n" + //
                "    if (event.keyCode === 13) {\n" + //
                "        document.getElementById('submitBtn').click();\n" + //
                "    }\n" + //
                "});\n" + //
                "</script>"; //
    }
}
