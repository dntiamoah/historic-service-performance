package com.nafifsource.hsp.service_performance.batch;

import com.nafifsource.hsp.service_performance.domain.BasicScheduleDailyPerformance;
import com.nafifsource.hsp.service_performance.domain.BasicScheduleLocationDailyPerformance;
import com.nafifsource.hsp.service_performance.domain.BasicScheduleLocationDailyPerformanceId;
import com.nafifsource.hsp.service_performance.domain.DailyPerformanceServiceRID;
import com.nafifsource.hsp.service_performance.dto.ServiceAttributesDetailsResponseDTO;
import com.nafifsource.hsp.service_performance.dto.ServiceDetailsRequestDTO;
import com.nafifsource.hsp.service_performance.exception.HistoricServicePerformanceMapException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class HistoricServicePerformanceProcessor implements ItemProcessor<DailyPerformanceServiceRID, BasicScheduleDailyPerformance> {

    private final WebClient webClient;
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricServicePerformanceProcessor.class);

    public HistoricServicePerformanceProcessor(@Qualifier("webClientRockShore") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public BasicScheduleDailyPerformance process(DailyPerformanceServiceRID item) {
        // use item to get serviceAttributesDetails with https://hsp-prod.rockshore.net/api/v1/serviceDetails
        ServiceDetailsRequestDTO reqRID = ServiceDetailsRequestDTO.builder()
                .rid(item.getRid())
                .build();
        try {
            ServiceAttributesDetailsResponseDTO serviceAttributesDetails =
                    webClient.post()
                            .uri("/serviceDetails")
                            .bodyValue(reqRID)
                            .retrieve()
                            .bodyToMono(ServiceAttributesDetailsResponseDTO.class)
                            .onErrorMap(WebClientResponseException.class, ex ->
                                    new IllegalArgumentException("Something went wrong with your request. error message: " + ex.getMessage()))
                            .block();
            AtomicInteger counter = new AtomicInteger(0);
            return Optional.ofNullable(serviceAttributesDetails)
                    .map(ServiceAttributesDetailsResponseDTO::getServiceAttributesDetailDTO)
                    .map(sad ->
                            BasicScheduleDailyPerformance.builder()
                                    .rid(sad.getRid())
                                    .dateOfService(LocalDate.parse(sad.getDateOfService()))
                                    .tocCode(sad.getTocCode())
                                    .locations(sad.getLocations()
                                            .stream()
                                            .map(location -> BasicScheduleLocationDailyPerformance.builder()
                                                    .id(BasicScheduleLocationDailyPerformanceId.builder()
                                                            .rid(sad.getRid())
                                                            .location(location.getLocation())
                                                            .build())
                                                    .publishedTimeOfArrival(location.getPublished_gbtt_pta())
                                                    .actualTimeOfArrival(location.getActual_ta())
                                                    .publishedTimeOfDeparture(location.getPublished_gbtt_ptd())
                                                    .dateOfService(LocalDate.parse(sad.getDateOfService()))
                                                    .actualTimeOfDeparture(location.getActual_td())
                                                    .lateCancellationReason(location.getLateCancelReason())
                                                    .callingPointOrder(counter.getAndIncrement())
                                                    .build()
                                            ).collect(Collectors.toList()))
                                    .build())
                    .orElseThrow(() -> new HistoricServicePerformanceMapException("Response mapping error for HSP-PROD API - RID: " + item.getRid()));
        } catch (HistoricServicePerformanceMapException e) {
            LOGGER.error("Error HistoricServicePerformanceMapException - {}", e.getLocalizedMessage());
        } catch (RuntimeException e) {
            String dateOfService = item.getRid().substring(1, 8);
            LOGGER.error("Skipping HistoricServicePerformance dateOfService: {}, trainuid: {},  rid: {} due to the following error: {}",
                    dateOfService,
                    item.getTrainUid(),
                    item.getRid(),
                    e.getLocalizedMessage());
        }
        // log the error and return null so writer does not break
        return null;
    }
}