package com.stimbpro.repository;

import com.stimbpro.entity.stimb2.KalenderAkademik;
import com.stimbpro.entity.stimb2.KartuRencanaStudi;
import com.stimbpro.entity.stimb2.ListKalenderAkademik;
import com.stimbpro.entity.stimb2.ReffTahunAjaran;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Yusfia Hafid A on 12/30/2015.
 */
public interface ListKalenderAkademikRepository extends PagingAndSortingRepository<ListKalenderAkademik, Long> {
    @Query("SELECT k FROM ReffTahunAjaran p, ListKalenderAkademik k WHERE k.reffTahunAjaran = p AND p.namaTahunAjaran LIKE %:thjar% AND p.periode LIKE %:sem%") //ORDER BY k.tanggal ASC")
    public List<ListKalenderAkademik> findReferedKalenderAkademikList(@Param("thjar") String thjar, @Param("sem") String sem);
}