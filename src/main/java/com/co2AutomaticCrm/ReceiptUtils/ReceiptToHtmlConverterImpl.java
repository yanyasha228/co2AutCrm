package com.co2AutomaticCrm.ReceiptUtils;

import com.co2AutomaticCrm.Models.NonPersistentModels.Receipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class ReceiptToHtmlConverterImpl implements ReceiptToHtmlConverter {

    @Autowired
    private TemplateEngine templateEngine;

    public String convert(Receipt receipt) {

        Context context = new Context();

        context.setVariable("receipt", receipt);

        return templateEngine.process("receipt", context);

    }

}
