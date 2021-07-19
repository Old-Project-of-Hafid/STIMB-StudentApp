package com.stimbpro.repository;

import com.stimbpro.entity.stimb2.KartuRencanaStudi;
import com.stimbpro.entity.stimb2.ListDosenKelas;
import com.stimbpro.entity.stimb2.ListKalenderAkademik;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yusfia Hafid A on 1/2/2016.
 */
@Repository
public interface ListDosenKelasRepository extends PagingAndSortingRepository<ListDosenKelas, Long> {
    @Query("SELECT p FROM ListDosenKelas p, MasterDosen m, KelasPerkuliahan k WHERE m = p.masterDosen AND p.kelasPerkuliahan = k AND k.id = :mstdid")
    public ListDosenKelas findByKelasId(@Param("mstdid") Long mstdid);
}
