package com.lg.jabcmysqldemo.controller;

import com.lg.jabcmysqldemo.model.Dy;
import com.lg.jabcmysqldemo.model.Hrefs;
import com.lg.jabcmysqldemo.model.WheelPlanting;
import com.lg.jabcmysqldemo.service.DyService;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: lg
 * Date: 2019/10/7 12:01
 * Content:
 */
@Controller
public class DyController {

    @Autowired
    private JdbcTemplate JdbcTemplate;
    @Autowired
    private DyService dyService;

    @GetMapping("/type")
    public String type(){
        return "type";
    }
    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping(value = {"/dy","/",""})
    public String dy(Model model, @RequestParam(value = "pageno", required = false) Integer pageno,
                     @RequestParam(value = "sort", required = false) String sort,
                     @RequestParam(value = "rank", required = false) String rank) throws Exception {
        File file = new File("C:\\Users\\Administrator\\Desktop\\images\\1561974694336164.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);


        String url = null;
        String tvurl = null;
        String zyurl = null;
        if(rank==null) rank="rankhot";
        if(pageno==null || pageno==0){
            url = "https://www.360kan.com/dianying/list.php?cat=all&year=all&area=all&act=all&rank="+rank+"&pageno=1";
            tvurl = "https://www.360kan.com/dianshi/list.php?cat=all&year=all&area=all&act=all&rank="+rank+"&pageno=1";
            zyurl = "https://www.360kan.com/zongyi/list.php?cat=all&year=all&area=all&act=all&rank="+rank+"&pageno=1";
            model.addAttribute("pageno",2);
        }else{
            url = "https://www.360kan.com/dianying/list.php?cat=all&year=all&area=all&act=all&rank="+rank+"&pageno="+pageno;
            tvurl = "https://www.360kan.com/dianshi/list.php?cat=all&year=all&area=all&act=all&rank="+rank+"&pageno="+pageno;
            zyurl = "https://www.360kan.com/zongyi/list.php?cat=all&year=all&area=all&act=all&rank="+rank+"&pageno="+pageno;
            model.addAttribute("pageno",pageno+1);
        }
        if(sort==null)sort="U";
        //String dburl = "https://movie.douban.com/j/new_search_subjects?sort="+sort+"&range=0,10&tags=%E7%94%B5%E5%BD%B1&playable=1&start=0";
        List<Dy> dys = dyService.getDyAll(url);
        List<Dy> tvs = dyService.getTv(tvurl);
        List<Dy> zys = dyService.getZy(zyurl);
        List<WheelPlanting> wheelPlantings = dyService.getWheelPlanting();
        List<Object> rankings = dyService.getRanking();

        //String dbs = dyService.getDb(dburl);
        //JSONObject object = JSON.parseObject(dbs);
        //model.addAttribute("dbs", object);
        model.addAttribute("zys", zys);
        model.addAttribute("dys", dys);
        model.addAttribute("wheelPlantings", wheelPlantings);
        model.addAttribute("rankings", rankings);
        model.addAttribute("tvs", tvs);
        model.addAttribute("dyph", dyService.danying(null));
        model.addAttribute("dyphaq", dyService.danying("爱情"));
        return "html/common/index";
    }

    @PostMapping("/dySearch")
    public String dySearch(Model model, @RequestParam(value = "value", required = false) String value) throws IOException {
        String url="https://so.360kan.com/index.php?from=&du=100&fr=100&pb=100&cat=1&st=101&pageno=1&kw="+value;
        ArrayList<Dy> dys = dyService.dySearch(url);
        model.addAttribute("dys",dys);
        return "list";
    }

    @GetMapping("/show/m/{url}.html")
    public  String show(@PathVariable("url") String url, Model model) throws IOException {
        model.addAttribute("url", "/m/"+url+".html");
        Dy dy = dyService.getPlay("https://www.360kan.com/m/"+url+".html");

        model.addAttribute("dy", dy);
        model.addAttribute("dyph", dyService.danying(null));
        List urls = new ArrayList();
        urls.add(new Hrefs("超清解析","http://www.pangujiexi.cc/jiexi.php?url="));
        urls.add(new Hrefs("高清解析","http://beaacc.com/api.php?url="));
        urls.add(new Hrefs("无广告解析","http://okjx.cc/?url="));
        urls.add(new Hrefs("解析四","http://www.playm3u8.cn/jiexi.php?url="));
        urls.add(new Hrefs("解析五","http://77jx.net/url="));
        urls.add(new Hrefs("解析六","http://cn.bjbanshan.cn/jx.php?url="));
        urls.add(new Hrefs("解析六","http://www.ckmov.vip/api.php?url="));
        urls.add(new Hrefs("解析七","http://607p.com/?url="));
        urls.add(new Hrefs("解析八","http://bofang.online/?url="));
        model.addAttribute("urls", urls);
        return "html/common/show";
    }
    @GetMapping("/play/m/{url}.html")
    public  String play(@PathVariable("url") String url, Model model) throws IOException {
        Dy dy = dyService.getPlay("https://www.360kan.com/m/"+url+".html");

        model.addAttribute("dy", dy);
        model.addAttribute("dyph", dyService.danying(null));
        List urls = new ArrayList();
        urls.add(new Hrefs("默认解析","http://jx.ph63.com/?url="));
        urls.add(new Hrefs("超清解析","http://www.pangujiexi.cc/jiexi.php?url="));
        urls.add(new Hrefs("高清解析","http://beaacc.com/api.php?url="));
        urls.add(new Hrefs("无广告解析","http://okjx.cc/?url="));
        urls.add(new Hrefs("解析四","http://www.playm3u8.cn/jiexi.php?url="));
        urls.add(new Hrefs("解析五","http://77jx.net/url="));
        urls.add(new Hrefs("解析六","http://cn.bjbanshan.cn/jx.php?url="));
        urls.add(new Hrefs("解析六","http://www.ckmov.vip/api.php?url="));
        urls.add(new Hrefs("解析七","http://607p.com/?url="));
        urls.add(new Hrefs("解析八","http://bofang.online/?url="));
        model.addAttribute("urls", urls);
        return "html/common/play";
    }
    @GetMapping("/zyplay/va/{url}.html")
    public  String zyplay(@PathVariable("url") String url, Model model) throws IOException {
        System.out.println(url);
        Dy dy = dyService.zyplay("https://www.360kan.com/m/"+url+".html");
        model.addAttribute("dy", dy);
        List urls = new ArrayList();
        urls.add(new Hrefs("超清解析","http://www.pangujiexi.cc/jiexi.php?url="));
        urls.add(new Hrefs("高清解析","http://beaacc.com/api.php?url="));
        urls.add(new Hrefs("无广告解析","http://okjx.cc/?url="));
        urls.add(new Hrefs("解析四","http://www.playm3u8.cn/jiexi.php?url="));
        urls.add(new Hrefs("解析五","http://77jx.net/url="));
        urls.add(new Hrefs("解析六","http://cn.bjbanshan.cn/jx.php?url="));
        urls.add(new Hrefs("解析六","http://www.ckmov.vip/api.php?url="));
        urls.add(new Hrefs("解析七","http://607p.com/?url="));
        urls.add(new Hrefs("解析八","http://bofang.online/?url="));
        model.addAttribute("urls", urls);
        return "play";
    }
    @GetMapping("/tvplay")
    public  String tvplay(Model model,@RequestParam(value = "url", required = false) String url) throws IOException {
        Dy dy = dyService.getTvPlay(url);
        model.addAttribute("dy", dy);
        List urls = new ArrayList();
        urls.add(new Hrefs("超清解析","http://www.pangujiexi.cc/jiexi.php?url="));
        urls.add(new Hrefs("高清解析","http://beaacc.com/api.php?url="));
        urls.add(new Hrefs("无广告解析","http://okjx.cc/?url="));
        urls.add(new Hrefs("解析四","http://www.playm3u8.cn/jiexi.php?url="));
        urls.add(new Hrefs("解析五","http://77jx.net/url="));
        urls.add(new Hrefs("解析六","http://cn.bjbanshan.cn/jx.php?url="));
        urls.add(new Hrefs("解析六","http://www.ckmov.vip/api.php?url="));
        urls.add(new Hrefs("解析七","http://607p.com/?url="));
        urls.add(new Hrefs("解析八","http://bofang.online/?url="));
        model.addAttribute("urls", urls);
        return "play";
    }
}
