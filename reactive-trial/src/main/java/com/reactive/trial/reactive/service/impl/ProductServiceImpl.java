package com.reactive.trial.reactive.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reactive.trial.reactive.dto.ProductDto;
import com.reactive.trial.reactive.entity.Product;
import com.reactive.trial.reactive.repo.ProductRepo;
import com.reactive.trial.reactive.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<ProductDto> createProduct(Mono<ProductDto> productDto) {
        return productDto
                .map(item -> {
                    Product product = new Product();
                    product.setId(UUID.randomUUID().toString());
                    System.out.println("product id: " + product.getId());
                    product.setName(item.getName());
                    product.setPrice(item.getPrice());
                    return product;
                })
                .flatMap(r2dbcEntityTemplate::insert)
                .map(item -> {
                    ProductDto pDto = new ProductDto();
                    pDto.setName(item.getName());
                    pDto.setPrice(item.getPrice());
                    return pDto;
                });
    }

    @Override
    public Mono<ProductDto> getProduct(String id) {
        return productRepo.findById(id).map(i -> {
            ProductDto pDto = new ProductDto();
            pDto.setName(i.getName());
            pDto.setPrice(i.getPrice());
            return pDto;
        });
    }

    @Override
    public Flux<ProductDto> getAllProducts(String currency) {
        return productRepo.findAll().handle((item, sink) -> {
            ProductDto pDto = new ProductDto();
            pDto.setName(item.getName());
            Double rate;
            try {
                rate = parseExchangeRates(wordUSDExchangeRate, currency);
            } catch (Exception e) {
                sink.error(new RuntimeException(e));
                return;
            }
            pDto.setPrice((int)(item.getPrice() * rate));
            sink.next(pDto);
        });
    }

    public String getExchangeRate() {
        return webClientBuilder.baseUrl("https://api.exchangerate-api.com/v4")
                .build()
                .get()
                .uri("/latest/USD")
                .retrieve()
                .bodyToMono(String.class).block();
    }

    public double parseExchangeRates(String jsonResponse, String currency) throws Exception {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode ratesNode = rootNode.path("rates");
            return ratesNode.path(currency).asDouble();
        } catch (Exception e) {
            log.error(e.getMessage());
            return 1;
        }
    }

    private final String wordUSDExchangeRate = """
            {"rates": {
                    "USD": 1,
                    "AED": 3.67,
                    "AFN": 68,
                    "ALL": 92.3,
                    "AMD": 387.44,
                    "ANG": 1.79,
                    "AOA": 921.03,
                    "ARS": 998.75,
                    "AUD": 1.53,
                    "AWG": 1.79,
                    "AZN": 1.7,
                    "BAM": 1.84,
                    "BBD": 2,
                    "BDT": 119.53,
                    "BGN": 1.84,
                    "BHD": 0.376,
                    "BIF": 2929.77,
                    "BMD": 1,
                    "BND": 1.34,
                    "BOB": 6.94,
                    "BRL": 5.75,
                    "BSD": 1,
                    "BTN": 84.42,
                    "BWP": 13.64,
                    "BYN": 3.31,
                    "BZD": 2,
                    "CAD": 1.39,
                    "CDF": 2854.65,
                    "CHF": 0.882,
                    "CLP": 980.35,
                    "CNY": 7.24,
                    "COP": 4376.68,
                    "CRC": 511.85,
                    "CUP": 24,
                    "CVE": 103.82,
                    "CZK": 23.91,
                    "DJF": 177.72,
                    "DKK": 7.02,
                    "DOP": 60.41,
                    "DZD": 133.79,
                    "EGP": 49.21,
                    "ERN": 15,
                    "ETB": 120.14,
                    "EUR": 0.942,
                    "FJD": 2.26,
                    "FKP": 0.783,
                    "FOK": 7.02,
                    "GBP": 0.783,
                    "GEL": 2.73,
                    "GGP": 0.783,
                    "GHS": 16.3,
                    "GIP": 0.783,
                    "GMD": 71.61,
                    "GNF": 8581.96,
                    "GTQ": 7.73,
                    "GYD": 209.19,
                    "HKD": 7.78,
                    "HNL": 25.28,
                    "HRK": 7.09,
                    "HTG": 131.52,
                    "HUF": 386.6,
                    "IDR": 15809.85,
                    "ILS": 3.76,
                    "IMP": 0.783,
                    "INR": 84.42,
                    "IQD": 1312.29,
                    "IRR": 42060,
                    "ISK": 139.24,
                    "JEP": 0.783,
                    "JMD": 158.65,
                    "JOD": 0.709,
                    "JPY": 154.44,
                    "KES": 128.9,
                    "KGS": 85.73,
                    "KHR": 4040.98,
                    "KID": 1.53,
                    "KMF": 463.23,
                    "KRW": 1406.46,
                    "KWD": 0.307,
                    "KYD": 0.833,
                    "KZT": 495.4,
                    "LAK": 22002.09,
                    "LBP": 89500,
                    "LKR": 292.43,
                    "LRD": 189.27,
                    "LSL": 18.1,
                    "LYD": 4.84,
                    "MAD": 9.92,
                    "MDL": 17.95,
                    "MGA": 4643.44,
                    "MKD": 57.73,
                    "MMK": 2101.91,
                    "MNT": 3429.67,
                    "MOP": 8.01,
                    "MRU": 39.87,
                    "MUR": 46.88,
                    "MVR": 15.46,
                    "MWK": 1743.98,
                    "MXN": 20.59,
                    "MYR": 4.44,
                    "MZN": 63.92,
                    "NAD": 18.1,
                    "NGN": 1677.32,
                    "NIO": 36.86,
                    "NOK": 11.07,
                    "NPR": 135.07,
                    "NZD": 1.69,
                    "OMR": 0.384,
                    "PAB": 1,
                    "PEN": 3.78,
                    "PGK": 4.01,
                    "PHP": 58.72,
                    "PKR": 278.28,
                    "PLN": 4.1,
                    "PYG": 7861.4,
                    "QAR": 3.64,
                    "RON": 4.68,
                    "RSD": 110.24,
                    "RUB": 98.03,
                    "RWF": 1378.89,
                    "SAR": 3.75,
                    "SBD": 8.51,
                    "SCR": 13.61,
                    "SDG": 449.4,
                    "SEK": 10.9,
                    "SGD": 1.34,
                    "SHP": 0.783,
                    "SLE": 22.69,
                    "SLL": 22686.44,
                    "SOS": 571.85,
                    "SRD": 35.48,
                    "SSP": 3449.69,
                    "STN": 23.07,
                    "SYP": 12916.27,
                    "SZL": 18.1,
                    "THB": 34.82,
                    "TJS": 10.69,
                    "TMT": 3.5,
                    "TND": 3.14,
                    "TOP": 2.35,
                    "TRY": 34.38,
                    "TTD": 6.77,
                    "TVD": 1.53,
                    "TWD": 32.45,
                    "TZS": 2647.08,
                    "UAH": 41.39,
                    "UGX": 3664.85,
                    "UYU": 42.01,
                    "UZS": 12798.11,
                    "VES": 44.93,
                    "VND": 25358.34,
                    "VUV": 120.62,
                    "WST": 2.76,
                    "XAF": 617.64,
                    "XCD": 2.7,
                    "XDR": 0.758,
                    "XOF": 617.64,
                    "XPF": 112.36,
                    "YER": 250.08,
                    "ZAR": 18.1,
                    "ZMW": 27.26,
                    "ZWL": 25.48
                }}""";
}

