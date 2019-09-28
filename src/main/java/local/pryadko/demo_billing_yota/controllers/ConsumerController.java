package local.pryadko.demo_billing_yota.controllers;

import local.pryadko.demo_billing_yota.entities.Simcard;
import local.pryadko.demo_billing_yota.entities.TrafficPack;
import local.pryadko.demo_billing_yota.entities.VoicePack;
import local.pryadko.demo_billing_yota.exceptions.NotAcceptableException;
import local.pryadko.demo_billing_yota.exceptions.NotFoundException;
import local.pryadko.demo_billing_yota.repository.SimcardRepository;
import local.pryadko.demo_billing_yota.services.SimcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
public class ConsumerController {

    @Autowired
    private SimcardRepository simcardRepository;

    @Autowired
    private SimcardService simcardService;

    @Transactional
    @DeleteMapping("/voice/{simSN}")
    public void writeOffVoice(@PathVariable long simSN, @RequestParam int minutes) {
        Simcard sc = simcardRepository.getBySnWithVoice(simSN);
        if (sc == null)
            throw new NotFoundException("Simcard with SN " + simSN + " not exists");
        simcardService.checkSimcardEnabled(sc);

        List<VoicePack> vp = sc.getVoicePacks();
        for (int x = vp.size() - 1; x >= 0; x--) {
            int availMinutes = vp.get(x).getMinutes();
            long currentTS = System.currentTimeMillis();

            if (vp.get(x).getExpiredUtc() <= currentTS) {
                vp.remove(x);
            } else if (availMinutes <= minutes) {
                minutes -= availMinutes;
                vp.remove(x);
            } else {
                vp.get(x).setMinutes(availMinutes - minutes);
                minutes = 0;
                break;
            }
        }

        simcardRepository.save(sc);

        if (minutes > 0)
            throw new NotAcceptableException("Not enough voice packs");
    }

    @Transactional
    @DeleteMapping("/traffic/{simSN}")
    public void writeOffTraffic(@PathVariable long simSN, @RequestParam int megabytes) {
        Simcard sc = simcardRepository.getBySnWithTraffic(simSN);
        if (sc == null)
            throw new NotFoundException("Simcard with SN " + simSN + " not exists");
        simcardService.checkSimcardEnabled(sc);

        List<TrafficPack> tp = sc.getTrafficPacks();
        for (int x = tp.size() - 1; x >= 0; x--) {
            int availMegabytes = tp.get(x).getMegabytes();
            long currentTS = System.currentTimeMillis();

            if (tp.get(x).getExpiredUtc() <= currentTS) {
                tp.remove(x);
            } else if (availMegabytes <= megabytes) {
                megabytes -= availMegabytes;
                tp.remove(x);
            } else {
                tp.get(x).setMegabytes(availMegabytes - megabytes);
                megabytes = 0;
                break;
            }
        }

        simcardRepository.save(sc);

        if (megabytes > 0)
            throw new NotAcceptableException("Not enough traffic packs");
    }

}
