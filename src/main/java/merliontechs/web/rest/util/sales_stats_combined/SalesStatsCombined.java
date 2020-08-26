package merliontechs.web.rest.util.sales_stats_combined;

import com.fasterxml.jackson.annotation.JsonValue;

public class SalesStatsCombined {
    private Long dailySales;
    private Long deliveredSales;

    public SalesStatsCombined(Long salesDaily, Long salesDelivered) {
        this.dailySales = salesDaily;
        this.deliveredSales = salesDelivered;
    }

    public Long getDailySales() {
        return dailySales;
    }

    public void setDailySales(Long dailySales) {
        this.dailySales = dailySales;
    }

    public Long getDeliveredSales() {
        return deliveredSales;
    }

    public void setDeliveredSales(Long deliveredSales) {
        this.deliveredSales = deliveredSales;
    }

    @Override
    @JsonValue
    public String toString() {
        return "{" +
            "dailySales :" + dailySales +
            ", deliveredSales: " + deliveredSales +
            '}';
    }
}
