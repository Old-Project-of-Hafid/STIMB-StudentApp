package com.stimbpro.ReleaseClass;

import com.stimbpro.entity.stimb2.KartuRencanaStudi;
import org.apache.wicket.util.io.IClusterable;

import java.io.Serializable;

/**
 * Created by Yusfia Hafid A on 12/27/2015.
 */
public class ReleaseKhs implements IClusterable, Serializable {
    private String kodeMataKuliah;
    private String namaMataKuliah;
    private Integer sksMataKuliah;
    private String nilai = "-";
    private String nilaiUTS = "-";
    private String nilaiUAS = "-";
    private String nilaiTugas = "-";
    private String nilaiIndeks = "-";
    private String nilaiAkhirAngka = "-";
    private String nama_instrumen_mayor;
    private String kode_instrumen_mayor;
    private String prodi_instrumen_mayor;
    private String instrumenMayor;
    private String batasLulus;
    private String statusKelulusan = "-";
    private boolean isiquisioner;
    private String info = "";

    public ReleaseKhs() {
    }

    public ReleaseKhs(KartuRencanaStudi kartuRencanaStudi) {
        isiquisioner = kartuRencanaStudi.isiquisioner();
        kodeMataKuliah = kartuRencanaStudi.getKelasPerkuliahan().getMasterMatakuliah().getKodeMatakuliah();
        namaMataKuliah = kartuRencanaStudi.getKelasPerkuliahan().getMasterMatakuliah().getNamaMatakuliah();
        sksMataKuliah = kartuRencanaStudi.getKelasPerkuliahan().getMasterMatakuliah().getSksMatakuliah();
        //sksPraktekLapangan = kartuRencanaStudi.getKelasPerkuliahan().getMasterMatakuliah().getSksPraktekLapangan();
        //sksPraktikum = kartuRencanaStudi.getKelasPerkuliahan().getMasterMatakuliah().getSksPraktikum();
        //sksSimulasi = kartuRencanaStudi.getKelasPerkuliahan().getMasterMatakuliah().getSksSimulasi();
        //sksTatapMuka = kartuRencanaStudi.getKelasPerkuliahan().getMasterMatakuliah().getSksTatapMuka();

        //if (isiquisioner) {
            nilai = kartuRencanaStudi.getNilaiMahasiswa().getNilaiAkhirHuruf();
            if (kartuRencanaStudi.getNilaiMahasiswa().getNilaiUAS() != null)
                nilaiUTS = kartuRencanaStudi.getNilaiMahasiswa().getNilaiUTS().toString();
            if (kartuRencanaStudi.getNilaiMahasiswa().getNilaiUAS() != null)
                nilaiUAS = kartuRencanaStudi.getNilaiMahasiswa().getNilaiUAS().toString();
            if (kartuRencanaStudi.getNilaiMahasiswa().getNilaiTugas() != null)
                nilaiTugas = kartuRencanaStudi.getNilaiMahasiswa().getNilaiTugas().toString();
            if (kartuRencanaStudi.getNilaiMahasiswa().getNilaiIndeks() != null)
                nilaiIndeks = kartuRencanaStudi.getNilaiMahasiswa().getNilaiIndeks().toString();
            if (kartuRencanaStudi.getNilaiMahasiswa().getNilaiAkhirAngka() != null)
                nilaiAkhirAngka = kartuRencanaStudi.getNilaiMahasiswa().getNilaiAkhirAngka().toString();
            if (kartuRencanaStudi.getNilaiMahasiswa().getKeteranganNilai() != null)
                statusKelulusan = kartuRencanaStudi.getNilaiMahasiswa().getKeteranganNilai().toString();
        //}else{
            info = "<font color=\"red\">Mohon isi quisioner mata kuliah ini terlebih dahulu untuk melihat nilai</font> "+kartuRencanaStudi.getNilaiMahasiswa().getId();
        //}
        if (kartuRencanaStudi.getKelasPerkuliahan().getInstrumentMayor() != null) {
            nama_instrumen_mayor = kartuRencanaStudi.getKelasPerkuliahan().getInstrumentMayor().getNamaInstrumentMayor();
            kode_instrumen_mayor = kartuRencanaStudi.getKelasPerkuliahan().getInstrumentMayor().getKodeInstrumentMayor();
            prodi_instrumen_mayor = kartuRencanaStudi.getKelasPerkuliahan().getInstrumentMayor().getProdiInstrumentMayor();
            instrumenMayor = "(" + getNama_instrumen_mayor() + " " + getKode_instrumen_mayor() + ")";
        } else {
            nama_instrumen_mayor = "";
            kode_instrumen_mayor = "";
            prodi_instrumen_mayor = "";
            instrumenMayor = "";
        }
    }

