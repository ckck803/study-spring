package com.example.demomultipart.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
@Slf4j
public class FileExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView("file");
        if (ex instanceof MaxUploadSizeExceededException) {
            log.info("File size exceeds limit!");
            modelAndView.getModel().put("message", "File size exceeds limit!");
        }
        return modelAndView;
    }
}
