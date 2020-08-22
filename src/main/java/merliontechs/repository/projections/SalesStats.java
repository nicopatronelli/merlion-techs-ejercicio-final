package merliontechs.repository.projections;

import java.time.LocalDate;

public interface SalesStats {
    LocalDate getDate();
    Long getSales();
}
