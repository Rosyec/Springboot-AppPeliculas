package com.example.consumirapi_rest.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.consumirapi_rest.http.IHttpService;
import com.example.consumirapi_rest.models.Pelicula;
import com.example.consumirapi_rest.service.IUploadService;

@Controller
@RequestMapping("/api")

public class IndexController {

    Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private IHttpService httpService;

    @Autowired
    private IUploadService uploadService;

    @GetMapping({ "index" })
    public String index(Model model) {
        Map<String, Object> map = httpService.buscarTodos();
        boolean error = (Boolean) map.get("error");
        if (error) {
            model.addAttribute("error", map.get("message"));
            model.addAttribute("generos", this.getGeneros());
            return "index";
        } else {

            model.addAttribute("peliculas", map.get("peliculas"));
            model.addAttribute("message", map.get("message"));
            model.addAttribute("generos", this.getGeneros());
            return "index";
        }
    }

    @GetMapping("/index/{term}")
    public String buscarPorNombre(@PathVariable String term, Model model) {
        Map<String, Object> map = httpService.buscarPorNombre(term);
        boolean error = (Boolean) map.get("error");
        if (error) {
            model.addAttribute("error", map.get("message"));
            model.addAttribute("generos", this.getGeneros());
            return "index";
        } else {

            model.addAttribute("peliculas", map.get("peliculas"));
            model.addAttribute("success", map.get("message"));
            model.addAttribute("generos", this.getGeneros());
            model.addAttribute("success", "Se encontrarón las siguientes películas");
            return "index";
        }
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/catalogo/{genero}")
    public String catalogo(@PathVariable String genero, Model model) {
        Map<String, Object> map = httpService.buscarPorGenero(genero);
        boolean error = (Boolean) map.get("error");
        if (error) {
            model.addAttribute("error", map.get("message"));
            model.addAttribute("generos", this.getGeneros());
            return "catalogo";
        } else {

            List<Pelicula> size = (List<Pelicula>) map.get("peliculas");
            model.addAttribute("peliculas", map.get("peliculas"));
            model.addAttribute("generos", this.getGeneros());
            model.addAttribute("success", map.get("message"));
            model.addAttribute("genero", genero);
            model.addAttribute("listSize", size.size());
            return "catalogo";
        }
    }

    @GetMapping("/form")
    public String form(Model model) {
        Pelicula pelicula = new Pelicula();
        model.addAttribute("titulo", "Agregar película");
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("generos", this.getGeneros());
        return "form";
    }

    @PostMapping("/form")
    public String procesar(@Valid Pelicula pelicula, @RequestParam("file") MultipartFile caratula, BindingResult result,
            Model model, RedirectAttributes flash) {

        if (result.hasErrors()) {
            return "redirect:/api/form";
        }

        if (!caratula.isEmpty()) {
            int sizeCaratula = (int) (caratula.getSize() / (1024 * 1024));
            if (sizeCaratula <= 1) {
                if (pelicula.getId() != null && pelicula.getId() > 0 && !pelicula.getCaratula().isEmpty()
                        && pelicula.getCaratula().length() > 0) {
                    uploadService.delete(pelicula.getCaratula());
                }
                String nombreUnico = null;
                nombreUnico = uploadService.copy(caratula);
                flash.addFlashAttribute("info", "Has subido correctamente " + caratula.getOriginalFilename());
                pelicula.setCaratula(nombreUnico);
            } else {
                flash.addFlashAttribute("error", "La imagen no debe pesar más de 1 Mega.");
                return "redirect:/api/form";
            }

        } else {
            pelicula.setCaratula("");
        }

        this.httpService.guardar(pelicula);
        flash.addFlashAttribute("success", "Película agregada con exito!");
        return "redirect:/api/index";

    }

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {
        Resource recurso = uploadService.load(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    public List<String> getGeneros() {
        List<String> generos = new ArrayList<>();
        generos.add("Acción");
        generos.add("Fantasía");
        generos.add("Aventuras");
        generos.add("Terror");
        generos.add("Ciencia-Ficción");
        return generos;
    }
}
