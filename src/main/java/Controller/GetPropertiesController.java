package Controller;

import Service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/properties")
public class GetPropertiesController {

    @Autowired
    PropertiesService propertiesService;

    @RequestMapping("/getProperties")
    public Callable<String> getProperties() {

        Callable ca = new Callable() {
            @Override
            public Object call() throws Exception {
                return propertiesService.getProperties();
            }
        };

        return ca;
    }



}
