package entity.prescription;

import entity.nursingprocess.Intervention;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "MPFormen")
/**
 *
 */
public class DosageForm implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FormID")
    private Long id;
    @Basic(optional = false)
    @Column(name = "Zubereitung")
    private String preparation;
    @Basic(optional = false)
    @Column(name = "AnwText")
    private String usageText;
    @Basic(optional = false)
    @Column(name = "AnwEinheit")
    private short usageUnit;
    @Basic(optional = false)
    @Column(name = "PackEinheit")
    private short packUnit;
    @Basic(optional = false)
    @Column(name = "Stellplan")
    private short dailyPlan;
    @Basic(optional = false)
    @Column(name = "Status")
    private short state;
    @Basic(optional = false)
    @Column(name = "Equiv")
    private int equivalent;

    public DosageForm() {
    }

    public String getPreparation() {
        return preparation;
    }

    public String getUsageText() {
        return usageText;
    }

    public short getUsageUnit() {
        return usageUnit;
    }

    public short getPackUnit() {
        return packUnit;
    }

    /**
     * das hier ist einfach eine Sortierungsmöglichkeit, die bei der Stellplanerzeugung berücksichtigt wird.
     * Da kann man festlegen, was auf dem Plan zusammenstehen soll.
     * Z.B. für Tropfen oder so. 1 sind hier Tropfen, 2 Spritzen. Der Fantasie sind keine Grenzen gesetzt.
     * Die Vorlage ist so eingestellt, dass alles über 0 grau hinterlegt wird.
     * @return
     */
    public short getDailyPlan() {
        return dailyPlan;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public int getSameAs() {
        return equivalent;
    }

    public boolean isUPR1(){
        return state == DosageFormTools.UPR1;
    }

    public Intervention getIntervention() {
        return intervention;
    }

    // N:1 Relationen
    @JoinColumn(name = "MassID", referencedColumnName = "MassID")
    @ManyToOne
    private Intervention intervention;


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof DosageForm)) {
            return false;
        }
        DosageForm other = (DosageForm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "DosageForm{" +
                "id=" + id +
                ", preparation='" + preparation + '\'' +
                ", usageText='" + usageText + '\'' +
                ", usageUnit=" + usageUnit +
                ", packUnit=" + packUnit +
                ", dailyPlan=" + dailyPlan +
                ", state=" + state +
                ", sameAs=" + equivalent +
                ", intervention=" + intervention +
                '}';
    }
}