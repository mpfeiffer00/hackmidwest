package com.cerner.engineering.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Base Controller for the Crucible aggregator web application.
 * @author Aaron McGinn (am025347)
 */
@Controller
public class BaseController
{
    private static final String VIEW_INDEX = "index";

    /**
     * The main search page for when a username is provided.
     * @return The jsp page to load.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String retrieveReviews()
    {
        return VIEW_INDEX;
    }
}
