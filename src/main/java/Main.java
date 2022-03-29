import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        List<String> userTokens = new ArrayList<>();

        context.addServlet(new ServletHolder(new AllProductsServlet(userTokens)), "/products");
        context.addServlet(new ServletHolder(new AddProductServlet(userTokens)), "/add");
        context.addServlet(new ServletHolder(new DeleteProductServlet(userTokens)), "/delete");
        context.addServlet(new ServletHolder(new UpdateProductServlet(userTokens)), "/update");
        context.addServlet(new ServletHolder(new SearchProductServlet(userTokens)), "/products/search");
        context.addServlet(new ServletHolder(new LoginServlet(userTokens)), "/login");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}