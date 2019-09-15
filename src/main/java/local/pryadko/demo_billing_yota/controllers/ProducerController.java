package local.pryadko.demo_billing_yota.controllers;

import local.pryadko.demo_billing_yota.entities.Simcard;
import local.pryadko.demo_billing_yota.entities.VoicePack;
import local.pryadko.demo_billing_yota.exceptions.BadRequestException;
import local.pryadko.demo_billing_yota.exceptions.NotFoundException;
import local.pryadko.demo_billing_yota.repository.SimcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController("/producer")
public class ProducerController {

    @Autowired
    private SimcardRepository simcardRepository;

    @PostMapping("/voice/{simSN}")
    public void addVoice(@PathVariable long simSN, @RequestParam int minutes, @RequestParam long expired) {
        if (minutes < 1)
            throw new BadRequestException("Minutes count must be greater then 0");
        if (expired <= System.currentTimeMillis())
            throw new BadRequestException("Expired must be in future");

        Simcard sc = simcardRepository.getBySnWithVoice(simSN);
        if (sc == null)
            throw new NotFoundException("Simcard with SN " + simSN + " not exists");

        sc.getVoicePacks().add(new VoicePack(minutes, expired));
        simcardRepository.save(sc);
    }

}
