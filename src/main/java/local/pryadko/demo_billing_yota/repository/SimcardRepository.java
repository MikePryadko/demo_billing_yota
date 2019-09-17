package local.pryadko.demo_billing_yota.repository;

import local.pryadko.demo_billing_yota.entities.Simcard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface SimcardRepository extends CrudRepository<Simcard, Long> {

    Simcard getSimcardBySerialNumber(long serialNumber);

    @Query("select sim, vp from Simcard sim left join fetch sim.voicePacks vp where sim.serialNumber = :simSN order by vp.expiredUtc desc")
    Simcard getBySnWithVoice(Long simSN);

    @Query("select sim, tp from Simcard sim left join fetch sim.trafficPacks tp where sim.serialNumber = :simSN order by tp.expiredUtc desc")
    Simcard getBySnWithTraffic(Long simSN);

}
