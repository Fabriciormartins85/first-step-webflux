package br.com.example.api.repositorie;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.example.api.model.Quote;


@Repository
public interface QuoteRepository extends PagingAndSortingRepository<Quote, Long> {

	Page<Quote> findAllBySymbol(String symbol, Pageable page);

	Optional<Quote> findTopBySymbolOrderByTimestampDesc(String string);
}
