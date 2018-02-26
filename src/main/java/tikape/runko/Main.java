package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.AnnosDao;
import tikape.runko.database.AnnosRaakaAineDao;
import tikape.runko.database.RaakaAineDao;
import tikape.runko.domain.Annos;
import tikape.runko.domain.AnnosRaakaAine;
import tikape.runko.domain.RaakaAine;

public class Main {

    public static void main(String[] args) throws Exception {
        Database database = new Database("jdbc:sqlite:smoothiet.db");
        database.init();

        AnnosDao annosDao = new AnnosDao(database);
        RaakaAineDao ainesDao = new RaakaAineDao(database);
        AnnosRaakaAineDao annosRaakaAineDao = new AnnosRaakaAineDao(database);
        
        //datan lisääminen sivuille, joilla sitä tarvitaan
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        get("/annokset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());
            map.put("ainekset", ainesDao.findAll());

            return new ModelAndView(map, "annokset");
        }, new ThymeleafTemplateEngine());
        
        //näytä smoothiekohtaiset ainesosat
        get("/annokset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annos", annosDao.findOne(Integer.parseInt(req.params(":id"))));
            map.put("ainekset", ainesDao.findBySmoothie(Integer.parseInt(req.params(":id"))));

            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());
        
        get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ainekset", ainesDao.findAll());

            return new ModelAndView(map, "ainekset");
        }, new ThymeleafTemplateEngine());
        
        get("/tilasto", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ainekset", ainesDao.findAll());
            map.put("annokset", annosDao.findAll());

            return new ModelAndView(map, "tilasto");
        }, new ThymeleafTemplateEngine());
        
        //raaka-aineen lisääminen
        post("/ainekset", (req, res) -> {
            RaakaAine uusi = new RaakaAine(null, req.queryParams("aine"));
            ainesDao.saveOrUpdate(uusi);
            res.redirect("/ainekset");
            return "";
        });
        //raaka-aineen poistaminen 
        post("ainekset/:id/poista", (req, res) -> {
            
            HashMap map = new HashMap<>();
            ainesDao.delete(Integer.parseInt(req.params(":id")));
            res.redirect("/ainekset");
            
            return "";
        });
        
     
        //smoothien lisääminen ja raaka-aineen lisääminen smoothieen
        post("/annokset", (req, res) -> {
            
            if(req.queryParams("annos") != null){
                Annos annos = new Annos(null, req.queryParams("annos"));
                annosDao.saveOrUpdate(annos);
            }else{
            
                Integer annosId = annosDao.findByName(req.queryParams("smoothie")).getId();
                Integer ainesId = ainesDao.findByName(req.queryParams("raakaAine")).getId();

                AnnosRaakaAine uusi = new AnnosRaakaAine(null, annosId, 
                        ainesId, Integer.parseInt(req.queryParams("järjestys")), 
                        req.queryParams("määrä"), req.queryParams("ohje"));

                annosRaakaAineDao.saveOrUpdate(uusi);
            }
                    
            res.redirect("/annokset");
            return "";
        });
        
        //smoothien haku raaka-aineen perusteella (ei toimi lähellekään)
//        post("/tilasto", (req, res) -> {
//            ainesDao.findByName(req.queryParams("haettava"));
//            res.redirect("/annokset");
//            return "";
//        });        
    }
}
