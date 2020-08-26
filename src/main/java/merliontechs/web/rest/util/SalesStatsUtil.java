package merliontechs.web.rest.util;

import merliontechs.repository.projections.SalesStats;
import merliontechs.web.rest.util.sales_stats_combined.SalesStatsCombined;
import merliontechs.web.rest.util.sales_stats_combined.SalesStatsCombinedDTO;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SalesStatsUtil {

//    /**
//     * Map a list of SalesStats into a list of SalesStatsDTO
//     *
//     * @param sales is the list of {@link SalesStats} to map.
//     * @return a list of {@link SalesStatsDTO}.
//     */
//    public static List<SalesStatsDTO> mapToSalesStatsDTO(List<SalesStats> sales) {
//        return sales.stream()
//            .map(
//                s -> new SalesStatsDTO(s.getDate().getDayOfMonth(), s.getSales())
//            )
//            .collect(Collectors.toList());
//    }

     /**
     * Merge two lists of sales into a map of <{@link LocalDate}, {@link SalesStatsCombined}>
     *
     * @param deliveredSales is the first one list of {@link SalesStats} to combine.
     * @param dailySales is the another list of {@link SalesStats} to combine.
     * @return a map of <{@link LocalDate}, {@link SalesStatsCombined}>.
     */
    public static Map<LocalDate, SalesStatsCombined> combineSalesStats(List<SalesStats> deliveredSales, List<SalesStats> dailySales ) {
        Map<LocalDate, SalesStatsCombined> salesStatsCombined = new HashMap<>();
        deliveredSales.forEach(
            d -> salesStatsCombined.put(
                d.getDate(),
                new SalesStatsCombined(0L, d.getSales())
            )
        );
        dailySales.forEach(
            d -> {
                if(salesStatsCombined.containsKey(d.getDate()))
                    salesStatsCombined.put(
                        d.getDate(),
                        new SalesStatsCombined(d.getSales(), salesStatsCombined.get(d.getDate()).getDeliveredSales())
                    );
                else
                    salesStatsCombined.put(
                        d.getDate(),
                        new SalesStatsCombined(d.getSales(), 0L)
                    );
            }
        );
        return salesStatsCombined;
    }

    /**
     * Map a map of <{@link LocalDate}, {@link SalesStatsCombined}> into a list of {@link SalesStatsDTO}
     *
     * @param salesStatsCombinedMap is the source map.
     * @return a list of {@link SalesStatsCombinedDTO}.
     */
    public static List<SalesStatsCombinedDTO> mapToSalesStatsCombinedDTO(Map<LocalDate, SalesStatsCombined> salesStatsCombinedMap) {
        List<SalesStatsCombinedDTO> listOfsalesStatsCombined = new ArrayList<>();
        salesStatsCombinedMap.forEach(
            (date, value) -> listOfsalesStatsCombined.add(
                new SalesStatsCombinedDTO(
                    date,
                    salesStatsCombinedMap.get(date).getDailySales(),
                    salesStatsCombinedMap.get(date).getDeliveredSales()
                ))
        );

        listOfsalesStatsCombined.sort(Comparator.comparing(SalesStatsCombinedDTO::getDate));
        return listOfsalesStatsCombined;
    }

}
