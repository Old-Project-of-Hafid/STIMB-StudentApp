package com.stimbpro.web;


import com.stimbpro.OtherService.ImageService;
import com.stimbpro.ReleaseClass.*;
import com.stimbpro.entity.stimb2.*;
import com.stimbpro.repository.*;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.*;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.minis.behavior.mootip.MootipBehaviour;
import org.wicketstuff.minis.behavior.mootip.MootipSettings;

import javax.inject.Inject;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yusfia Hafid A on 12/10/2015.
 */

@MountPath("mahasiswa")
public class StudentsPage extends PubLayout {
    private static final Logger log = LoggerFactory.getLogger(StudentsPage.class);
    @Inject
    public Environment env;
    @Inject
    StudentRepository studentService;
    @Inject
    KartuRencanaStudiRepository kartuRencanaStudiRepository;
    @Inject
    ReffTahunAjaranRepository reffTahunAjaranRepository;
    @Inject
    ListKalenderAkademikRepository listKalenderAkademikRepository;
    @Inject
    ListDosenKelasRepository listDosenKelasRepository;
    @Inject
    ListJadwalPerkuliahanRepository listJadwalPerkuliahanRepository;
    @Inject
    KelasPerkuliahanRepository kelasPerkuliahanRepository;

    private MasterMahasiswa masterMahasiswa;
    private KartuRencanaStudi kartuRencanaStudi;
    List<ReleaseKrs> releaseKrses = new ArrayList<ReleaseKrs>();
    List<ReleaseKhs> releaseKhses = new ArrayList<ReleaseKhs>();
    List<ReleaseListAktifitasPerkuliahan> releaseListAktifitasPerkuliahans = new ArrayList<>();
    List<ReleaseKalenderAkademik> releaseKalenderAkademiks = new ArrayList<>();
    List<ReleaseKelasPerkuliahan> releaseKelasPerkuliahans = new ArrayList<>();
    ReleaseTahunAjaran selected;

