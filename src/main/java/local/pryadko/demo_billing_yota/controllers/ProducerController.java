package local.pryadko.demo_billing_yota.controllers;

import local.pryadko.demo_billing_yota.entities.Simcard;
import local.pryadko.demo_billing_yota.entities.TrafficPack;
import local.pryadko.demo_billing_yota.entities.VoicePack;
import local.pryadko.demo_billing_yota.exceptions.BadRequestException;
import local.pryadko.demo_billing_yota.exceptions.NotFoundException;
import local.pryadko.demo_billing_yota.repository.SimcardRepository;
import local.pryadko.demo_billing_yota.services.SimcardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;


@RestController
public class ProducerController {

    @Autowired
    private SimcardRepository simcardRepository;

    @Autowired
    private SimcardService simcardService;

    @Transactional
    @PostMapping("/voice/{simSN}")
    public void addVoice(@PathVariable long simSN, @RequestParam int minutes, @RequestParam long expired) {
        if (minutes < 1)
            throw new BadRequestException("Minutes count must be greater then 0");
        if (expired <= System.currentTimeMillis())
            throw new BadRequestException("Expired must be in future");

        Simcard sc = simcardRepository.getBySnWithVoice(simSN);
        if (sc == null)
            throw new NotFoundException("Simcard with SN " + simSN + " not exists");
        simcardService.checkSimcardEnabled(sc);

        sc.getVoicePacks().add(new VoicePack(minutes, expired));
        simcardRepository.save(sc);
    }

    @Transactional
    @PostMapping("/traffic/{simSN}")
    public void addTraffic(@PathVariable long simSN, @RequestParam int megabytes, @RequestParam long expired) {
        if (megabytes < 1)
            throw new BadRequestException("Megabytes count must be greater then 0");
        if (expired <= System.currentTimeMillis())
            throw new BadRequestException("Expired must be in future");

        Simcard sc = simcardRepository.getBySnWithTraffic(simSN);
        if (sc == null)
            throw new NotFoundException("Simcard with SN " + simSN + " not exists");
        simcardService.checkSimcardEnabled(sc);

        sc.getTrafficPacks().add(new TrafficPack(megabytes, expired));
        simcardRepository.save(sc);
    }

}
