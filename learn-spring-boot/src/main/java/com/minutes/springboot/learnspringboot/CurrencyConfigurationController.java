package com.minutes.springboot.learnspringboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConfigurationController {

    private CurrencyServiceConfiguration currencyServiceConfiguration;

    public CurrencyConfigurationController(CurrencyServiceConfiguration currencyServiceConfiguration) {
        this.currencyServiceConfiguration = currencyServiceConfiguration;
    }

    @RequestMapping("/currency-configuration")
    public CurrencyServiceConfiguration retrieveAllCourse() {
        return currencyServiceConfiguration;
    }
}
