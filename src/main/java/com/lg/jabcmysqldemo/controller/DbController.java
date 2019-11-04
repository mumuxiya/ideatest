package com.lg.jabcmysqldemo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lg.jabcmysqldemo.model.Dy;
import com.lg.jabcmysqldemo.model.Hrefs;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: lg
 * Date: 2019/10/17 13:15
 * Content:
 */

@Controller
public class DbController {
    @GetMapping("/dbjx")
    public String dbjx(Model model, @RequestParam(value = "id", required = true) Integer id) throws IOException {
        String url = "https://movie.douban.com/subject/"+id+"/";
        Dy dy = new Dy();
        Document doc = Jsoup.connect(url).get();
        Element e = doc.getElementById("content");
        dy.setName(e.getElementsByTag("h1").get(0).getElementsByTag("span").get(0).text());
        List<Hrefs> hs = new ArrayList<Hrefs>();
        Elements es = e.getElementsByClass("bs").get(0).getElementsByTag("a");
        for(Element ee: es){
            Hrefs hrefs = new Hrefs();
            String name = ee.text();
            if("哔哩哔哩".equals(name)){
                continue;
            }
            String _url = ee.attr("href");
            String _url2 = _url.replaceAll("%3A",":").replaceAll("%2F","/").replaceAll("%26","&").replaceAll("%3D","=");
            hrefs.setUrl(_url2.substring(_url.indexOf("?url=")+5, _url2.indexOf("html")+4));
            hrefs.setName(name);
            System.out.println(hrefs);
            hs.add(hrefs);
        }
        dy.setHrefs(hs);
        model.addAttribute("dy", dy);
        List urls = new ArrayList();
        urls.add("http://www.pangujiexi.cc/jiexi.php?url=");
        urls.add("http://beaacc.com/api.php?url=");
        urls.add("http://www.playm3u8.cn/jiexi.php?url=");
        urls.add("http://77jx.net/url=");
        urls.add("http://cn.bjbanshan.cn/jx.php?url=");
        urls.add("http://www.ckmov.vip/api.php?url=");
        urls.add("http://607p.com/?url=");
        urls.add("http://bofang.online/?url=");
        model.addAttribute("urls", urls);
        return "play";
    }
    @GetMapping("/my")
    public String getmy(){
        return "my";
    }
}
