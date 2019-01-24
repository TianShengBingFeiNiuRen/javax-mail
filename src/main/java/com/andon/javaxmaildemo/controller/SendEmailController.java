package com.andon.javaxmaildemo.controller;

import com.andon.javaxmaildemo.service.SendEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Andon
 * @date 2019/1/24
 */
@RestController
public class SendEmailController {

    private static final Logger LOG = LoggerFactory.getLogger(SendEmailController.class);

    @Resource
    private SendEmailService sendEmailService;

    @GetMapping(value = "/sendEmail")
    public String sendEmail(String title, String content) {
        LOG.info("title={},content={}", title, content);
        sendEmailService.sendEmail(title, content);
        LOG.info("sendEmailService sendEmail success!! title={}", title);
        return "sendEmailService sendEmail success!! title=" + title;
    }
}
