package com.in28minutes.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyExchangeController {

  private Logger logger = LoggerFactory.getLogger(CurrencyExchange.class);

  private CurrencyExchangeRepository currencyExchangeRepository;

  private Environment environment;

  public CurrencyExchangeController(
      CurrencyExchangeRepository currencyExchangeRepository, Environment environment) {
    this.currencyExchangeRepository = currencyExchangeRepository;
    this.environment = environment;
  }

  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public CurrencyExchange retrieveExchangeValue(
      @PathVariable String from, @PathVariable String to) {
    logger.info("retrieveExchangeValue called with {} to {}", from , to);

    CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);

    if (currencyExchange == null) {
      throw new RuntimeException("Unable to find data for " + from + " to " + to);
    }

    String port = environment.getProperty("local.server.port");
    currencyExchange.setEnvironment(port);

    return currencyExchange;
  }
}
