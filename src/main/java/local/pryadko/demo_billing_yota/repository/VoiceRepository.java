package local.pryadko.demo_billing_yota.repository;

import local.pryadko.demo_billing_yota.entities.VoicePack;
import org.springframework.data.repository.CrudRepository;

public interface VoiceRepository extends CrudRepository<VoicePack, Long> {
}
