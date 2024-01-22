package vantruong.nststore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import vantruong.nststore.dto.UserDto;
import vantruong.nststore.job.SendMailJob;
import vantruong.nststore.service.MailService;

import java.util.HashMap;
import java.util.Map;

import static vantruong.nststore.constant.EmailConstant.VERIFY_EMAIL;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
  private final TemplateEngine templateEngine;
  private final JavaMailSender javaMailSender;

  @Value("${spring.mail.username}")
  private String systemEmail;

  private void jobSendMail(String from, String to, String subject, String htmlBody, JavaMailSender javaMailSender) {
    Thread job = new SendMailJob(from, to, subject, htmlBody, javaMailSender);
    job.start();
  }

  @Override
  public void senMail(UserDto userDto) {
    Context context = new Context();
    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("name", userDto.getName());

    context.setVariables(templateModel);

    String subject = "Verify email";
    String htmlBody = templateEngine.process(VERIFY_EMAIL, context);

    jobSendMail(systemEmail, userDto.getEmail(), subject, htmlBody, javaMailSender);
  }

}
