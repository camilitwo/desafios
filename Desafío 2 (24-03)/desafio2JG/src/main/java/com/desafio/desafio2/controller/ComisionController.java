package com.desafio.desafio2.controller;
import com.desafio.desafio2.dto.ComisionResponse;
import com.desafio.desafio2.dto.VentaRequest;
import com.desafio.desafio2.service.ComisionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comisiones")  // ¡Asegúrate de que coincida con la URL que usas!
public class ComisionController {
    @Autowired
    private ComisionServiceImpl comisionService;

    @PostMapping  // o @PostMapping("/") si prefieres
    public ComisionResponse calcularComision(@RequestBody VentaRequest request) {
        return comisionService.calcularComision(request);
    }
}