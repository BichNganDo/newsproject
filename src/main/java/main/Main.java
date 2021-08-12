package main;

import servlets.admin.ManageAdminServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.admin.LoginServlet;
import servlets.api.ApiAdminServlet;
import servlets.api.ApiAuthorServlet;
import servlets.api.ApiCategoryServlet;
import servlets.api.ApiNewsServlet;
import servlets.author.AddAuthorServlet;
import servlets.author.EditAuthorServlet;
import servlets.author.ManageAuthorServlet;
import servlets.category.AddCategoryServlet;
import servlets.category.EditCategoryServlet;
import servlets.category.ManageCategoryServlet;
import servlets.client.DetailNewsServlet;
import servlets.client.IndexServlet;
import servlets.client.NewsInCategory;
import servlets.news.AddNewsServlet;
import servlets.news.EditNewsServlet;
import servlets.news.ManageNewsServlet;

public class Main {

    public static void main(String[] args) throws Exception {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new ManageAdminServlet()), "/admin/admin");
        context.addServlet(new ServletHolder(new ManageNewsServlet()), "/admin/news");
        context.addServlet(new ServletHolder(new ManageCategoryServlet()), "/admin/category");
        context.addServlet(new ServletHolder(new ManageAuthorServlet()), "/admin/author");
        context.addServlet(new ServletHolder(new LoginServlet()), "/login");

        context.addServlet(new ServletHolder(new AddAuthorServlet()), "/admin/addauthor");
        context.addServlet(new ServletHolder(new AddCategoryServlet()), "/admin/addcategory");
        context.addServlet(new ServletHolder(new AddNewsServlet()), "/admin/addnews");

        context.addServlet(new ServletHolder(new EditAuthorServlet()), "/admin/editauthor");
        context.addServlet(new ServletHolder(new EditCategoryServlet()), "/admin/editcategory");
        context.addServlet(new ServletHolder(new EditNewsServlet()), "/admin/editnews");

        context.addServlet(new ServletHolder(new ApiAuthorServlet()), "/admin/api/author");
        context.addServlet(new ServletHolder(new ApiCategoryServlet()), "/admin/api/category");
        context.addServlet(new ServletHolder(new ApiNewsServlet()), "/admin/api/news");
        context.addServlet(new ServletHolder(new ApiAdminServlet()), "/admin/api/admin");

        context.addServlet(new ServletHolder(new IndexServlet()), "/index");
        context.addServlet(new ServletHolder(new NewsInCategory()), "/danh-muc");
        context.addServlet(new ServletHolder(new DetailNewsServlet()), "/chi-tiet-tin-tuc");

        ContextHandler resourceHandler = new ContextHandler("/static");
        String resource = "./public";
        if (!resource.isEmpty()) {
            resourceHandler.setResourceBase(resource);
            resourceHandler.setHandler(new ResourceHandler());
        }

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resourceHandler, context});

        Server server = new Server(8080);

        server.setHandler(handlers);

        server.start();

        System.out.println("Server started");

        server.join();
    }
}
