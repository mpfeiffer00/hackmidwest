package com.cerner.engineering.controller;

import java.io.File;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

    private final int maxUploadSizeInMb = 5 * 1024 * 1024; // 5 MB

    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        return new Class[] {SpringWebMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings()
    {
        return new String[] {"/"};
    }

    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return null;
    }

    @Override
    protected void customizeRegistration(final ServletRegistration.Dynamic registration)
    {

        // upload temp file will put here
        final File uploadDirectory = new File(System.getProperty("java.io.tmpdir"));

        // register a MultipartConfigElement
        final MultipartConfigElement multipartConfigElement = new MultipartConfigElement(uploadDirectory.getAbsolutePath(), maxUploadSizeInMb,
                maxUploadSizeInMb * 2, maxUploadSizeInMb / 2);

        registration.setMultipartConfig(multipartConfigElement);

    }

}
