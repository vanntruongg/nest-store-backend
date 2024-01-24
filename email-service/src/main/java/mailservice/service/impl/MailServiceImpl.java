package mailservice.service.impl;

import lombok.RequiredArgsConstructor;
import mailservice.constant.CommonConstant;
import mailservice.dto.UserDto;
import mailservice.job.SendMailJob;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import mailservice.dto.SendMailVerifyUserRequest;
import mailservice.service.MailService;

import java.util.HashMap;
import java.util.Map;

import static mailservice.constant.EmailConstant.VERIFY_EMAIL;

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

  @Override
  public void sendVerificationEmail(SendMailVerifyUserRequest request) {
    Context context = new Context();

    String urlVerification = UriComponentsBuilder
            .fromHttpUrl(CommonConstant.BASE_URL_CLIENT)
            .path(CommonConstant.VERIFY_EMAIL)
            .queryParam("token", request.getToken())
            .toUriString();

    Map<String, Object> templateModel = new HashMap<>();
    templateModel.put("name", request.getName());
    templateModel.put("url", urlVerification);

    context.setVariables(templateModel);

    String subject = "Verify email";
    String htmlBody = templateEngine.process(VERIFY_EMAIL, context);

    jobSendMail(systemEmail, request.getEmail(), subject, htmlBody, javaMailSender);
  }

}
