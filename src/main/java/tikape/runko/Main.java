package tikape.runko;

import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Spark;
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
        // asetetaan portti jos heroku antaa PORT-ympÃ¤ristÃ¶muuttujan
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }

        // alla oleva mÃ¤Ã¤rittelee css-tiedoston sijainnin
        staticFileLocation("/public");

        Database database = new Database("jdbc:sqlite:smoothiet.db");
        database.init();

        AnnosDao annosDao = new AnnosDao(database);
        RaakaAineDao ainesDao = new RaakaAineDao(database);
        AnnosRaakaAineDao annosRaakaAineDao = new AnnosRaakaAineDao(database);

        //datan lisÃ¤Ã¤minen sivuille, joilla sitÃ¤ tarvitaan
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

        //nÃ¤ytÃ¤ smoothiekohtaiset ainesosat
        get("/annokset/:id", (Request req, Response res) -> {
            HashMap map = new HashMap<>();
            map.put("annos", annosDao.findOne(Integer.parseInt(req.params(":id"))));
            map.put("ainekset", ainesDao.findBySmoothieId(Integer.parseInt(req.params(":id"))));
            List<AnnosRaakaAine> annosAineet = annosRaakaAineDao.findBySmoothieId(Integer.parseInt(req.params(":id")));
            //annosAineet.sort();
            map.put("annosAineet", annosRaakaAineDao.findBySmoothieId(Integer.parseInt(req.params(":id"))));

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

        //nÃ¤ytÃ¤ raaka-ainekohtaiset smoothiet
        get("/tilasto/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("aines", ainesDao.findOne(Integer.parseInt(req.params(":id"))));
            map.put("annokset", annosDao.findByRaakaAineId(Integer.parseInt(req.params(":id"))));

            return new ModelAndView(map, "ainesosa");
        }, new ThymeleafTemplateEngine());

        //raaka-aineen lisÃ¤Ã¤minen
        post("/ainekset", (req, res) -> {
            RaakaAine uusi = new RaakaAine(null, req.queryParams("aine"));
            ainesDao.saveOrUpdate(uusi);
            res.redirect("/ainekset");
            return "";
        });
        //raaka-aineen poistaminen 
        post("ainekset/:id/poista", (req, res) -> {

            ainesDao.delete(Integer.parseInt(req.params(":id")));
            res.redirect("/ainekset");

            return "";
        });

        //raaka-aineen poistaminen annoksesta
        post("annokset/:id/poista", (req, res) -> {

            annosRaakaAineDao.delete(Integer.parseInt(req.params(":id")));
            res.redirect("/");

            return "";
        });

        //smoothien poistaminen
        post("annokset/:id/poistahan", (req, res) -> {

            annosDao.delete(Integer.parseInt(req.params(":id")));
            res.redirect("/annokset");

            return "";
        });

        //smoothien lisÃ¤Ã¤minen ja raaka-aineen lisÃ¤Ã¤minen smoothieen
        post("/annokset", (req, res) -> {

            if (req.queryParams("annos") != null) {
                Annos annos = new Annos(null, req.queryParams("annos"));
                annosDao.saveOrUpdate(annos);
            } else {

                Integer annosId = annosDao.findByName(req.queryParams("smoothie")).getId();
                Integer ainesId = ainesDao.findByName(req.queryParams("raakaAine")).getId();
                List<AnnosRaakaAine> smoothienAnnosRaakaAineet = annosRaakaAineDao.findBySmoothieId(annosId);
                Integer suurin = 0;
                for (AnnosRaakaAine a : smoothienAnnosRaakaAineet) {
                    Integer järjestys = a.getJarjestys();
                    if (järjestys >= suurin) {
                        suurin = järjestys;
                    }
                }

                AnnosRaakaAine uusi = new AnnosRaakaAine(null, "nimi", annosId,
                        ainesId, suurin + 1,
                        req.queryParams("määrä"), req.queryParams("ohje"));

                annosRaakaAineDao.saveOrUpdate(uusi);
            }

            res.redirect("/annokset");
            return "";
        });
    }
}
