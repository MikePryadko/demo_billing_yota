package local.pryadko.demo_billing_yota.services;

import local.pryadko.demo_billing_yota.entities.Simcard;
import local.pryadko.demo_billing_yota.exceptions.NotAcceptableException;
import org.springframework.stereotype.Service;

@Service
public class SimcardService {
    public void checkSimcardEnabled(Simcard sc) {
        if (!sc.getEnabled())
            throw new NotAcceptableException("Simcard is blocked");
    }
}
