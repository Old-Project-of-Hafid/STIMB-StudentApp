package com.stimbpro.repository;

import com.stimbpro.entity.stimb2.KartuRencanaStudi;
import com.stimbpro.entity.stimb2.KomentarSaran;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Yusfia Hafid A on 1/8/2016.
 */

@Repository
public interface KomentarSaranRepository extends PagingAndSortingRepository<KomentarSaran,Long> {
}
