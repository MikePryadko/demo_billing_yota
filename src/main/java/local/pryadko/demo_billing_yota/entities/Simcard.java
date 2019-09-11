package local.pryadko.demo_billing_yota.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Data
@Entity
@Table(name = "simcards")
public class Simcard extends BaseEntity {
    @Column(nullable = false)
    private Long serialNumber;

    @Column(nullable = false)
    private Long phoneNumber;

    @Column(nullable = false)
    private Boolean enabled;

    private List<VoicePack> voicePacks;

    private List<TrafficPack> trafficPacks;
}
