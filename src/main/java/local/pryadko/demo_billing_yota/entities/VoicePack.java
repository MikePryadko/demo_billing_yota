package local.pryadko.demo_billing_yota.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "voicepacks")
public class VoicePack extends BaseEntity {
    @Column(nullable = false)
    private Integer minutes;

    @Column(nullable = false)
    private Long expiredUtc;
}
