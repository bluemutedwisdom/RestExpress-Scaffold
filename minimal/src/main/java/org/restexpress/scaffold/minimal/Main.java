package org.restexpress.scaffold.minimal;

import java.io.IOException;

import org.restexpress.RestExpress;
import org.restexpress.pipeline.SimpleConsoleLogMessageObserver;
import org.restexpress.scaffold.minimal.serialization.SerializationProvider;
import org.restexpress.util.Environment;

public class Main
{
	private static final String SERVICE_NAME = "TODO: Enter service name";

	public static void main(String[] args) throws Exception
	{
		RestExpress server = initializeServer(args);
		server.awaitShutdown();
	}

	public static RestExpress initializeServer(String[] args) throws IOException
	{
		RestExpress.setSerializationProvider(new SerializationProvider());

		Configuration config = Environment.load(args, Configuration.class);
		RestExpress server = new RestExpress()
				.setName(SERVICE_NAME)
				.setBaseUrl(config.getBaseUrl())
				.setExecutorThreadCount(config.getExecutorThreadPoolSize())
				.addMessageObserver(new SimpleConsoleLogMessageObserver());

		Routes.define(config, server);
		server.bind(config.getPort());
		return server;
    }
}
