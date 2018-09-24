package Controller;

import Service.MqSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/log")
public class MqSenderController {

    @Autowired
    private MqSenderService mqSenderService;

    @RequestMapping("/sendlog")
    public void sendLog(@RequestParam("msg")String msg) {

        mqSenderService.send(msg);
    }

}
