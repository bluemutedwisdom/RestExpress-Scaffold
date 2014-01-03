package com.strategicgains.restexpress.scaffold.mongodb.config;

import java.util.Properties;

import org.restexpress.RestExpress;
import org.restexpress.util.Environment;

import com.strategicgains.repoexpress.mongodb.MongoConfig;
import com.strategicgains.repoexpress.mongodb.MongodbUuidEntityRepository;
import com.strategicgains.restexpress.scaffold.mongodb.controller.SampleController;
import com.strategicgains.restexpress.scaffold.mongodb.domain.Sample;

public class Configuration
extends Environment
{
	private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

	private static final String PORT_PROPERTY = "port";
	private static final String BASE_URL_PROPERTY = "base.url";
	private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";

	private int port;
	private String baseUrl;
	private int executorThreadPoolSize;
	private MetricsConfig metricsSettings;

	private SampleController sampleController;

	@Override
	protected void fillValues(Properties p)
	{
		this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)));
		this.baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost:" + String.valueOf(port));
		this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
		this.metricsSettings = new MetricsConfig(p);
		MongoConfig mongo = new MongoConfig(p);
		initialize(mongo);
	}

	private void initialize(MongoConfig mongo)
	{
		@SuppressWarnings("unchecked")
        MongodbUuidEntityRepository<Sample> orderRepository = new MongodbUuidEntityRepository<Sample>(mongo.getClient(), mongo.getDbName(), Sample.class);
		sampleController = new SampleController(orderRepository);
	}

	public int getPort()
	{
		return port;
	}
	
	public String getBaseUrl()
	{
		return baseUrl;
	}
	
	public int getExecutorThreadPoolSize()
	{
		return executorThreadPoolSize;
	}

	public MetricsConfig getMetricsConfig()
    {
	    return metricsSettings;
    }

	public SampleController getSampleController()
	{
		return sampleController;
	}
}
