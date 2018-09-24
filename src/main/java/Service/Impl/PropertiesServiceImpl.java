package Service.Impl;

import Service.PropertiesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

@Service
public class PropertiesServiceImpl implements PropertiesService {

    @Value("${server.port}")
    private String serverPort;

    @Override
    public String getProperties() {
        return serverPort;
    }
}
