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

public class AllProductsServlet extends HttpServlet {
    SecurityService securityService = new SecurityService();

    public AllProductsServlet(List<String> userTokens) {
        this.userTokens = userTokens;
    }

    private List<String> userTokens;

    JdbcProductDao jdbcProductDao = new JdbcProductDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        boolean isAuth = securityService.isAuth(request, userTokens);
        if (isAuth) {
            Map<String, Object> paramMap = new HashMap<>();

            List<Product> products;
            try {
                products = jdbcProductDao.getAllProducts();

                paramMap.put("products", products);

                PageGenerator pageGenerator = PageGenerator.instance();
                String page = pageGenerator.getPage("allproducts.html", paramMap);

                response.setContentType("text/html;charset=utf-8");
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(page);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        } else {
            try {
                response.sendRedirect("/login");
            } catch (IOException exception) {
                throw new RuntimeException("You have to log in");
            }
        }
    }
}
