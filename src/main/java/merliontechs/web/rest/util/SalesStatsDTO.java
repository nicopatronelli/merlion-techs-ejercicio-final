package merliontechs.web.rest.util;

public class SalesStatsDTO {
    private int dayNumber;
    private Long sales;

    public SalesStatsDTO(int day, Long sales) {
        this.dayNumber = day;
        this.sales = sales;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public Long getSales() {
        return sales;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

}
