package com.familia.listadetarefas.adapter.out.email;

import com.familia.listadetarefas.domain.port.out.EmailSenderPort;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class SmtpEmailSenderAdapter implements EmailSenderPort {

    private final JavaMailSender mailSender;
    private final String remetente;

    public SmtpEmailSenderAdapter(JavaMailSender mailSender, @Value("${app.mail.from}") String remetente) {
        this.mailSender = mailSender;
        this.remetente = remetente;
    }

    @Override
    public void enviar(String destinatario, String assunto, String corpoHtml) {
        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, "UTF-8");
            helper.setFrom(remetente);
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(corpoHtml, true);
            mailSender.send(mensagem);
        } catch (Exception e) {
            // Não deixa o fluxo quebrar de forma feia se o SMTP falhar -
            // mas registra bem alto, porque sem isso o usuário fica sem
            // conseguir definir a senha e ninguém percebe.
            throw new IllegalStateException("Falha ao enviar e-mail para " + destinatario, e);
        }
    }
}
