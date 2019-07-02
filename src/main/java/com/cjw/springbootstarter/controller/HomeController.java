package com.cjw.springbootstarter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;


@RestController
public class HomeController {
    @ApiIgnore
    @RequestMapping(value = "/")
    public void index(HttpServletResponse response) throws Exception {

        response.sendRedirect("swagger-ui.html");
    }
}
