package local.pryadko.demo_billing_yota.repository;

import local.pryadko.demo_billing_yota.entities.Simcard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SimcardRepository extends CrudRepository<Simcard, Long> {

    @Query("select sim, vp from Simcard sim left join fetch sim.voicePacks as vp where sim.serialNumber = :serialNumber order by vp.expiredUtc desc")
    public Simcard getBySnWithVoice(Long serialNumber);

    @Query("select sim, tp from Simcard sim left join fetch sim.trafficPacks as tp where sim.serialNumber = :serialNumber order by tp.expiredUtc desc")
    public Simcard getBySnWithTraffic(Long serialNumber);
}
