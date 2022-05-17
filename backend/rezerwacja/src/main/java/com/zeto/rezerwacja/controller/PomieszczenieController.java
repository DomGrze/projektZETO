package com.zeto.rezerwacja.controller;


import com.zeto.rezerwacja.exception.ResourceNotFoundException;
import com.zeto.rezerwacja.model.Pomieszczenie;
import com.zeto.rezerwacja.repo.PomieszczenieRepository;
import com.zeto.rezerwacja.service.PomieszczenieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pomieszczenie")
@CrossOrigin("*")
public class PomieszczenieController {

    @Autowired
    private PomieszczenieService pomieszczenieService;
    @Autowired
    private PomieszczenieRepository pomieszczenieRepository;

    //dodawanie pomieszczeń
    @PostMapping("/add")
    public Pomieszczenie dodajPomieszczenie(@RequestBody Pomieszczenie pomieszczenie) throws Exception{

        return this.pomieszczenieService.dodajPomieszczenie(pomieszczenie);
    }

    //szukanie pomieszczeń po nazwie
    @GetMapping("/find/{nazwa}")
    public List<Pomieszczenie> znajdzPomieszczenie(@PathVariable("nazwa") String nazwa){
        return this.pomieszczenieService.getPomieszczenie(nazwa);
    }
    //szukanie pomieszczeń po typie
    @GetMapping("/findtyp/{typ}")
    public List<Pomieszczenie> znajdzTypPomieszczenie(@PathVariable("typ") String typ){
        return this.pomieszczenieService.getTypPomieszczenie(typ);
    }

    //szukanie pomieszczeń po id
    @GetMapping("/findid/{id}")
    public Optional<Pomieszczenie> znajdzPomieszczenieId(@PathVariable("id") Long id){
        return this.pomieszczenieService.findPomieszczenie(id);
    }

    //usuwanie pomieszczenia
    @DeleteMapping("/delete/{id}")
    public void usunPomieszczenie(@PathVariable("id") Long id){
        this.pomieszczenieService.usunPomieszczenie(id);
    }
    //wszystkie pomieszczenia
    @GetMapping("/findall")
    List<Pomieszczenie> show(Model map) {
        List<Pomieszczenie> pomieszczenia = pomieszczenieService.getAllPomieszczenia();
        map.addAttribute("pomieszczenia", pomieszczenia);
        return pomieszczenia;
    }

    //edytuj pomieszczenie
    @PostMapping("/edit/{id}")
    public ResponseEntity<Pomieszczenie> updatePomieszczenie(@PathVariable long id,
                                                             @RequestBody Pomieszczenie pomieszczenie){
        Pomieszczenie updatePomieszczenie = pomieszczenieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pomieszczenie o podanym id nie istnieje! Id:" + id));

        if (pomieszczenie.getNazwa() != null) {
            updatePomieszczenie.setNazwa(pomieszczenie.getNazwa());
        }
        if (pomieszczenie.getKodPocztowy() != null) {
            updatePomieszczenie.setKodPocztowy(pomieszczenie.getKodPocztowy());
        }
        if (pomieszczenie.getMiasto() != null) {
            updatePomieszczenie.setMiasto(pomieszczenie.getMiasto());
        }
        if (pomieszczenie.getUlica() != null) {
            updatePomieszczenie.setUlica(pomieszczenie.getUlica());
        }
        if (pomieszczenie.getTyp() != null) {
            updatePomieszczenie.setTyp(pomieszczenie.getTyp());
        }
        if (pomieszczenie.getNumer() != null) {
            updatePomieszczenie.setNumer(pomieszczenie.getNumer());
        }
        if (pomieszczenie.getOpis() != null) {
            updatePomieszczenie.setOpis(pomieszczenie.getOpis());
        }
        if (pomieszczenie.getWojewodztwo() != null) {
            updatePomieszczenie.setWojewodztwo(pomieszczenie.getWojewodztwo());
        }
        pomieszczenieRepository.save(updatePomieszczenie);

        return ResponseEntity.ok(updatePomieszczenie);


    }

}