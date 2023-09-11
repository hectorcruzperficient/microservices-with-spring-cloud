package com.in28minutes.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {

  private CurrencyExchangeProxy currencyExchangeProxy;

  private RestTemplate restTemplate;

  public CurrencyConversionController(
      CurrencyExchangeProxy currencyExchangeProxy, RestTemplate restTemplate) {
    this.currencyExchangeProxy = currencyExchangeProxy;
    this.restTemplate = restTemplate;
  }

  @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateCurrencyConversion(
      @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

    Map<String, String> uriVariables = new HashMap<>();
    uriVariables.put("from", from);
    uriVariables.put("to", to);

    //    ResponseEntity<CurrencyConversion> responseEntity =
    //        new RestTemplate()
    ResponseEntity<CurrencyConversion> responseEntity =
        restTemplate.getForEntity(
            "http://localhost:8000/currency-exchange/from/{from}/to/{to}",
            CurrencyConversion.class,
            uriVariables);

    CurrencyConversion currencyConversion = responseEntity.getBody();

    return new CurrencyConversion(
        currencyConversion.getId(),
        from,
        to,
        quantity,
        currencyConversion.getConversionMultiple(),
        quantity.multiply(currencyConversion.getConversionMultiple()),
        currencyConversion.getEnvironment() + " RestTemplate");
  }

  @GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversion calculateCurrencyConversionFeign(
      @PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {

    CurrencyConversion currencyConversion = currencyExchangeProxy.retrieveExchangeValue(from, to);

    return new CurrencyConversion(
        currencyConversion.getId(),
        from,
        to,
        quantity,
        currencyConversion.getConversionMultiple(),
        quantity.multiply(currencyConversion.getConversionMultiple()),
        currencyConversion.getEnvironment() + " Feign");
  }
}
