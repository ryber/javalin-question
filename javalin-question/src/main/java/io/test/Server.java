package io.test;

import io.javalin.Javalin;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class Server {
    public static void main(String[] args){
        Javalin app = Javalin.create(c -> {
            c.staticFiles.add("public/");
            c.jetty.addConnector((server, httpConfiguration) -> {
                ServerConnector sslConnector = new ServerConnector(server, getSslContextFactory());
                sslConnector.setPort(443);
                return sslConnector;
            });
            c.jetty.addConnector((server, httpConfiguration) -> {
                ServerConnector connector = new ServerConnector(server);
                connector.setPort(80);
                return connector;
            });

        }).start(8080);
    }

    private static SslContextFactory.Server getSslContextFactory() {
        var sslContextFactory = new SslContextFactory.Server();
        sslContextFactory.setKeyStorePath(Server.class.getResource("/keystore.jks").toExternalForm());
        sslContextFactory.setKeyStorePassword("password");
        return sslContextFactory;
    }
}
