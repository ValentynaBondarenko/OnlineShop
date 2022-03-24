package servlets;

import pagegenerator.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AllRequestsServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> pageVariables = createPageVariablesMap(request);
        pageVariables.put("message", " ");

        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("request_page.html", pageVariables);

        try {
            response.getWriter().println(page);
        } catch (IOException exception) {
            throw new RuntimeException("Cant receive page with request parameters");
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> pageVariables = createPageVariablesMap(request);

        String message = request.getParameter("message");

        response.setContentType("text/html;charset=utf-8");

        if (message == null || message.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        pageVariables.put("message", message == null ? "" : message);

        try {
            response.getWriter().println(PageGenerator.instance().getPage("request_page.html", pageVariables));
        } catch (IOException exception) {
            throw new RuntimeException("Cant send page with response to request");
        }
    }

    static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("method", request.getMethod());
        pageVariables.put("URL", request.getRequestURL().toString());
        pageVariables.put("pathInfo", request.getPathInfo());
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("parameters", request.getParameterMap().toString());
        return pageVariables;
    }
}