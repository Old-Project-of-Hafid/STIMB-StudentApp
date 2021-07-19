package com.stimbpro.web;

import com.stimbpro.ReleaseClass.*;
import com.stimbpro.entity.stimb2.*;
import com.stimbpro.repository.*;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.ladda.LaddaAjaxButton;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.wicketstuff.annotation.mount.MountPath;
import org.wicketstuff.minis.behavior.mootip.MootipBehaviour;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Yusfia Hafid A on 1/3/2016.
 */

@MountPath("quisioner")
public class QuisionerPage extends PubLayout {
    private static final Logger log = LoggerFactory.getLogger(QuisionerPage.class);
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
    QuisionerRepository quisionerRepository;
    @Inject
    QuisionerLogRepository quisionerLogRepository;
    @Inject
    KomentarSaranRepository komentarSaranRepository;

    private MasterMahasiswa masterMahasiswa;
    private KartuRencanaStudi kartuRencanaStudi;
    List<ReleaseKrs> releaseKrses = new ArrayList<ReleaseKrs>();
    List<ReleaseKhs> releaseKhses = new ArrayList<ReleaseKhs>();
    List<ReleaseListAktifitasPerkuliahan> releaseListAktifitasPerkuliahans = new ArrayList<>();
    List<ReleaseKalenderAkademik> releaseKalenderAkademiks = new ArrayList<>();
    List<ReleaseQuisionerLog> releaseQuisionerLogs = new ArrayList<>();

    ReleaseKrs selected;

