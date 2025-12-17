//package filters;
//
//import beans.DateBean;
//import jakarta.enterprise.inject.spi.CDI;
//import jakarta.servlet.*;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//
//import java.io.IOException;
//
//public class TimezoneFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        Cookie[] cookies = httpServletRequest.getCookies();
//
//        DateBean dateBean = CDI.current().select(DateBean.class).get();
//
//        Cookie timezoneCookie = null;
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("TIMEZONE_COOKIE")) {
//                    timezoneCookie = cookie;
//                    break;
//                }
//            }
//            if (timezoneCookie != null) {
//                String timezone = "GMT";
//                int offset = -1 * Integer.parseInt(timezoneCookie.getValue()) / 60;
//                if (offset >= 0) {
//                    timezone = timezone + "+" + String.valueOf(offset);
//                } else {
//                    timezone = timezone + String.valueOf(offset);
//                }
//                dateBean.setTimezone(timezone);
//            }
//        }
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//    }
//
//}
