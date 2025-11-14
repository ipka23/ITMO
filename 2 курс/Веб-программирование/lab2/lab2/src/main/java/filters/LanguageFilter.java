package filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
@Log
public class LanguageFilter extends HttpFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("filter started");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String clientLang = request.getParameter("lang");
        if (clientLang == null) clientLang = "ru-RU";
        System.out.println(clientLang);
        log.info("clientLang: " + clientLang);
        Locale locale = getLocaleFromParam(clientLang);

        log.info("locale: " + locale);
        request.setAttribute("locale", locale);
        request.setAttribute("messages", ResourceBundle.getBundle("messages", locale));

        filterChain.doFilter(request, response);
    }

    private Locale getLocaleFromParam(String clientLang) {
        return switch (clientLang) {
            case "en-US" -> new Locale("en");
            case "eo-EO" -> new Locale("eo");
            default -> new Locale("ru");
        };
    }
}
