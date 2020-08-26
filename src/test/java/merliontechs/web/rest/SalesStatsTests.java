package merliontechs.web.rest;

import merliontechs.TestApp;
import merliontechs.domain.Product;
import merliontechs.domain.Sales;
import merliontechs.domain.enumeration.State;
import merliontechs.repository.ProductRepository;
import merliontechs.repository.SalesRepository;
import merliontechs.repository.projections.SalesStats;
import org.junit.Before;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;

/**
 * Integration tests for the {@link ProductResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SalesStatsTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SalesRepository salesRepository;

    private Product macBookPro, tabletHuweiT5, ps4, kindlePW, wdElements1Tera, nintendoSwitch,
        samsungTabA, samsungSSD, nintendo3DS,  hdmiAdapter;

    private Sales saleAugustFirstA, saleAugustFirstB, saleAugustSecondA, saleAugustSecondB,
        saleAugustSecondC, saleAugustThirdA;

    @Before
    public void initTest() {
        saveProducts();
        saveSales();
    }

    @Test
    void theSalesInDeliveredStateAreGroupedByDay() {
        List<SalesStats> sales = salesRepository.findNumberOfSalesInStateDeliveredPerDay(2020, 8);
        sales.forEach(
            s -> System.out.println("date" + s.getDate() + "sales" + s.getSales())
        );
        assertEquals(4, sales.size());
    }

//    @Test
//    void theNumberOfSalesInDeliveredStatePerDayIsCorrect() {
//        List<SalesStats> sales = salesRepository.findNumberOfSalesInStateDeliveredPerDay(2020, 8);
//        Long deliveredSalesOnAugustSecond = sales.get(1).getSales();
//        assertEquals(2, deliveredSalesOnAugustSecond);
//    }
//
//    @Test
//    void findTotalNumberOfSalesPerDayReturnsTheTotalNumberOfSalesInAnyState() {
//        List<SalesStats> sales = salesRepository.findTotalNumberOfSalesPerDay(2020, 8);
//        assertEquals(6, sales.size());
//    }

    private void saveProducts() {
        macBookPro = new Product("Mac Book Pro", new BigDecimal(649.99));
        productRepository.save(macBookPro);
        tabletHuweiT5 = new Product("Tablet Huawei T5", new BigDecimal(149.99));
        productRepository.save(tabletHuweiT5);
        ps4 = new Product("Playstation 4", new BigDecimal(399.99));
        productRepository.save(ps4);
        kindlePW = new Product("Kindle Paperwhite", new BigDecimal(99.99));
        productRepository.save(kindlePW);
        wdElements1Tera = new Product("WD Elements 1 TB", new BigDecimal(129.99));
        productRepository.save(wdElements1Tera);
        nintendoSwitch = new Product("Nintendo Switch", new BigDecimal(299));
        productRepository.save(nintendoSwitch);
        samsungTabA = new Product("Samsung Tab A", new BigDecimal(399.99));
        productRepository.save(samsungTabA);
        samsungSSD = new Product("Samsung Evo SSD 256 GB", new BigDecimal(89.99));
        productRepository.save(samsungSSD);
        nintendo3DS = new Product("Nintendo 3DS", new BigDecimal(129.99));
        productRepository.save(nintendo3DS);
        hdmiAdapter = new Product("HDMI VGA Adapter", new BigDecimal(9.99));
        productRepository.save(hdmiAdapter);
    }

    private void saveSales() {
        saleAugustFirstA = new Sales(State.DELIVERED, LocalDate.parse("2020-08-01"), nintendoSwitch);
        salesRepository.save(saleAugustFirstA);
        saleAugustFirstB = new Sales(State.DELIVERED, LocalDate.parse("2020-08-01"), nintendoSwitch);
        salesRepository.save(saleAugustFirstB);
        saleAugustSecondA = new Sales(State.DELIVERED, LocalDate.parse("2020-08-02"), macBookPro);
        salesRepository.save(saleAugustSecondA);
        saleAugustSecondB = new Sales(State.IN_CHARGE, LocalDate.parse("2020-08-02"), tabletHuweiT5);
        salesRepository.save(saleAugustSecondB);
        saleAugustSecondC = new Sales(State.DELIVERED, LocalDate.parse("2020-08-02"), samsungSSD);
        salesRepository.save(saleAugustSecondC);
        saleAugustThirdA = new Sales(State.SHIPPED, LocalDate.parse("2020-08-03"), nintendoSwitch);
        salesRepository.save(saleAugustThirdA);
    }
}
