package xyz.mdou.springboot.properties.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mdou.springboot.properties.property.ApplicationProperty;
import xyz.mdou.springboot.properties.property.AuthorProperty;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("config")
public class PropertiesController {

    @Autowired
    private ApplicationProperty applicationProperty;

    @Autowired
    private AuthorProperty authorProperty;

    @GetMapping
    public String getConfigs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put("appName", applicationProperty.getAppName());
        configs.put("version", applicationProperty.getVersion());
        configs.put("author", authorProperty.getName());
        configs.put("homepage", authorProperty.getHomepage());
        return new Gson().toJson(configs);
    }
}
