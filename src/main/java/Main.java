import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;


public class Main {

    public static void main(String[] args) throws Exception {

//        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();
        AllProductsServlet allProductsServlet = new AllProductsServlet();
        AddProductServlet addProductServlet = new AddProductServlet();
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet();
        UpdateProductServlet updateProductServlet = new UpdateProductServlet();
        SearchProductServlet searchProducServlet = new SearchProductServlet();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

//        context.addServlet(new ServletHolder(allRequestsServlet), "/*");
        context.addServlet(new ServletHolder(allProductsServlet), "/products");
        context.addServlet(new ServletHolder(addProductServlet), "/add");
        context.addServlet(new ServletHolder(deleteProductServlet), "/delete");
        context.addServlet(new ServletHolder(updateProductServlet), "/update");
        context.addServlet(new ServletHolder(searchProducServlet), "/users/search");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}