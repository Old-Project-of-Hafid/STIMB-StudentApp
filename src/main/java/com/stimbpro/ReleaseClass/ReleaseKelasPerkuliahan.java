package com.stimbpro.ReleaseClass;

import com.stimbpro.entity.stimb2.KelasPerkuliahan;
import com.stimbpro.entity.stimb2.ListJadwalPerkuliahan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Yusfia Hafid A on 1/11/2016.
 */
public class ReleaseKelasPerkuliahan implements Serializable {
    private Long idKelas;
    private String nama_matakuliah;
    private String kode_matakuliah;
    private String jadwal_matakuliah = "";
    private String instrumen = "";
    private String dosen;

    public ReleaseKelasPerkuliahan(KelasPerkuliahan kelasPerkuliahan, List<ListJadwalPerkuliahan> listJadwalPerkuliahans) {
        idKelas = kelasPerkuliahan.getId();
        nama_matakuliah = kelasPerkuliahan.getMasterMatakuliah().getNamaMatakuliah();
        kode_matakuliah = kelasPerkuliahan.getMasterMatakuliah().getKodeMatakuliah();
        if (kelasPerkuliahan.getListJadwalPerkuliahans() != null) {
            for (int i = 0; i < listJadwalPerkuliahans.size(); i++) {
                if (i < kelasPerkuliahan.getListJadwalPerkuliahans().size() - 1) {
                    jadwal_matakuliah = jadwal_matakuliah + listJadwalPerkuliahans.get(i).getHari() + ", " + listJadwalPerkuliahans.get(i).getWaktu() + "<br>";
                } else {
                    jadwal_matakuliah = jadwal_matakuliah + listJadwalPerkuliahans.get(i).getHari() + ", " + listJadwalPerkuliahans.get(i).getWaktu();
                }
            }
        }
        if (kelasPerkuliahan.getInstrumentMayor() != null)
            instrumen = "(" + kelasPerkuliahan.getInstrumentMayor().getNamaInstrumentMayor() + " " + kelasPerkuliahan.getInstrumentMayor().getKodeInstrumentMayor() + ")";
    }

    public ReleaseKelasPerkuliahan(ListJadwalPerkuliahan listJadwalPerkuliahan) {
        idKelas = listJadwalPerkuliahan.getKelasPerkuliahan().getId();
        nama_matakuliah = listJadwalPerkuliahan.getKelasPerkuliahan().getMasterMatakuliah().getNamaMatakuliah();
        kode_matakuliah = listJadwalPerkuliahan.getKelasPerkuliahan().getMasterMatakuliah().getKodeMatakuliah();
        jadwal_matakuliah = listJadwalPerkuliahan.getHari() + ", " + listJadwalPerkuliahan.getWaktu();
        if (listJadwalPerkuliahan.getKelasPerkuliahan().getInstrumentMayor() != null)
            instrumen = "(" + listJadwalPerkuliahan.getKelasPerkuliahan().getInstrumentMayor().getNamaInstrumentMayor() + " " + listJadwalPerkuliahan.getKelasPerkuliahan().getInstrumentMayor().getKodeInstrumentMayor() + ")";
    }

    public Long getIdKelas() {
        return idKelas;
    }

    public void setIdKelas(Long idKelas) {
        this.idKelas = idKelas;
    }

    public String getNama_matakuliah() {
        return nama_matakuliah;
    }

    public void setNama_matakuliah(String nama_matakuliah) {
        this.nama_matakuliah = nama_matakuliah;
    }

    public String getKode_matakuliah() {
        return kode_matakuliah;
    }

    public void setKode_matakuliah(String kode_matakuliah) {
        this.kode_matakuliah = kode_matakuliah;
    }

    public String getJadwal_matakuliah() {
        return jadwal_matakuliah;
    }

    public void setJadwal_matakuliah(String jadwal_matakuliah) {
        this.jadwal_matakuliah = jadwal_matakuliah;
    }

    public String getInstrumen() {
        return instrumen;
    }

    public void setInstrumen(String instrumen) {
        this.instrumen = instrumen;
    }
}
