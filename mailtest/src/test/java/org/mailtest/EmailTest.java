package org.mailtest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.testng.annotations.Test;

/**
 * 发送邮件的测试程序
 * 
 * @author lwq
 * 
 */
public class EmailTest {
    @Test
    public void email() throws MessagingException {
    	
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sFormat = new SimpleDateFormat("YYYY-MM-dd");
		String date = sFormat.format(calendar.getTime());
        // 配置发送邮件的环境属性
    		System.out.println("---------发送email------");
        final Properties props = new Properties();
        /*
         * 可用的属性： mail.store.protocol / mail.transport.protocol / mail.host /
         * mail.user / mail.from
         */
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", "true");
        props.setProperty("mail.transport.protocol", "smtp"); 
        props.put("mail.smtp.host", "smtp.163.com");
        // 发件人的账号
        props.put("mail.user", "13774364001@163.com");
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", "yyf1984");

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        
        MimeBodyPart text = new MimeBodyPart();
        // setContent(“邮件的正文内容”,”设置邮件内容的编码方式”)
        text.setContent("系统自动发送，无需回复！<img src='cid:a'>","text/html;charset=gb2312");
        MimeBodyPart img = new MimeBodyPart();
        DataHandler dh = new DataHandler(new FileDataSource("D:\\gitlab\\maven\\maven\\"+date+".jpg"));//图片路径  
        img.setDataHandler(dh); 
        img.setContentID("a");
        MimeMultipart mm = new MimeMultipart(); 
        mm.addBodyPart(text);
        mm.addBodyPart(img); 
        mm.setSubType("related");
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(
                props.getProperty("mail.user"));
        message.setFrom(form);

        // 设置收件人
        InternetAddress to = new InternetAddress("yefeiyang@ctrip.com");
        message.setRecipient(RecipientType.TO, to);

        // 设置抄送
        InternetAddress cc = new InternetAddress("yefeiyang@ctrip.com");
        message.setRecipient(RecipientType.CC, cc);

        // 设置密送，其他的收件人不能看到密送的邮件地址
//        InternetAddress bcc = new InternetAddress("aaaaa@163.com");
//        message.setRecipient(RecipientType.CC, bcc);

        // 设置邮件标题
        message.setSubject("测试邮件");

        // 设置邮件的内容体
        message.setContent(mm);
    //    message.saveChanges();
        // 发送邮件
        Transport.send(message);
    }
}
