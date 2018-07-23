package com.cerner.engineering.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import shareit.api.external.search.ImageSearch;
import shareit.google.GoogleBooks;
import shareit.object.Book;

/**
 * Base Controller for the Crucible aggregator web application.
 * @author Aaron McGinn (am025347)
 */
@Controller
public class BaseController
{
    private static final String VIEW_INDEX = "index";
    private static final String SEARCH_INDEX = "search";
    private static final String RESULT_INDEX = "showbook";
    private static final String SCAN_INDEX = "scan";

    /**
     * The main search page for when a username is provided.
     * @return The jsp page to load.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String retrieveReviews()
    {
        return VIEW_INDEX;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String retrieveSearch()
    {
        return SEARCH_INDEX;
    }

    @RequestMapping(value = "/scan", method = RequestMethod.GET)
    public String retrieveScan()
    {
        return SCAN_INDEX;
    }

    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public String retrieveSearchResult(@RequestParam(value = "q", required = false) final String searchQuery, final ModelMap model)
    {
        if (searchQuery == null || searchQuery.isEmpty())
        {
            return SEARCH_INDEX;
        }

        // final Book book = ReadISBN.getBookFromISBN(searchQuery);
        final List<Book> bookResults = GoogleBooks.search(searchQuery);

        if (bookResults == null)
        {
            return SEARCH_INDEX;
        }
        model.addAttribute("bookResults", bookResults);

        return RESULT_INDEX;
    }

    @PostMapping(value = "/result", produces = MediaType.APPLICATION_JSON_VALUE)
    public String singleFileUpload(@RequestPart("file") final MultipartFile file, final ModelMap model)
    {

        if (file.isEmpty())
        {
            return SEARCH_INDEX;
        }

        final List<Book> bookResults = ImageSearch.search(file);

        if (bookResults == null)
        {
            return SEARCH_INDEX;
        }
        model.addAttribute("bookResults", bookResults);

        return RESULT_INDEX;
    }
}
