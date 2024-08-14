package com.id3.uischeduler.quartz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.id3.uischeduler.object.entity.StockData;
import com.id3.uischeduler.repository.IStockDataRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class PythonScriptService {

    @Value("${python.script.path}")
    private String scriptPath;

    @Value("${json.output.path}")
    private String jsonOutputPath;

    @Value("${email.recipients}")
    private String[] emailRecipients;

    @Autowired
    private IStockDataRepository stockDataRepository;

    @Autowired
    private JavaMailSender emailSender;

    public void runPythonScript() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("python3", scriptPath);

        Process process = processBuilder.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        int exitCode = process.waitFor();
        System.out.println("Exited with code : " + exitCode);

        readJsonOutputAndSaveToDb();
        sendEmailWithAttachment();
    }

    public void readJsonOutputAndSaveToDb() throws IOException {
        File jsonFile = new File(jsonOutputPath);

        ObjectMapper objectMapper = new ObjectMapper();
        List<StockData> stockDataList = objectMapper.readValue(jsonFile,
                objectMapper.getTypeFactory().constructCollectionType(List.class, StockData.class));

        stockDataRepository.saveAll(stockDataList);
    }

    public void sendEmailWithAttachment() throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Alıcılara e-posta gönderme
        helper.setTo(emailRecipients);
        helper.setSubject("Stock Data JSON File");
        helper.setText("Please find the attached stock data JSON file.");

        // JSON dosyasını ekle
        File jsonFile = new File(jsonOutputPath);
        helper.addAttachment("user_stock_datas.json", jsonFile);

        // E-postayı gönder
        emailSender.send(message);
    }
}
