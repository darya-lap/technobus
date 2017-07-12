package main;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import services.H2DBServiceImpl;
import servlets.getScheduleServlet;

/**
 * Created by antonandreev on 06/07/2017.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        H2DBServiceImpl dbService = H2DBServiceImpl.getInstance();

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(new getScheduleServlet(dbService)), "/schedule");

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase("src/main/web");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] {resourceHandler, contextHandler});

        Server server = new Server(8081);
        server.setHandler(handlers);

        server.join();
        server.start();
    }
}
