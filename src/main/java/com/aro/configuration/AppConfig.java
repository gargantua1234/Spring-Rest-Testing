package com.aro.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Arek on 16.02.2017.
 */


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.aro")
public class AppConfig {

}
