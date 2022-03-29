package servlets;

import dao.JdbcProductDao;
import entity.Product;
import pagegenerator.PageGenerator;
import security.SecurityService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateProductServlet extends HttpServlet {
    SecurityService securityService = new SecurityService();

    public UpdateProductServlet(List<String> userTokens) {
        this.userTokens = userTokens;
    }

    private List<String> userTokens;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        boolean isAuth = securityService.isAuth(request, userTokens);
        if (isAuth) {

            Map<String, Object> pageVariables = createPageVariablesMap(request);
            PageGenerator pageGenerator = PageGenerator.instance();
            String page = pageGenerator.getPage("updateproduct.html", pageVariables);

            try {
                response.getWriter().println(page);
            } catch (IOException e) {
                throw new RuntimeException("Cant get data from request");
            }
            response.setContentType("text/html;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            try {
                response.sendRedirect("/login");
            } catch (IOException exception) {
                throw new RuntimeException("You have to log in");
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        boolean isAuth = securityService.isAuth(request, userTokens);
        if (isAuth) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            int price = Integer.parseInt(request.getParameter("price"));
            String creationdate = request.getParameter("creationdate");

            response.setContentType("text/html;charset=utf-8");
            Product product = new Product(id, name, price, creationdate);

            JdbcProductDao jdbcProductDao = new JdbcProductDao();
            try {
                jdbcProductDao.edit(product);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Cant edit product from database");
            }
            try {
                response.sendRedirect("/products");
            } catch (IOException e) {
                throw new RuntimeException("Cant show update products");
            }
            try {
                response.getWriter().close();
            } catch (IOException exception) {
                throw new RuntimeException("Cant show update products");
            }
        } else {
            try {
                response.sendRedirect("/login");
            } catch (IOException exception) {
                throw new RuntimeException("You have to log in");
            }
        }
    }

    static Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put("id", request.getParameter("id"));

        return pageVariables;
    }
}
