package servlets;

import dao.JdbcProductDao;
import pagegenerator.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DeleteProductServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        Map<String, Object> pageVariables = createPageVariablesMap(request);

        PageGenerator pageGenerator = PageGenerator.instance();

        String page = pageGenerator.getPage("deleteproduct.html", pageVariables);

        try {
            response.getWriter().println(page);
        } catch (IOException e) {
            throw new RuntimeException("Cant get data from request about delete user");
        }
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");

        response.setContentType("text/html;charset=utf-8");

        JdbcProductDao jdbcProductDao = new JdbcProductDao();
        try {
            jdbcProductDao.remove(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            response.sendRedirect("/products");
        } catch (IOException e) {
            throw new RuntimeException("Cant delete product from database");
        }
        try {
            response.getWriter().close();
        } catch (IOException exception) {
            throw new RuntimeException("Cant show update products");
        }
    }

    static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", request.getParameter("id"));

        return pageVariables;
    }
}
