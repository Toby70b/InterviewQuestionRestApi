package controllers.errors;

import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@AllArgsConstructor
public class RequestErrorAttributes extends DefaultErrorAttributes {

    private String currentApiVersion;
    private String sendReportUri;

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest,boolean includeStackTrace) {
        final Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, false);
        final ApiError error = ApiError.fromDefaultAttributeMap(
                currentApiVersion, defaultErrorAttributes, sendReportUri
        );
        return error.toAttributeMap();
    }
}