    public StudentsPage(PageParameters parameters) {
        super(parameters);
        final Form<Void> form = new Form<Void>("form");
        final TextField<String> npmSearch = new TextField<String>("npmsearch", Model.of(""));
        form.add(npmSearch);

        //keterangan atas------
        //npm
        Label markupNpm = new Label("npm", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                if (masterMahasiswa != null) {
                    return "<b>" + masterMahasiswa.getNpm() + "</b>";
                } else {
                    return "";
                }
            }
        });
        markupNpm.setEscapeModelStrings(false);
        markupNpm.setOutputMarkupId(true);
        form.add(markupNpm);
        //nama
        Label markupNama = new Label("name", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                if (masterMahasiswa != null) {
                    return "<b>" + masterMahasiswa.getNama() + "</b>";
                } else {
                    return "";
                }
            }
        });
        markupNama.setEscapeModelStrings(false);
        markupNama.setOutputMarkupId(true);
        form.add(markupNama);
        //Mayor
        Label markupAlamat = new Label("alamat", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                if (masterMahasiswa != null) {
                    return "<b>" + masterMahasiswa.getReffInstrumentMayor().getNamaInstrumentMayor() + "</b>";
                } else {
                    return "";
                }
            }
        });
        markupAlamat.setEscapeModelStrings(false);
        markupAlamat.setOutputMarkupId(true);
        form.add(markupAlamat);
        //Prodi
        Label markupEmail = new Label("email", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                if (masterMahasiswa != null) {
                    return "<b>" + masterMahasiswa.getReffProgramStudi().getNamaProgramStudi() + "</b>";
                } else {
                    return "";
                }
            }
        });
        markupEmail.setEscapeModelStrings(false);
        markupEmail.setOutputMarkupId(true);
        form.add(markupEmail);
        //-------------------TABEL JADWAL UMUM--------------------------------------------------------------------------
        ListDataProvider<ReleaseKelasPerkuliahan> releaseKelasPerkuliahanListDataProvider = new ListDataProvider<ReleaseKelasPerkuliahan>() {
            @Override
            protected List<ReleaseKelasPerkuliahan> getData() {
                return releaseKelasPerkuliahans;
            }
        };
        DataView<ReleaseKelasPerkuliahan> dataViewKelasPerkuliahan = new DataView<ReleaseKelasPerkuliahan>("rowsjadwal", releaseKelasPerkuliahanListDataProvider) {
            @Override
            protected void populateItem(Item<ReleaseKelasPerkuliahan> item) {
                ReleaseKelasPerkuliahan rks = item.getModelObject();
                RepeatingView repeatingView = new RepeatingView("dataRowjadwal");
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getKode_matakuliah()));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getNama_matakuliah()+ " " + rks.getInstrumen()));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getJadwal_matakuliah()).setEscapeModelStrings(false));
                item.add(repeatingView);
            }
        };
        dataViewKelasPerkuliahan.setOutputMarkupId(true);
        AjaxPagingNavigator paginationJadwal = new AjaxPagingNavigator("pagingNavigatorjadwal", dataViewKelasPerkuliahan);
        paginationJadwal.setOutputMarkupId(true);
        final WebMarkupContainer wmcJadwal = new WebMarkupContainer("containeridjadwal");
        wmcJadwal.add(paginationJadwal);
        wmcJadwal.add(dataViewKelasPerkuliahan);
        wmcJadwal.setOutputMarkupId(true);
        form.add(wmcJadwal);

        //-------------------TABEL ABSENSI------------------------------------------------------------------------------
        ListDataProvider<ReleaseListAktifitasPerkuliahan> releaseListAktifitasPerkuliahanListDataProvider = new ListDataProvider<ReleaseListAktifitasPerkuliahan>() {
            @Override
            protected List<ReleaseListAktifitasPerkuliahan> getData() {
                return releaseListAktifitasPerkuliahans;
            }
        };
        DataView<ReleaseListAktifitasPerkuliahan> dataViewAbsen = new DataView<ReleaseListAktifitasPerkuliahan>("rowsabsen", releaseListAktifitasPerkuliahanListDataProvider, 10) {
            @Override
            protected void populateItem(Item<ReleaseListAktifitasPerkuliahan> item) {
                ReleaseListAktifitasPerkuliahan rks = item.getModelObject();
                RepeatingView repeatingView = new RepeatingView("dataRowabsen");
                repeatingView.add(new Label(repeatingView.newChildId(), "(" + rks.getKode_mata_kuliah() + ") " + rks.getNama_matakuliah()));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensi1())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "Pertemuan 1 : <br>" + rks.hadirConverter(rks.isPresensi1()) + "<br>" + rks.getPresensi1_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensi2())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "Pertemuan 2 : <br>" + rks.hadirConverter(rks.isPresensi2()) + "<br>" + rks.getPresensi2_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensi3())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "Pertemuan 3 : <br>" + rks.hadirConverter(rks.isPresensi3()) + "<br>" + rks.getPresensi3_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensi4())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "Pertemuan 4 : <br>" + rks.hadirConverter(rks.isPresensi4()) + "<br>" + rks.getPresensi4_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensi5())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "Pertemuan 5 : <br>" + rks.hadirConverter(rks.isPresensi5()) + "<br>" + rks.getPresensi5_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensi6())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "Pertemuan 6 : <br>" + rks.hadirConverter(rks.isPresensi6()) + "<br>" + rks.getPresensi6_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensi7())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "Pertemuan 7 : <br>" + rks.hadirConverter(rks.isPresensi7()) + "<br>" + rks.getPresensi7_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensiUTS())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "UTS : <br>" + rks.hadirConverter(rks.isPresensiUTS()) + "<br>" + rks.getPresensiUTS_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensi8())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "Pertemuan 8 : <br>" + rks.hadirConverter(rks.isPresensi8()) + "<br>" + rks.getPresensi8_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensi9())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "Pertemuan 9 : <br>" + rks.hadirConverter(rks.isPresensi9()) + "<br>" + rks.getPresensi9_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensi10())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "Pertemuan 10 : <br>" + rks.hadirConverter(rks.isPresensi10()) + "<br>" + rks.getPresensi10_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensi11())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "Pertemuan 11 : <br>" + rks.hadirConverter(rks.isPresensi11()) + "<br>" + rks.getPresensi11_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensi12())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "Pertemuan 12 : <br>" + rks.hadirConverter(rks.isPresensi12()) + "<br>" + rks.getPresensi12_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensi13())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "Pertemuan 13 : <br>" + rks.hadirConverter(rks.isPresensi13()) + "<br>" + rks.getPresensi13_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensi14())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "Pertemuan 14 : <br>" + rks.hadirConverter(rks.isPresensi14()) + "<br>" + rks.getPresensi14_tgl())));
                repeatingView.add(new Label(repeatingView.newChildId(), ImageService.getHtmlIcon(rks.isPresensiUAS())).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getNama_matakuliah(), "UAS : <br>" + rks.hadirConverter(rks.isPresensiUAS()) + "<br>" + rks.getPresensiUAS_tgl())));
                DecimalFormat df = new DecimalFormat(".##");
                repeatingView.add(new Label(repeatingView.newChildId(), "<div style=\"text-align: center\">" + rks.getTotalKehadiran() + "</div>").setEscapeModelStrings(false).add(new MootipBehaviour("Persentase Kehadiran", Double.valueOf(df.format(((double) rks.getTotalKehadiran() / (double) 14) * 100)) + "%")));
                item.add(repeatingView);
            }
        };
        dataViewAbsen.setItemsPerPage(10);
        dataViewAbsen.setOutputMarkupId(true);
        AjaxPagingNavigator paginationAbsen = new AjaxPagingNavigator("pagingNavigatorabsen", dataViewAbsen);
        paginationAbsen.setOutputMarkupId(true);
        final WebMarkupContainer wmcAbsen = new WebMarkupContainer("containeridabsen");
        wmcAbsen.add(paginationAbsen);
        wmcAbsen.add(dataViewAbsen);
        wmcAbsen.setOutputMarkupId(true);
        form.add(wmcAbsen);
        //-------------------TABEL KALENDER AKADEMIK---------------------------------------------
        ListDataProvider<ReleaseKalenderAkademik> releaseTahunAjaranDataProvider = new ListDataProvider<ReleaseKalenderAkademik>() {
            @Override
            protected List<ReleaseKalenderAkademik> getData() {
                return releaseKalenderAkademiks;
            }
        };
        DataView<ReleaseKalenderAkademik> dataViewTahunAjaran = new DataView<ReleaseKalenderAkademik>("rowskalender", releaseTahunAjaranDataProvider, 10) {
            @Override
            protected void populateItem(Item<ReleaseKalenderAkademik> item) {
                ReleaseKalenderAkademik rks = item.getModelObject();
                RepeatingView repeatingView = new RepeatingView("dataRowkalender");
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getTahunajar()));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getSemester()));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getKegiatan()));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getTanggal()));
                item.add(repeatingView);
            }
        };
        dataViewTahunAjaran.setItemsPerPage(10);
        dataViewTahunAjaran.setOutputMarkupId(true);
        AjaxPagingNavigator paginationKalender = new AjaxPagingNavigator("pagingNavigatorkalender", dataViewTahunAjaran);
        paginationKalender.setOutputMarkupId(true);
        final WebMarkupContainer wmcKalender = new WebMarkupContainer("containeridkalender");
        wmcKalender.add(paginationKalender);
        wmcKalender.add(dataViewTahunAjaran);
        wmcKalender.setOutputMarkupId(true);
        form.add(wmcKalender);
        //-------------------TABEL KRS-----------------------------------------------------------
        /**/
        ListDataProvider<ReleaseKrs> releaseKrsDataProvider = new ListDataProvider<ReleaseKrs>() {
            @Override
            protected List<ReleaseKrs> getData() {
                return releaseKrses;
            }
        };
        DataView<ReleaseKrs> dataView = new DataView<ReleaseKrs>("rowsKrs", releaseKrsDataProvider, 10) {
            @Override
            protected void populateItem(Item<ReleaseKrs> item) {
                ReleaseKrs rks = item.getModelObject();
                RepeatingView repeatingView = new RepeatingView("dataRowKrs");
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getKodeMataKuliah()));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getNamaMataKuliah() + " " + rks.getInstrumenMayor()));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getSksMataKuliah()));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getTahunAjaran()));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getSemester()));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getNamaDosen()));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getJadwalkuliah()).setEscapeModelStrings(false));
                item.add(repeatingView);
            }
        };
        dataView.setItemsPerPage(10);
        dataView.setOutputMarkupId(true);
        AjaxPagingNavigator pagination = new AjaxPagingNavigator("pagingNavigator", dataView);
        pagination.setOutputMarkupId(true);
        final WebMarkupContainer wmc = new WebMarkupContainer("containerid");
        wmc.add(dataView);
        wmc.add(pagination);
        wmc.setOutputMarkupId(true);
        form.add(wmc);

        //----------------TABEL KHS----------------------------------------------------------------
        ListDataProvider<ReleaseKhs> releaseKhsDataProvider = new ListDataProvider<ReleaseKhs>() {
            @Override
            protected List<ReleaseKhs> getData() {
                return releaseKhses;
            }
        };
        DataView<ReleaseKhs> dataViewKhs = new DataView<ReleaseKhs>("rowskhs", releaseKhsDataProvider, 10) {
            @Override
            protected void populateItem(Item<ReleaseKhs> item) {
                ReleaseKhs rks = item.getModelObject();
                RepeatingView repeatingView = new RepeatingView("dataRowkhs");
                MootipSettings mooSettings = new MootipSettings();
                mooSettings.setEvalAlways(true);
                mooSettings.setMaxTitleChars(100);
                String info = "Nilai UTS " + rks.getNilaiUTS()
                        + "<br> Nilai UAS " + rks.getNilaiUAS()
                        + "<br> Nilai Tugas " + rks.getNilaiTugas()
                        + "<br> Nilai Indeks " + rks.getNilaiIndeks()
                        + "<br> Nilai Akhir Angka " + rks.getNilaiAkhirAngka();
                //MootipBehaviour tooltip = new MootipBehaviour(rks.getKodeMataKuliah() + " " + rks.getNamaMataKuliah(), info);
                //tooltip.setMootipSettings(mooSettings);
                //item.add(tooltip);
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getKodeMataKuliah()).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getKodeMataKuliah() + " " + rks.getNamaMataKuliah(), info)));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getNamaMataKuliah() + " " + rks.getInstrumenMayor()).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getKodeMataKuliah() + " " + rks.getNamaMataKuliah(), info)));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getSksMataKuliah()).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getKodeMataKuliah() + " " + rks.getNamaMataKuliah(), info)));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getNilai()).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getKodeMataKuliah() + " " + rks.getNamaMataKuliah(), info)));
                repeatingView.add(new Label(repeatingView.newChildId(), rks.getStatusKelulusan()).setEscapeModelStrings(false).add(new MootipBehaviour(rks.getKodeMataKuliah() + " " + rks.getNamaMataKuliah(), info)));
                item.add(repeatingView);
            }
        };
        dataViewKhs.setItemsPerPage(10);
        dataViewKhs.setOutputMarkupId(true);
        AjaxPagingNavigator paginationKhs = new AjaxPagingNavigator("pagingNavigatorkhs", dataViewKhs);
        paginationKhs.setOutputMarkupId(true);
        final WebMarkupContainer wmcKhs = new WebMarkupContainer("containeridkhs");
        wmcKhs.add(dataViewKhs);
        wmcKhs.add(paginationKhs);
        wmcKhs.setOutputMarkupId(true);
        form.add(wmcKhs);
        Label markupIPK = new Label("ipk", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                if (masterMahasiswa != null) {
                    return "<b>" + getIPK() + "</b>";
                } else {
                    return "";
                }
            }
        });
        markupIPK.setEscapeModelStrings(false);
        markupIPK.setOutputMarkupId(true);
        form.add(markupIPK);

        Label ipkorips = new Label("ipkorips", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                if (masterMahasiswa != null) {
                    if (selected.getTahunAjaran().equals("Semua")) {
                        return "IPK";
                    } else {
                        return "IP Semester";
                    }
                } else {
                    return "IPK";
                }
            }
        });
        ipkorips.setEscapeModelStrings(false);
        ipkorips.setOutputMarkupId(true);
        form.add(ipkorips);

        Label markupSKS = new Label("totsks", new AbstractReadOnlyModel<String>() {
            @Override
            public String getObject() {
                if (masterMahasiswa != null) {
                    return "<b>" + totalSKS() + "</b>";
                } else {
                    return "";
                }
            }
        });
        markupSKS.setEscapeModelStrings(false);
        markupSKS.setOutputMarkupId(true);
        form.add(markupSKS);
        //--------------------------------------------------------------------------------------------------------------
        //LIST TAHUN AJARAN------------------------------------------------------------------------------------------------------
        List<ReffTahunAjaran> listReffTahunAjaran = reffTahunAjaranRepository.findAllList();
        List<ReleaseTahunAjaran> option = new ArrayList<>();
        for (int i = 0; i < listReffTahunAjaran.size(); i++) {
            selected = new ReleaseTahunAjaran();
            selected.setTahunAjaran(listReffTahunAjaran.get(i).getNamaTahunAjaran());
            selected.setValue(listReffTahunAjaran.get(i).getNamaTahunAjaran());
            selected.setSemester(listReffTahunAjaran.get(i).getPeriode());
            option.add(selected);
        }
        selected = new ReleaseTahunAjaran();
        selected.setTahunAjaran("Semua");
        selected.setValue("");
        selected.setSemester("");
        option.add(selected);
        PropertyModel<ReleaseTahunAjaran> dropdownmod = new PropertyModel<ReleaseTahunAjaran>(this, "selected");
        DropDownChoice<ReleaseTahunAjaran> listOperator = new DropDownChoice<ReleaseTahunAjaran>("sites", dropdownmod, option);
        listOperator.add(new AjaxFormComponentUpdatingBehavior("change") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                masterMahasiswa = studentService.findByNPM(npmSearch.getModelObject());
                releaseKalenderAkademiks.clear();
                releaseKelasPerkuliahans.clear();
                List<ListKalenderAkademik> listKalenderAkademiks = listKalenderAkademikRepository.findReferedKalenderAkademikList(selected.getValue(), selected.getSemester());
                for (int i = 0; i < listKalenderAkademiks.size(); i++) {
                    ReleaseKalenderAkademik releaseKalenderAkademik = new ReleaseKalenderAkademik(listKalenderAkademiks.get(i));
                    releaseKalenderAkademiks.add(releaseKalenderAkademik);
                }
                List<ListJadwalPerkuliahan> kelasPerkuliahans = listJadwalPerkuliahanRepository.findByThAjar(selected.getValue(), selected.getSemester());
                for (int i = 0; i < kelasPerkuliahans.size(); i++) {
                    ReleaseKelasPerkuliahan rf = new ReleaseKelasPerkuliahan(kelasPerkuliahans.get(i));
                    releaseKelasPerkuliahans.add(rf);
                }
                if (masterMahasiswa != null) {
                    releaseKhses.clear();
                    releaseKrses.clear();
                    releaseListAktifitasPerkuliahans.clear();
                    List<KartuRencanaStudi> krs = kartuRencanaStudiRepository.findByMhsId(masterMahasiswa.getId(), selected.getValue(), selected.getSemester());
                    for (int i = 0; i < krs.size(); i++) {
                        ListDosenKelas ldk = listDosenKelasRepository.findByKelasId(krs.get(i).getKelasPerkuliahan().getId());
                        List<ListJadwalPerkuliahan> listJadwalPerkuliahans = listJadwalPerkuliahanRepository.findByKelasId(krs.get(i).getKelasPerkuliahan().getId());
                        ReleaseKrs releaseKrs = new ReleaseKrs(krs.get(i), ldk, listJadwalPerkuliahans);
                        ReleaseKhs releaseKhs = new ReleaseKhs(krs.get(i));
                        ReleaseListAktifitasPerkuliahan releaseListAktifitasPerkuliahan = new ReleaseListAktifitasPerkuliahan(krs.get(i));
                        releaseKrses.add(releaseKrs);
                        releaseKhses.add(releaseKhs);
                        releaseListAktifitasPerkuliahans.add(releaseListAktifitasPerkuliahan);
                    }
                } else {
                    releaseKhses.clear();
                    releaseKrses.clear();
                    releaseListAktifitasPerkuliahans.clear();
                }
                //targetKRS
                target.add(pagination, wmc);
                //targetKHS
                target.add(markupIPK, wmcKhs, markupSKS, ipkorips);
                //targetKalender
                target.add(wmcKalender);
                //targetAbsen
                target.add(wmcAbsen);
                //targetJadwal
                target.add(wmcJadwal);
            }
        });
        form.add(listOperator);
        //----------------------------------------------------------------------------------------------------------------------
        //add( new AjaxSelfUpdatingTimerBehavior( Duration.seconds(5) ) );
        form.add(new LaddaAjaxButton("klik", new Model<String>("Cari"), Buttons.Type.Default) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                masterMahasiswa = studentService.findByNPM(npmSearch.getModelObject());
                releaseKalenderAkademiks.clear();
                List<ListKalenderAkademik> listKalenderAkademiks = listKalenderAkademikRepository.findReferedKalenderAkademikList(selected.getValue(), selected.getSemester());
                for (int i = 0; i < listKalenderAkademiks.size(); i++) {
                    ReleaseKalenderAkademik releaseKalenderAkademik = new ReleaseKalenderAkademik(listKalenderAkademiks.get(i));
                    releaseKalenderAkademiks.add(releaseKalenderAkademik);
                }
                if (masterMahasiswa != null) {
                    releaseKhses.clear();
                    releaseKrses.clear();
                    releaseListAktifitasPerkuliahans.clear();
                    releaseKelasPerkuliahans.clear();
                    List<KartuRencanaStudi> krs = kartuRencanaStudiRepository.findByMhsId(masterMahasiswa.getId(), selected.getValue(), selected.getSemester());
                    for (int i = 0; i < krs.size(); i++) {
                        ListDosenKelas ldk = listDosenKelasRepository.findByKelasId(krs.get(i).getKelasPerkuliahan().getId());
                        List<ListJadwalPerkuliahan> listJadwalPerkuliahans = listJadwalPerkuliahanRepository.findByKelasId(krs.get(i).getKelasPerkuliahan().getId());
                        ReleaseKrs releaseKrs = new ReleaseKrs(krs.get(i), ldk, listJadwalPerkuliahans);
                        ReleaseKhs releaseKhs = new ReleaseKhs(krs.get(i));
                        ReleaseListAktifitasPerkuliahan releaseListAktifitasPerkuliahan = new ReleaseListAktifitasPerkuliahan(krs.get(i));
                        releaseKrses.add(releaseKrs);
                        releaseKhses.add(releaseKhs);
                        releaseListAktifitasPerkuliahans.add(releaseListAktifitasPerkuliahan);
                    }
                    List<ListJadwalPerkuliahan> kelasPerkuliahans = listJadwalPerkuliahanRepository.findByThAjar(selected.getValue(), selected.getSemester());
                    for (int i = 0; i < kelasPerkuliahans.size(); i++) {
                        ReleaseKelasPerkuliahan rf = new ReleaseKelasPerkuliahan(kelasPerkuliahans.get(i));
                        releaseKelasPerkuliahans.add(rf);
                    }
                    log.info("KRS PROVIDER {} {} {}", releaseKrsDataProvider.size(), releaseKhsDataProvider.size(), selected.getValue());
                } else {
                    releaseKhses.clear();
                    releaseKrses.clear();
                    releaseListAktifitasPerkuliahans.clear();
                    releaseKelasPerkuliahans.clear();
                }

                target.add(markupNpm, markupNama, markupEmail, markupAlamat);
                //targetKRS
                target.add(pagination, wmc);
                //targetKHS
                target.add(markupIPK, wmcKhs, markupSKS, ipkorips);
                //targetKalender
                target.add(wmcKalender);
                //targetAbsen
                target.add(wmcAbsen);
                //targetJadwal
                target.add(wmcJadwal);
            }
        });

        form.setOutputMarkupId(true);
        setOutputMarkupId(true);
        add(form);
    }

    @Override
    public IModel<String> getTitleModel() {
        return new Model<>("Sekolah Tinggi Musik Bandung");
    }

    @Override
    public IModel<String> getMetaDescriptionModel() {
        return new Model<>("Sekolah Tinggi Musik Bandung");
    }

    public String getIPK() {
        int sumSKS = 0;
        int totalVal = 0;
        int temp = 0;
        for (int i = 0; i < releaseKhses.size(); i++) {
            String val = releaseKhses.get(i).getNilai();
            int currentsks = releaseKhses.get(i).getSksMataKuliah();
            if (val != null) {
                if (val.equals("A")) {
                    temp = 4;
                } else if (val.equals("B")) {
                    temp = 3;
                } else if (val.equals("C")) {
                    temp = 2;
                } else if (val.equals("D")) {
                    temp = 1;
                } else if (val.equals("E")) {
                    temp = 0;
                }
                totalVal = totalVal + (currentsks * temp);
                sumSKS = sumSKS + currentsks;
            }
        }
        DecimalFormat df = new DecimalFormat(".##");
        String last_format;
        if (sumSKS > 0) {
            last_format = "" + totalVal + "/" + sumSKS + " = " + Double.valueOf(df.format((double) ((double) totalVal / (double) sumSKS)));
        } else {
            last_format = "0";
        }
        return last_format;
    }

    public String totalSKS() {
        int sumSKS = 0;
        for (int i = 0; i < releaseKhses.size(); i++) {
            int currentsks = releaseKhses.get(i).getSksMataKuliah();
            sumSKS = sumSKS + currentsks;
        }
        DecimalFormat df = new DecimalFormat(".##");
        return "" + sumSKS;
    }
}
