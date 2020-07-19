package github.com.felipebarbosaferreira;

import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.MongoDBContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class MongoDBLifeCycleControlTest implements QuarkusTestResourceLifecycleManager {

	private static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:3.6");

	@Override
	public Map<String, String> start() {
		mongoDBContainer.start();
		Map<String, String> properties = new HashMap<>();
		properties.put("quarkus.mongodb.connection-string",
				"mongodb://" + mongoDBContainer.getContainerIpAddress() + ":" + mongoDBContainer.getFirstMappedPort());
		return properties;
	}

	@Override
	public void stop() {
		if (mongoDBContainer.isCreated())
			mongoDBContainer.start();
	}

}
