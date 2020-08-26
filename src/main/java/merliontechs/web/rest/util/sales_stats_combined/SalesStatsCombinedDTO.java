package merliontechs.web.rest.util.sales_stats_combined;

import java.time.LocalDate;

public class SalesStatsCombinedDTO {
    private LocalDate date;
    private Long dailySales;
    private Long deliveredSales;

    public SalesStatsCombinedDTO(LocalDate date, Long dailySales, Long deliveredSales) {
        this.date = date;
        this.dailySales = dailySales;
        this.deliveredSales = deliveredSales;
    }

    public LocalDate getDate() {
        return date;
    }

    public Long getDailySales() {
        return dailySales;
    }

    public Long getDeliveredSales() {
        return deliveredSales;
    }

    @Override
    public String toString() {
        return "SalesStatsCombinedDTO{" +
            "date=" + date +
            ", dailySales=" + dailySales +
            ", deliveredSales=" + deliveredSales +
            '}';
    }
}
