package com.Jonny.logic.Controller;

import com.Jonny.logic.Entity.Part;
import com.Jonny.logic.Service.PartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PartController {

    private PartService service;
    private String filterMethod = "";
    private String sortedByNeed = "0";
    private Integer logic = 0;

    @Autowired
    public void setPartService(PartService service){this.service = service;}


    @GetMapping("/")
    public String list(Model model, Pageable pageable){
        Page<Part> partPage = filterAndSort(pageable);
        PageWrapper<Part> page = new PageWrapper<Part>(partPage, "/");
        logic = getLogic();
        model.addAttribute("parts", page.getContent());
        model.addAttribute("filter", filterMethod);
        model.addAttribute("sort", sortedByNeed);
        model.addAttribute("page", page);
        model.addAttribute("logic", logic);
        return "index";
    }

    @GetMapping("/filter/{filter}")
    public String filterChoose(@RequestParam(required = false, defaultValue = "0") String filter) {
        filterMethod = filter;
        return "redirect:/";
    }

    @GetMapping("sort/{sortNeed}")
    public String sortChoose(@PathVariable String sortNeed){
        sortedByNeed = sortNeed;
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Part part = service.getPartById(id);
        model.addAttribute("part", part);
        return "operations/edit";
    }

    @PostMapping("/update")
    public String savePart(@RequestParam Integer id, @RequestParam String name, @RequestParam boolean need, int quantity) {
        service.updatePart(id, name, need, quantity);
        return "redirect:/";
    }

    @GetMapping("/new")
    public String newPart() {
        return "operations/new";
    }

    @PostMapping("/save")
    public String updatePart(@RequestParam String name,  @RequestParam boolean need, @RequestParam int quantity) {
        service.savePart(new Part(name, need, quantity));
        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.deletePart(id);
        return "redirect:/";
    }


    private Page<Part> filterAndSort(Pageable pageable) {
        Page<Part> page = null;
        if (filterMethod=="") {

            switch (sortedByNeed) {
                case "0":
                    page = service.findAllByOrderByIdAsc(pageable);
                    break;
                case "ASC":
                    page = service.findAllByOrderByNeedAsc(pageable);
                    break;
                case "DESC":
                    page = service.findAllByOrderByNeedDesc(pageable);
                    break;
            }
        }
        else {
            switch (sortedByNeed) {
                case "0":
                    page = service.search(filterMethod, pageable);
                    filterMethod="";
                    break;
                case "ASC":
                    page = service.findAllByOrderByNeedAsc(pageable);
                    break;
                case "DESC":
                    page = service.findAllByOrderByNeedDesc(pageable);
                    break;
            }
        }
        return page;
    }

    @GetMapping(path = "/search")
    public @ResponseBody
    Page<Part> search(@RequestParam(required = false, defaultValue = "") String filterMethod, Pageable pageable){
        return service.search(filterMethod, pageable);
    }

    final Logger logger = LoggerFactory.getLogger(PartController.class);

    @GetMapping(path = "/all")
    public @ResponseBody
    List<Part> getAllPart() {
        return service.findAll();
    }

    @GetMapping(path = "/logic")
    public @ResponseBody
    Integer getLogic() {
        List<Part> partList = service.findAll();
        if (partList==null) return 0;
        int counter = 0;
        for (Part part : partList) {
            if (part.isNeed()==true && part.getQuantity()==0) return 0;
            if (part.isNeed()==true && counter==0) counter = part.getQuantity();
            if (part.isNeed()==true && part.getQuantity()!=0 && counter > part.getQuantity()) counter = part.getQuantity();
        }
        logic = counter;
        return counter;
    }

}
