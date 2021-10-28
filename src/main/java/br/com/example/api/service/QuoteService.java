package br.com.example.api.service;

import java.util.Date;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.example.api.model.Quote;
import br.com.example.api.repositorie.QuoteRepository;
import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class QuoteService {

	private QuoteRepository repo;


	public QuoteService(QuoteRepository repo) {
		this.repo = repo;
	}
	
	@RestResource(rel = "quotes", path = "quotes")
	public Page<Quote> findAllBySymbol(@Param("symbol") String symbol, Pageable page){
		return this.repo.findAllBySymbol(symbol, page);
	}
	
	@Scheduled(fixedDelay = 2500)
	public void generateData() {
		log.info(this.repo.findTopBySymbolOrderByTimestampDesc("TESTE")
				.map(this::generateNewData)
				.orElseGet(this::initializeData));
	}
	private Quote initializeData() {
		Date date = new Date();
		return this.repo.save(Quote.builder()
				.symbol("TESTE")
				.openValue(0.2222222)
				.closeValue(0.2222222)
				.timestamp(date)
				.build());
	}
	private Quote generateNewData(Quote quote) {
		Date date = new Date();
		return this.repo.save(Quote.builder()
				.symbol(quote.getSymbol())
				.openValue((quote.getOpenValue() * new RandomDataGenerator().nextUniform(-0.10, 0.10)))
				.closeValue((quote.getCloseValue()* new RandomDataGenerator().nextUniform(-0.10, 0.10)))
				.timestamp(date)
				.build());
	}
}
