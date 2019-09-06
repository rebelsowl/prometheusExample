package com.mae;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@SpringBootApplication
@EnablePrometheusEndpoint
public class mainController {
    static final Gauge gibGauge = Gauge.build()
    .name("gibOnline").help("0 if GIB website is online 1 if offline").register();

    static final Counter vkn = Counter.build()
            .name("VKN_Error_Requests").help("Error requests of VKNs")
            .labelNames("VKN").register();

    static final Counter vknGib = Counter.build()
            .name("VKN_GIB_Error_Requests").help("Error requests of VKNs GIB")
            .labelNames("VKN").register();

    @Scheduled(fixedRate = 60000)
    public void gibController(){ 
        System.out.println("sending ping");
        if(Gib.wCheck2()){  // Website is online
            if (gibGauge.get()==1.0) { // shows offline
                gibGauge.dec();
            }else{return;}
        }else{  // Gib website if offline
            if (gibGauge.get()==0.0) { // shows online
                gibGauge.inc();
            } else {return;}
        } 
    }
    @RequestMapping(path = "/vkn/{id}/error/mali")
    public void vknCheck(@PathVariable String  id){
        vkn.labels(id).inc();
    }
    @RequestMapping(path = "/vkn/{id}/error/gib")
    public void vknGibCheck(@PathVariable String  id){
        vknGib.labels(id).inc();
    }

    public static void main(String[] args) {
        SpringApplication.run(mainController.class, args);
    }
}
