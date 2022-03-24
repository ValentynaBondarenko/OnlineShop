import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.*;

import javax.servlet.Servlet;

public class Main {

    public static void main(String[] args) throws Exception {

//        AllRequestsServlet allRequestsServlet = new AllRequestsServlet();
        AllProductsServlet allProductsServlet = new AllProductsServlet();
//        AddUserServlet addUserServlet = new AddUserServlet();
//        DeleteUserServlet deleteUserServlet = new DeleteUserServlet();
//        UpdateUserServlet updateUserServlet = new UpdateUserServlet();
//        SearchUserServlet searchUserServlet = new SearchUserServlet();
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

//        context.addServlet(new ServletHolder(allRequestsServlet), "/*");
        context.addServlet(new ServletHolder(allProductsServlet), "/products");
//        context.addServlet(new ServletHolder(addUserServlet), "/add");
//        context.addServlet(new ServletHolder(deleteUserServlet), "/delete");
//        context.addServlet(new ServletHolder(updateUserServlet), "/update");
//        context.addServlet(new ServletHolder(searchUserServlet), "/users/search");

        Server server = new Server(8080);
        server.setHandler(context);

        server.start();
    }
}