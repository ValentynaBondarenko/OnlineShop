package servlets;

import dao.JdbcProductDao;
import entity.Product;
import pagegenerator.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchProductServlet extends HttpServlet {
    JdbcProductDao jdbcProductDao = new JdbcProductDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("start search");

        List<Product> products = jdbcProductDao.search(request.getParameter("searchText"));
        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("products", products);
        response.setContentType("text/html; charset=utf-8");
        try {
            response.getWriter().println(PageGenerator.instance().getPage("allusers.html", parametersMap));
        } catch (IOException exception) {
            throw new RuntimeException("Cant search product from database");
        }
    }

}

