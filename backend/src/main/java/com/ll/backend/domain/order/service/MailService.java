package com.ll.backend.domain.order.service;

import com.ll.backend.domain.order.dto.MailForm;
import com.ll.backend.domain.orderdetail.entity.OrderDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    private static final String title = "[CoffeeBean] 고객님의 주문이 배송을 시작했습니다.";
    private static final StringBuilder message = new StringBuilder("안녕하세요. [CoffeeBean] 주문 배송 안내 메일입니다. "
            + "\n" + "고객님께서 주문하신 제품의 배송이 오늘 시작되었습니다."
            + "\n" + "고객님의 소중한 주문에 감사드리며, 더 나은 서비스를 위해 최선을 다하겠습니다."
            + "\n" + "문의 사항이 있으시면 언제든지 연락 주시기 바랍니다." + "\n"
            + "\n" + "주문 내역은 아래에서 확인하시 수 있습니다." + "\n");

    //properties에서 설정한 email이 자동으로 설정되었음(Mail 생성하기위해 일단 해놓음)
    private String from;

    public MailForm createMail(String to, List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            createMessage(orderDetail.getProduct().getName(), orderDetail.getQuantity());
        }
        MailForm mailDto = new MailForm(from, to, title, message.toString());
        return mailDto;
    }

    public void createMessage(String product, int quantity) {
        message.append("- [").append(product).append("] : ").append(quantity).append("개\n");
    }

    public void sendMail(MailForm mailForm) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailForm.getTo());
        mailMessage.setSubject(mailForm.getTitle());
        mailMessage.setText(mailForm.getMessage());
        mailMessage.setFrom(mailForm.getFrom());
        mailMessage.setReplyTo(mailForm.getFrom());

        mailSender.send(mailMessage);
    }
}
