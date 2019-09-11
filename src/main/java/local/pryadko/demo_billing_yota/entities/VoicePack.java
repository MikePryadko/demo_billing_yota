package local.pryadko.demo_billing_yota.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "voicepacks")
public class VoicePack extends BaseEntity {
    @Column(nullable = false)
    private Integer minutes;

    @Column(nullable = false)
    private Long expiredUtc;
}
