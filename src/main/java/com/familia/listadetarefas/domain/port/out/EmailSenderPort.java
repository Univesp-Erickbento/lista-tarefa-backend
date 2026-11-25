package com.familia.listadetarefas.domain.port.out;

/**
 * Abstrai o mecanismo de envio de e-mail. A implementação real (SMTP,
 * SES, etc.) fica no adaptador - o domínio só sabe que existe "enviar".
 */
public interface EmailSenderPort {
    void enviar(String destinatario, String assunto, String corpoHtml);
}
