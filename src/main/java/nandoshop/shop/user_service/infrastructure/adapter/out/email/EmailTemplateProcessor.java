package nandoshop.shop.user_service.infrastructure.adapter.out.email;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Component
public class EmailTemplateProcessor {
    private final TemplateEngine templateEngine;

    public EmailTemplateProcessor(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String renderTemplate(String templateName, Map<String, Object> variables){
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(templateName, context);
    }
}
