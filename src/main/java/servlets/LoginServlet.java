package servlets;

import pagegenerator.PageGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class LoginServlet extends HttpServlet {

    public LoginServlet(List<String> userTokens) {
        this.userTokens = userTokens;
    }

    private List<String> userTokens;

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> pageVariables = createPageVariablesMap(request);

        PageGenerator pageGenerator = PageGenerator.instance();
        String page = pageGenerator.getPage("login.html", pageVariables);
        try {
            response.getWriter().write(page);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String userToken = UUID.randomUUID().toString();
        userTokens.add(userToken);

        Cookie cookie = new Cookie("user-token", userToken);
        response.addCookie(cookie);
        try {
            response.sendRedirect("/products");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("email", request.getParameter("email"));
        pageVariables.put("password", request.getParameter("password"));

        return pageVariables;
    }
}
