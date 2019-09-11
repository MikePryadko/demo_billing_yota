package local.pryadko.demo_billing_yota.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "traffic_packs")
public class TrafficPack extends BaseEntity {
    @Column(nullable = false)
    private Integer megabytes;

    @Column(nullable = false)
    private Long expiredUtc;
}
