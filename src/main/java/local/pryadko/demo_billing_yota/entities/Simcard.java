package local.pryadko.demo_billing_yota.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "simcards")
public class Simcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long serialNumber;

    @Column(nullable = false, unique = true)
    private Long phoneNumber;

    @Column(nullable = false)
    private Boolean enabled = true;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "simcard_sn")
    private List<VoicePack> voicePacks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "simcard_sn")
    private List<TrafficPack> trafficPacks = new ArrayList<>();


    public Simcard(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
