package ro.unibuc.hello.controller;

import java.util.concurrent.atomic.AtomicLong;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ro.unibuc.hello.data.InformationEntity;
import ro.unibuc.hello.data.InformationRepository;
import ro.unibuc.hello.dto.Greeting;

@Controller
public class HelloWorldController {

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    MeterRegistry metricsRegistry;

    private static final String helloTemplate = "Hello, %s!";
    private static final String informationTemplate = "%s : %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello-world")
    @ResponseBody
    public Greeting sayHello(@RequestParam(name="name", required=false, defaultValue="Stranger") String name) {
        // count invocations
        metricsRegistry.counter("invocation_count", "endpoint", "hello").increment(counter.incrementAndGet());
        return new Greeting(counter.get(), String.format(helloTemplate, name));
    }

    @GetMapping("/info")
    @ResponseBody
    public Greeting listAll(@RequestParam(name="title", required=false, defaultValue="Overview") String title) {
        // record invocation duration
        return metricsRegistry.timer("invocation_duration", "endpoint", "info").record(()->{
            InformationEntity entity = informationRepository.findByTitle(title);
            return new Greeting(counter.incrementAndGet(), String.format(informationTemplate, entity.title, entity.description));
        });
    }

}