package local.pryadko.demo_billing_yota.controllers;

import local.pryadko.demo_billing_yota.entities.Simcard;
import local.pryadko.demo_billing_yota.entities.TrafficPack;
import local.pryadko.demo_billing_yota.entities.VoicePack;
import local.pryadko.demo_billing_yota.exceptions.NotFoundException;
import local.pryadko.demo_billing_yota.repository.SimcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/consumer")
public class ConsumerController {

    @Autowired
    private SimcardRepository simcardRepository;

    @PostMapping("/voice/{simSN}/{mntCount}")
    public void writeOffVoice(@PathVariable long simSN, @PathVariable int mntCount) {
        Simcard sc = simcardRepository.getBySnWithVoice(simSN);
        if (sc == null)
            throw new NotFoundException("Simcard with SN " + simSN + " not exists");

        System.out.println("voices pack is:");
        for (VoicePack vp : sc.getVoicePacks()) {
            System.out.println("\texpired: " + vp.getExpiredUtc() + ", minutes: " + vp.getMinutes());
        }

    }

    @PostMapping("/traffic/{simSN}/{mbCount}")
    public void writeOffTraffic(@PathVariable long simSN, @PathVariable int mbCount) {
        Simcard sc = simcardRepository.getBySnWithTraffic(simSN);
        if (sc == null)
            throw new NotFoundException("Simcard with SN " + simSN + " not exists");

        System.out.println("traffic pack is:");
        for (TrafficPack tp : sc.getTrafficPacks()) {
            System.out.println("\texpired: " + tp.getExpiredUtc() + ", megabytes: " + tp.getMegabytes());
        }
    }

}
