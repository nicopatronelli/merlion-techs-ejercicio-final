package merliontechs.web.rest;

import merliontechs.repository.SalesRepository;
import merliontechs.repository.projections.SalesStats;
import static merliontechs.web.rest.util.SalesStatsUtil.*;
import merliontechs.web.rest.util.sales_stats_combined.SalesStatsCombined;
import merliontechs.web.rest.util.sales_stats_combined.SalesStatsCombinedDTO;
import merliontechs.web.rest.util.SalesStatsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
@Transactional
public class SalesStatsController {

    private final Logger log = LoggerFactory.getLogger(SalesResource.class);

    private static final String ENTITY_NAME = "sales";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalesRepository salesRepository;

    public SalesStatsController(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    @GetMapping("/stats/sales/delivered")
    public List<SalesStats> getNumberOfSalesInStateDeliveredPerDay(
        @RequestParam("year") int year,
        @RequestParam("month") int month
    ) {
        log.debug("REST request to get sales in delivered state per day stats");
        return salesRepository.findNumberOfSalesInStateDeliveredPerDay(year, month);
    }

    @GetMapping("/stats/sales/daily")
    public List<SalesStats> getTotalNumberOfSalesPerDay(
        @RequestParam("year") int year,
        @RequestParam("month") int month
    ) {
        log.debug("REST request to get the total number of sales per day stats");
        return salesRepository.findTotalNumberOfSalesPerDay(year, month);
    }

    @GetMapping("/stats/sales/combined")
    public List<SalesStatsCombinedDTO> getTotalAndDeliveredSalesPerDay(
        @RequestParam("year") int year,
        @RequestParam("month") int month
    ) {
        log.debug("REST request to get sales in delivered state and total sales per day combined stats");
        List<SalesStats> deliveredSales = salesRepository.findNumberOfSalesInStateDeliveredPerDay(year, month);
        List<SalesStats> dailySales = salesRepository.findTotalNumberOfSalesPerDay(year, month);
        Map<LocalDate, SalesStatsCombined> salesStatsCombined = combineSalesStats(deliveredSales, dailySales);
        return mapToSalesStatsCombinedDTO(salesStatsCombined);
    }

}