    public void setKodeMataKuliah(String kodeMataKuliah) {
        this.kodeMataKuliah = kodeMataKuliah;
    }

    public void setNamaMataKuliah(String namaMataKuliah) {
        this.namaMataKuliah = namaMataKuliah;
    }

    public void setSksMataKuliah(Integer sksMataKuliah) {
        this.sksMataKuliah = sksMataKuliah;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getKodeMataKuliah() {
        return kodeMataKuliah;
    }

    public String getNamaMataKuliah() {
        return namaMataKuliah;
    }

    public Integer getSksMataKuliah() {
        return sksMataKuliah;
    }

    public String getNilaiUTS() {
        return nilaiUTS;
    }

    public String getNilaiUAS() {
        return nilaiUAS;
    }

    public String getNilaiTugas() {
        return nilaiTugas;
    }

    public String getNilaiIndeks() {
        return nilaiIndeks;
    }

    public String getNilaiAkhirAngka() {
        return nilaiAkhirAngka;
    }

    public void setNilaiUTS(String nilaiUTS) {
        this.nilaiUTS = nilaiUTS;
    }

    public void setNilaiUAS(String nilaiUAS) {
        this.nilaiUAS = nilaiUAS;
    }

    public void setNilaiTugas(String nilaiTugas) {
        this.nilaiTugas = nilaiTugas;
    }

    public void setNilaiIndeks(String nilaiIndeks) {
        this.nilaiIndeks = nilaiIndeks;
    }

    public void setNilaiAkhirAngka(String nilaiAkhirAngka) {
        this.nilaiAkhirAngka = nilaiAkhirAngka;
    }

    public String getNama_instrumen_mayor() {
        return nama_instrumen_mayor;
    }

    public void setNama_instrumen_mayor(String nama_instrumen_mayor) {
        this.nama_instrumen_mayor = nama_instrumen_mayor;
    }

    public String getKode_instrumen_mayor() {
        return kode_instrumen_mayor;
    }

    public void setKode_instrumen_mayor(String kode_instrumen_mayor) {
        this.kode_instrumen_mayor = kode_instrumen_mayor;
    }

    public String getProdi_instrumen_mayor() {
        return prodi_instrumen_mayor;
    }

    public void setProdi_instrumen_mayor(String prodi_instrumen_mayor) {
        this.prodi_instrumen_mayor = prodi_instrumen_mayor;
    }

    public String getInstrumenMayor() {
        return instrumenMayor;
    }

    public void setInstrumenMayor(String instrumenMayor) {
        this.instrumenMayor = instrumenMayor;
    }

    public String getNilai() {
        return nilai;
    }

    public String getBatasLulus() {
        return batasLulus;
    }

    public void setBatasLulus(String batasLulus) {
        this.batasLulus = batasLulus;
    }

    public String getStatusKelulusan() {
        return statusKelulusan;
    }

    public void setStatusKelulusan(String statusKelulusan) {
        this.statusKelulusan = statusKelulusan;
    }

    public boolean isiquisioner() {
        return isiquisioner;
    }

    public void setIsiquisioner(boolean isiquisioner) {
        this.isiquisioner = isiquisioner;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer toAngka(String nilai) {
        if (nilai.equals("A")) {
            return 4;
        } else if (nilai.equals("B")) {
            return 3;
        } else if (nilai.equals("C")) {
            return 2;
        } else if (nilai.equals("D")) {
            return 1;
        } else {
            return 0;
        }
    }
}