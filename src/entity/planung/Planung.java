/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.planung;

import entity.Bewohner;
import entity.Users;
import entity.info.BWInfoKat;
import entity.vorgang.SYSPLAN2VORGANG;
import entity.vorgang.VorgangElement;
import op.OPDE;
import op.tools.SYSConst;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @author tloehr
 */
@Entity
@Table(name = "Planung")
@NamedQueries({
        @NamedQuery(name = "Planung.findAll", query = "SELECT p FROM Planung p"),
        @NamedQuery(name = "Planung.findByPlanID", query = "SELECT p FROM Planung p WHERE p.planID = :planID"),
        @NamedQuery(name = "Planung.findByVorgang", query = " "
                + " SELECT p, av.pdca FROM Planung p "
                + " JOIN p.attachedVorgaenge av"
                + " JOIN av.vorgang v"
                + " WHERE v = :vorgang "),
        @NamedQuery(name = "Planung.findByStichwort", query = "SELECT p FROM Planung p WHERE p.stichwort = :stichwort"),
        @NamedQuery(name = "Planung.findByVon", query = "SELECT p FROM Planung p WHERE p.von = :von"),
        @NamedQuery(name = "Planung.findByBis", query = "SELECT p FROM Planung p WHERE p.bis = :bis"),
        @NamedQuery(name = "Planung.findByPlanKennung", query = "SELECT p FROM Planung p WHERE p.planKennung = :planKennung"),
        @NamedQuery(name = "Planung.findByNKontrolle", query = "SELECT p FROM Planung p WHERE p.nKontrolle = :nKontrolle")})
public class Planung implements Serializable, VorgangElement {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PlanID")
    private Long planID;
    @Basic(optional = false)
    @Column(name = "Stichwort")
    private String stichwort;
    @Lob
    @Column(name = "Situation")
    private String situation;
    @Lob
    @Column(name = "Ziel")
    private String ziel;
    //    @Basic(optional = false)
//    @Column(name = "BWIKID")
//    private long bwikid;
    @Basic(optional = false)
    @Column(name = "Von")
    @Temporal(TemporalType.TIMESTAMP)
    private Date von;
    @Basic(optional = false)
    @Column(name = "Bis")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bis;
    @Basic(optional = false)
    @Column(name = "PlanKennung")
    private long planKennung;
    @Basic(optional = false)
    @Column(name = "NKontrolle")
    @Temporal(TemporalType.DATE)
    private Date nKontrolle;
    @Version
    @Column(name = "version")
    private Long version;

    // ==
    // N:1 Relationen
    // ==
    @JoinColumn(name = "AnUKennung", referencedColumnName = "UKennung")
    @ManyToOne
    private Users angesetztDurch;
    @JoinColumn(name = "AbUKennung", referencedColumnName = "UKennung")
    @ManyToOne
    private Users abgesetztDurch;
    @JoinColumn(name = "BWKennung", referencedColumnName = "BWKennung")
    @ManyToOne
    private Bewohner bewohner;
    @JoinColumn(name = "BWIKID", referencedColumnName = "BWIKID")
    @ManyToOne
    private BWInfoKat kategorie;

    // ==
    // 1:N Relationen
    // ==
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planung")
    private Collection<SYSPLAN2VORGANG> attachedVorgaenge;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planung")
    private Collection<PlanKontrolle> kontrollen;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "planung")
    private Collection<MassTermin> massnahmen;

    public Planung() {
    }

    public Planung(Bewohner bewohner) {
        this.bewohner = bewohner;
        this.angesetztDurch = OPDE.getLogin().getUser();
        massnahmen = new ArrayList<MassTermin>();
        kontrollen = new ArrayList<PlanKontrolle>();
        attachedVorgaenge = new ArrayList<SYSPLAN2VORGANG>();
    }

    public Long getPlanID() {
        return planID;
    }

    public void setPlanID(Long planID) {
        this.planID = planID;
    }


    public String getStichwort() {
        return stichwort;
    }

    public void setStichwort(String stichwort) {
        this.stichwort = stichwort;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getZiel() {
        return ziel;
    }

    public void setZiel(String ziel) {
        this.ziel = ziel;
    }

    public Date getVon() {
        return von;
    }

    public void setVon(Date von) {
        this.von = von;
    }

    public Date getBis() {
        return bis;
    }

    public void setBis(Date bis) {
        this.bis = bis;
    }


    public long getPlanKennung() {
        return planKennung;
    }

    public void setPlanKennung(long planKennung) {
        this.planKennung = planKennung;
    }

    public Date getNKontrolle() {
        return nKontrolle;
    }

    public void setNKontrolle(Date nKontrolle) {
        this.nKontrolle = nKontrolle;
    }

    public Users getAbgesetztDurch() {
        return abgesetztDurch;
    }

    public void setAbgesetztDurch(Users abgesetztDurch) {
        this.abgesetztDurch = abgesetztDurch;
    }

    public Users getAngesetztDurch() {
        return angesetztDurch;
    }

    public void setAngesetztDurch(Users angesetztDurch) {
        this.angesetztDurch = angesetztDurch;
    }

    public Bewohner getBewohner() {
        return bewohner;
    }

    public void setBewohner(Bewohner bewohner) {
        this.bewohner = bewohner;
    }

    public BWInfoKat getKategorie() {
        return kategorie;
    }

    public void setKategorie(BWInfoKat kategorie) {
        this.kategorie = kategorie;
    }

    public Collection<SYSPLAN2VORGANG> getAttachedVorgaenge() {
        return attachedVorgaenge;
    }

    public boolean isAbgesetzt() {
        return bis.before(SYSConst.DATE_BIS_AUF_WEITERES);
    }



    public Collection<PlanKontrolle> getKontrollen() {
        return kontrollen;
    }

    public Collection<MassTermin> getMassnahmen() {
        return massnahmen;
    }

    @Override
    public long getPITInMillis() {
        return von.getTime();
    }

    @Override
    public String getContentAsHTML() {
        // TODO: fehlt noch
        return "<html>not yet</html>";
    }

    @Override
    public String getPITAsHTML() {
        // TODO: fehlt noch
        return "<html>not yet</html>";
    }

    @Override
    public long getID() {
        return planID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (planID != null ? planID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Planung)) {
            return false;
        }
        Planung other = (Planung) object;
        if ((this.planID == null && other.planID != null) || (this.planID != null && !this.planID.equals(other.planID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.planung.Planung[planID=" + planID + "]";
    }
}