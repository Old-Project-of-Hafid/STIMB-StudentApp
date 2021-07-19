package com.stimbpro.repository;

import com.stimbpro.entity.stimb2.KartuRencanaStudi;
import com.stimbpro.entity.stimb2.KelasPerkuliahan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Yusfia Hafid A on 1/11/2016.
 */
@Repository
public interface KelasPerkuliahanRepository extends PagingAndSortingRepository<KelasPerkuliahan,Long> {
    @Query("SELECT p FROM KelasPerkuliahan p, ReffTahunAjaran r WHERE p.reffTahunAjaran = r AND p.statusopen = 'buka' AND r.namaTahunAjaran = :thjar AND r.periode = :per")
    public List<KelasPerkuliahan> findKelasBuka(@Param("thjar") String thjar, @Param("per") String per);
}
