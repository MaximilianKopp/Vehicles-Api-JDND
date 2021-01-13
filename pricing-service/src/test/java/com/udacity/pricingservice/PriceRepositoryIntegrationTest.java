package com.udacity.pricingservice;

import com.udacity.pricingservice.model.Price;
import com.udacity.pricingservice.repository.PriceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceRepositoryIntegrationTest {

    private List<Price> getPrices() {
        List<Price> prices = new ArrayList<>();
        prices.add(new Price("USD", new BigDecimal("1234.234"), 1L));
        prices.add(new Price("EURO", new BigDecimal("4343.234"), 2L));
        prices.add(new Price("NOK", new BigDecimal("8765.234"), 3L));
        return prices;
    }

    @MockBean
    private PriceRepository priceRepository;

    @Before
    public void setup() {
        when(priceRepository.findAll()).thenReturn(getPrices());
        when(priceRepository.findById(anyLong())).thenReturn(Optional.of(getPrices().get(0)));
        when(priceRepository.save(any(Price.class))).thenReturn(getPrices().get(1));
    }

    @Test
    public void getAllPrices() {
        List<Price> comparedPrices = (List<Price>) priceRepository.findAll();
        assertEquals(3, comparedPrices.size());
        verify(priceRepository, times(1)).findAll();
    }

    @Test
    public void getPriceById() {
        Optional<Price> comparedPrice = priceRepository.findById(1L);
        comparedPrice.ifPresent(price -> assertEquals("USD", price.getCurrency()));
        comparedPrice.ifPresent(price -> assertEquals(new BigDecimal("1234.234"), price.getPrice()));
        verify(priceRepository, times(1)).findById(anyLong());
    }

    @Test
    public void savePrice() {
        Price price = getPrices().get(1);
        priceRepository.save(price);
        assertEquals(price.getCurrency(), "EURO");
        verify(priceRepository, times(1)).save(price);
    }


    @Test
    public void deletePriceById() {
        Optional<Price> deletedPrice = priceRepository.findById(1L);
        if (deletedPrice.isPresent()) {
            deletedPrice.get().setId(1L);
            priceRepository.deleteById(1L);
        }
        verify(priceRepository, times(1)).findById(1L);
        verify(priceRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteAllPrices() {
        priceRepository.deleteAll();
        verify(priceRepository, times(1)).deleteAll();
    }
}