    public QuisionerPage(PageParameters parameters) {
        super(parameters);
        final Form<Void> form = new Form<Void>("form");
        final TextField<String> npmSearch = new TextField<String>("npmsearch", Model.of(""));
        form.add(npmSearch);

        //------------TABEL QUISIONER---------------------------------------------------
        ListDataProvider<ReleaseQuisionerLog> releaseQuisionerLogListDataProvider = new ListDataProvider<ReleaseQuisionerLog>() {
            @Override
            protected List<ReleaseQuisionerLog> getData() {
                return releaseQuisionerLogs;
            }
        };

        DataView<ReleaseQuisionerLog> dataViewQuisioner = new DataView<ReleaseQuisionerLog>("rowsquisioner", releaseQuisionerLogListDataProvider) {
            @Override
            protected void populateItem(Item<ReleaseQuisionerLog> item) {
                ReleaseQuisionerLog rks = item.getModelObject();
                //item.add(new MootipBehaviour("Keterangan",rks.getKeterangan()));
                item.add(new Label("dataRowQuisioner", "(" + rks.getKodematkul() + "-"+rks.getNamamatakuliah()+") " + rks.getQuestion()));
                RadioGroup<Integer> adminRadioGroup = new RadioGroup<>("radiobutton", new Model<Integer>(null));
                log.info("{} ", rks.getQuestion());
                Radio radio1 = new Radio("radio1", new Model<>(1));
                radio1.add(new AjaxEventBehavior("click") {
                    @Override
                    protected void onEvent(final AjaxRequestTarget target) {
                        ReleaseQuisionerLog rq = item.getModelObject();
                        rq.setSelectedChoice(1);
                        setAnswerValue(rq);
                    }
                });
                Radio radio2 = new Radio("radio2", new Model<>(2));
                radio2.add(new AjaxEventBehavior("click") {
                    @Override
                    protected void onEvent(final AjaxRequestTarget target) {
                        ReleaseQuisionerLog rq = item.getModelObject();
                        rq.setSelectedChoice(2);
                        setAnswerValue(rq);
                    }
                });
                Radio radio3 = new Radio("radio3", new Model<>(3));
                radio3.add(new AjaxEventBehavior("click") {
                    @Override
                    protected void onEvent(final AjaxRequestTarget target) {
                        ReleaseQuisionerLog rq = item.getModelObject();
                        rq.setSelectedChoice(3);
                        setAnswerValue(rq);
                    }
                });
                Radio radio4 = new Radio("radio4", new Model<>(4));
                radio4.add(new AjaxEventBehavior("click") {
                    @Override
                    protected void onEvent(final AjaxRequestTarget target) {
                        ReleaseQuisionerLog rq = item.getModelObject();
                        rq.setSelectedChoice(4);
                        setAnswerValue(rq);
                    }
                });
                Radio radio5 = new Radio("radio5", new Model<>(5));
                radio5.add(new AjaxEventBehavior("click") {
                    @Override
                    protected void onEvent(final AjaxRequestTarget target) {
                        ReleaseQuisionerLog rq = item.getModelObject();
                        rq.setSelectedChoice(5);
                        setAnswerValue(rq);
                    }
                });
                adminRadioGroup.add(radio1);//.setOutputMarkupId(true));
                adminRadioGroup.add(radio2);//.setOutputMarkupId(true));
                adminRadioGroup.add(radio3);//.setOutputMarkupId(true));
                adminRadioGroup.add(radio4);//.setOutputMarkupId(true));
                adminRadioGroup.add(radio5);//.setOutputMarkupId(true));
                //adminRadioGroup.setOutputMarkupId(true);
                item.add(adminRadioGroup);
            }
        };
        dataViewQuisioner.setOutputMarkupId(true);
        final WebMarkupContainer wmcQuisioner = new WebMarkupContainer("containeridquisioner");
        wmcQuisioner.add(dataViewQuisioner);
        wmcQuisioner.setOutputMarkupId(true);
        form.add(wmcQuisioner);

        //------------DROP DOWN KRS-----------------------------------------------------
        PropertyModel<ReleaseKrs> dropdownmod = new PropertyModel<ReleaseKrs>(this, "selected");
        DropDownChoice<ReleaseKrs> listOperator = new DropDownChoice<ReleaseKrs>("sites", dropdownmod, releaseKrses);
        listOperator.setNullValid(false);
        listOperator.add(new AjaxFormComponentUpdatingBehavior("change") {
            @Override
            protected void onUpdate(AjaxRequestTarget target) {
                createQuestionaire();
                target.add(wmcQuisioner);
            }
        });
        listOperator.setOutputMarkupId(true);
        form.add(listOperator);
        //------------------------------------------------------------------------------

        //------------------------------------------------------------------------------
        form.add(new LaddaAjaxButton("klik", new Model<String>("Cari"), Buttons.Type.Default) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                masterMahasiswa = studentService.findByNPM(npmSearch.getModelObject());
                log.info("{}", masterMahasiswa);
                if (masterMahasiswa != null) {
                    releaseKrses.clear();
                    List<KartuRencanaStudi> krs = kartuRencanaStudiRepository.findByMhsIdandThAjar(masterMahasiswa.getId(), true);
                    for (int i = 0; i < krs.size(); i++) {
                        ReleaseKrs releaseKrs = new ReleaseKrs(krs.get(i));
                        if (i == 0) {
                            selected = releaseKrs;
                            createQuestionaire();
                        }
                        releaseKrses.add(releaseKrs);
                    }
                } else {
                    releaseKrses.clear();
                    removeQuisionaireList();
                }
                target.add(listOperator, wmcQuisioner);
            }
        });

        TextArea<String> question = new TextArea<String>("komentar", Model.of(""));
        form.add(question);
        LaddaAjaxButton submitButton = new LaddaAjaxButton("klikSubmit", new Model<String>("Submit"), Buttons.Type.Default) {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                if (releaseQuisionerLogs.size()>0) {
                    submitQuisionerLog(question.getModelObject());
                }else{
                    target.appendJavaScript("alert('Data Quisioner Kosong!');");
                }
                if (masterMahasiswa != null) {
                    releaseKrses.clear();
                    List<KartuRencanaStudi> krs = kartuRencanaStudiRepository.findByMhsIdandThAjar(masterMahasiswa.getId(), true);
                    for (int i = 0; i < krs.size(); i++) {
                        ReleaseKrs releaseKrs = new ReleaseKrs(krs.get(i));
                        if (i == 0) {
                            selected = releaseKrs;
                            createQuestionaire();
                        }
                        releaseKrses.add(releaseKrs);
                        //selected = releaseKrs;
                    }
                } else {
                    releaseKrses.clear();
                    removeQuisionaireList();
                }
                target.add(listOperator, wmcQuisioner);
            }
        };
        form.add(submitButton);
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

    protected void setAnswerValue(ReleaseQuisionerLog answerValue) {
        for (int i = 0; i < releaseQuisionerLogs.size(); i++) {
            if (releaseQuisionerLogs.get(i).equals(answerValue)) {
                log.info("List {} {} Item {} {}", releaseQuisionerLogs.get(i).getKrsId(), releaseQuisionerLogs.get(i).getQuisionerId(), answerValue.getKrsId(), answerValue.getQuisionerId());
                releaseQuisionerLogs.get(i).setSelectedChoice(answerValue.getSelectedChoice());
                break;
            }
        }
    }

    protected void submitQuisionerLog(String komentar) {
        for (int i = 0; i < releaseQuisionerLogs.size(); i++) {
            quisionerLogRepository.save(releaseQuisionerLogs.get(i).getQuisionerLog());
            KartuRencanaStudi krs = kartuRencanaStudiRepository.findOne(releaseQuisionerLogs.get(i).getKrsId());
            krs.setIsiquisioner(true);
            kartuRencanaStudiRepository.save(krs);
            if (i == releaseQuisionerLogs.size() - 1) {
                KomentarSaran komentarSaran = new KomentarSaran();
                komentarSaran.setContent(komentar);
                komentarSaran.setKartuRencanaStudi(krs);
                komentarSaranRepository.save(komentarSaran);
            }
        }

    }

    private void createQuestionaire() {
        List<Quisioner> quisionerList = quisionerRepository.findActive(true);
        KartuRencanaStudi krs = kartuRencanaStudiRepository.findOneBasedOnQuestionaire(selected.getIdKrs(), false);
        releaseQuisionerLogs.clear();
        for (int i = 0; i < quisionerList.size(); i++) {
            ReleaseQuisionerLog releaseQuisionerLog = new ReleaseQuisionerLog(krs, quisionerList.get(i));
            releaseQuisionerLogs.add(releaseQuisionerLog);
        }
    }

    private void removeQuisionaireList(){
        releaseQuisionerLogs.clear();
    }
}
