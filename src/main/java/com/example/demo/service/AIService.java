package com.example.demo.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.demo.entity.ProductEntity;
import com.example.demo.repository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class AIService {

    private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("0");
    private static final String EMPTY_MENU_MESSAGE =
            "Hien tai menu cua quan chua san sang, ban vui long quay lai sau giup minh nhe.";

    private final ProductRepository productRepository;
    private final WebClient webClient;
    private final String model;
    private final String apiKey;

    public AIService(ProductRepository productRepository,
                     WebClient.Builder webClientBuilder,
                     @Value("${openrouter.api-url}") String openRouterApiUrl,
                     @Value("${openrouter.model}") String model,
                     @Value("${openrouter.api-key:}") String apiKey,
                     @Value("${openrouter.site-url:http://localhost:10000}") String siteUrl,
                     @Value("${openrouter.app-name:CafeManagement}") String appName) {
        this.productRepository = productRepository;
        this.model = model;
        this.apiKey = apiKey;
        this.webClient = webClientBuilder
                .baseUrl(openRouterApiUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader("HTTP-Referer", siteUrl)
                .defaultHeader("X-Title", appName)
                .build();
    }

    public String chat(String userMessage) {
        if (!StringUtils.hasText(apiKey)) {
            throw new IllegalStateException("OPENROUTER_API_KEY chua duoc cau hinh");
        }

        List<ProductEntity> products = productRepository.findByActiveTrue()
                .stream()
                .filter(product -> !Boolean.TRUE.equals(product.getDeleted()))
                .toList();

        if (products.isEmpty()) {
            return EMPTY_MENU_MESSAGE;
        }

        String menuText = buildMenuText(products);
        String prompt = buildSystemPrompt(menuText);
        Map<String, Object> requestBody = buildRequestBody(prompt, userMessage.trim());

        String responseBody = webClient.post()
                .uri("/chat/completions")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonNode responseNode = parseResponse(responseBody);
        String assistantMessage = extractAssistantMessage(responseNode);
        if (!StringUtils.hasText(assistantMessage)) {
            throw new IllegalStateException("Khong lay duoc noi dung tra loi tu OpenRouter");
        }

        return assistantMessage.trim();
    }

    private String buildMenuText(List<ProductEntity> products) {
        return products.stream()
                .map(product -> String.format(Locale.ROOT, "- %s - %s VND",
                        product.getName(),
                        PRICE_FORMAT.format(product.getPrice())))
                .collect(Collectors.joining("\n"));
    }

    private String buildSystemPrompt(String menuText) {
        return """
                Ban la nhan vien quan cafe.
                Nhiem vu cua ban la tu van do uong dua tren menu duoc cung cap, khong duoc tu y them mon ngoai menu.

                Quy tac bat buoc:
                - Chi duoc tra loi dua tren menu ben duoi.
                - Neu khach hoi mon khong co trong menu, hay noi ro quan hien khong co mon do va goi y mon gan nhu cau neu phu hop.
                - Neu khach hoi "co gi ngon", hay goi y 2-3 mon noi bat tu menu.
                - Neu khach hoi ve gia, phai tra loi dung theo gia trong menu.
                - Neu khach hoi "troi nong uong gi", uu tien cac mon goi cam giac mat nhu tra, nuoc trai cay, da xay. Neu menu khong co nhom nay thi chon mon de uong nhat.
                - Neu khach muon it dang, it ngot, de uong, hay goi y mon phu hop theo ten mon trong menu.
                - Tra loi bang tieng Viet, tu nhien, ngan gon, than thien.

                Menu hien co:
                %s
                """.formatted(menuText);
    }

    private Map<String, Object> buildRequestBody(String prompt, String userMessage) {
        return Map.of(
                "model", model,
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", prompt
                        ),
                        Map.of(
                                "role", "user",
                                "content", userMessage
                        )
                ),
                "temperature", 0.2
        );
    }

    private JsonNode parseResponse(String responseBody) {
        try {
            return new com.fasterxml.jackson.databind.ObjectMapper().readTree(responseBody);
        } catch (Exception ex) {
            throw new IllegalStateException("Khong parse duoc response tu OpenRouter", ex);
        }
    }

    private String extractAssistantMessage(JsonNode responseNode) {
        if (responseNode == null) {
            return null;
        }

        JsonNode choicesNode = responseNode.path("choices");
        if (!choicesNode.isArray() || choicesNode.isEmpty()) {
            return null;
        }

        return choicesNode.get(0)
                .path("message")
                .path("content")
                .asText(null);
    }
}
