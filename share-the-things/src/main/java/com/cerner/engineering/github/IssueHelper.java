package com.cerner.engineering.github;

import com.cerner.engineering.object.Issue;

import java.util.List;

/**
 * Class to help manage the issues returned.
 * @author Aaron McGinn (am025347)
 */
public class IssueHelper
{
    /**
     * Parse the List of {@link Issue}s to display them in the web page.
     * @param issues
     *            The {@link Issue}s to parse.
     * @return The HTMl String of a table of the provided {@link Issue}s.
     */
    public static String parseIssues(final List<Issue> issues)
    {
        if (issues.isEmpty())
        {
            return "You are not currently involved in any GitHub Issues!";
        }

        final StringBuilder issuesBuilder = new StringBuilder();
        issuesBuilder.append(getIssuesTableHeaderRow());

        for (final Issue issue : issues)
        {
            issuesBuilder.append(convertIssueToTableRow(issue));
        }

        issuesBuilder.append("</table><br>");

        return issuesBuilder.toString();
    }

    /**
     * Create the issue table's header row.
     * @return The HTML formatted header row for the issue table.
     */
    private static String getIssuesTableHeaderRow()
    {
        return new StringBuilder().append("<table style=\"border: 1px solid black; border-collapse: collapse;\"><tr>")
                .append("<th style=\"border: 1px solid black; padding: 10px;\">Author</th>")
                .append("<th style=\"border: 1px solid black; padding: 10px;\">Issue Id</th>")
                .append("<th style=\"border: 1px solid black; padding: 10px; min-width: 14ch; text-align: center;\">Date Created</th>")
                .append("<th style=\"border: 1px solid black; padding: 10px;\">Issue Details</th></tr>").toString();
    }

    private static String convertIssueToTableRow(final Issue issue)
    {
        final StringBuilder tableRow = new StringBuilder("<tr>");
        tableRow.append("<td style=\"border: 1px solid black; border-collapse: collapse; padding: 10px;\">").append("<img src=\"")
                .append(issue.getUser().getAvatarUrl()).append("\" height=\"18\" width=\"18\">&nbsp;").append(issue.getUser().getDisplayName())
                .append("</td>");

        return tableRow.append("<td style=\"border: 1px solid black; border-collapse: collapse; padding: 10px;\">").append(convertIssueToLink(issue))
                .append("</td><td style=\"border: 1px solid black; border-collapse: collapse; padding: 10px; text-align: center;\">")
                .append(issue.getDateCreated().toString("MM/dd/yy H:mm"))
                .append("</td><td style=\"border: 1px solid black; border-collapse: collapse; padding: 10px;\">").append(issue.getTitle())
                .append("</tr>").toString();
    }

    /**
     * Create an HTML link string from the review.
     * @param issue
     *            The review to create a link to.
     * @return The HTML formatted URL link to the review.
     */
    private static String convertIssueToLink(final Issue issue)
    {
        final String repo = issue.getRepoUrl().substring(issue.getRepoUrl().indexOf("repos/") + 6);
        return "<a href=\"" + issue.getUrl() + "\" target=\"_blank\">" + repo + "#" + issue.getNumber() + "</a>";
    }
}
