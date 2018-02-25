package tikape.runko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.AnnosDao;
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

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        get("/annokset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annokset", annosDao.findAll());

            return new ModelAndView(map, "annokset");
        }, new ThymeleafTemplateEngine());

        get("/annokset/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("annos", annosDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "annos");
        }, new ThymeleafTemplateEngine());
        
        get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ainekset", ainesDao.findAll());

            return new ModelAndView(map, "ainekset");
        }, new ThymeleafTemplateEngine());
        
        
        post("/ainekset", (req, res) -> {
            RaakaAine uusi = new RaakaAine(null, req.queryParams("aine"), new ArrayList<>());
            ainesDao.saveOrUpdate(uusi);
            res.redirect("/ainekset");
            return "";
        });
        
        post("/annokset", (req, res) -> {
            Annos uusi = new Annos(null, req.queryParams("annos"), new ArrayList<>());
            annosDao.saveOrUpdate(uusi);
            res.redirect("/annokset");
            return "";
        });
        
        
        post("/annokset", (req, res) -> {
            ArrayList<AnnosRaakaAine> aineet = new ArrayList<>();
            RaakaAine aines = new RaakaAine(null, req.queryParams("raakaAine"), new ArrayList<>());
            
            Annos annos = new Annos(null, req.queryParams("smoothie"), aineet);
            
            AnnosRaakaAine uusi = new AnnosRaakaAine(null, aines,
                    annos, req.queryParams("määrä"), req.queryParams("ohje"));
            
            ArrayList<AnnosRaakaAine> ls = annos.getRaakaAineitaAnnoksessa();
            ls.add(uusi);
            ArrayList<AnnosRaakaAine> l = aines.getRaakaAineitaAnnoksessa();
            l.add(uusi);
            
            annos.setRaakaAineitaAnnoksessa(ls);
            aines.setRaakaAineitaAnnoksessa(l);
            
            annosDao.saveOrUpdate(annos);
            ainesDao.saveOrUpdate(aines);

                    
            res.redirect("/annokset");
            return "";
        });
    }
}
