package com.lg.jabcmysqldemo.service;

import com.lg.jabcmysqldemo.model.Dy;
import com.lg.jabcmysqldemo.model.Hrefs;
import com.lg.jabcmysqldemo.model.WheelPlanting;
import com.lg.jabcmysqldemo.tools.HttpClient;
import org.apache.ibatis.annotations.Select;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Name: lg
 * Date: 2019/10/14 14:53
 * Content:
 */
@Service
public class DyService {

    public List<Dy> getDyAll(String url) throws IOException {
        List<Dy> dys = new ArrayList<>();
        List<String> list=new ArrayList<String>();
        String PostUrl = "http://data.zz.baidu.com/urls?site=api.mumuxiya.com&token=oCHW77eSxqpXLuwB";
        String main = "https://www.360kan.com";
        Document doc = Jsoup.connect(url).get();//?rank=rankhot&cat=all&area=all&act=all&year=all&pageno=4
        // System.out.println(doc);
        Elements plays = doc.getElementsByClass("js-tongjic");
        int i=1;
        //遍历完imgs后，srcL链表已包含所有图片地�?
        for (Element play : plays) {
            if(i==13)continue;
            Dy dy = new Dy();
            String href = play.attr("href");
            dy.setUrl(href);
            list.add("https://api.mumuxiya.com/play"+href);
            //  Attribute
            //System.out.println(play);
//				  String href=play.attr("href");
            String p = play.toString();
            //写一个方法，取出href里面的内容，src里面的内容，以及span里的年份，视频名和评分，还有演员
            String[] s = p.split(">");
            String hr = s[0] + ">";
            String hre = main + hr.substring(hr.indexOf("href=") + 6, hr.indexOf("\">"));
            String sr = s[2] + ">";
            String src = sr.substring(sr.indexOf("src=") + 5, sr.indexOf("\">"));
            dy.setImgUrl(src);
            String year, name, actor, score;
            if (p.contains("付费")) {
                try {
                    year = s[6].substring(0, 4);
                    score = s[9].split("<")[0];
                    dy.setScore(score);
                    dy.setName(play.getElementsByClass("s1").text());
                    dy.setActor(play.getElementsByClass("star").text());
                }catch (Exception e) {
                    dy.setName(play.getElementsByClass("s1").text());
                    dy.setActor(play.getElementsByClass("star").text());
                }
            } else {
                try{
                    year = s[4].substring(0, 4);
                    score = s[7].split("<")[0];
                    dy.setScore(score);
                    dy.setName(play.getElementsByClass("s1").text());
                    dy.setActor(play.getElementsByClass("star").text());

                }catch (Exception e){
                    dy.setName(play.getElementsByClass("s1").text());
                    dy.setActor(play.getElementsByClass("star").text());

                }
            }

//            if (actor.length() > 11) {
//                actor = actor.substring(0, 11) + "...";
//            }
//            if (score.equals("")) {
//                score = "暂无";
//            }
//            if (year.length() != 4) {
//                year = "暂无";
//            }
//            if (actor.equals("")) {
//                actor = "暂无";
//            }
            ///LongVideos/player.jsp?href=
            //得到hre中的网页源码，以从中筛选出想要的信息

            Document docu = Jsoup.connect(hre).get();
            Elements btns = docu.getElementsByClass("s-cover");//播放链接在这个id中
            String pu = null;

            String sb = btns.toString();
//				  System.out.println(sb);
//					  PlayerUrl获取该视频的播放链接
            pu = sb.substring(sb.indexOf("href=\"") + 6, sb.indexOf("\" class=\""));
            //System.out.println(name + "\n" + pu);

            //从href网页源码中获取其他信息，如视频的详情，剧集，等等，然后传入播放界面并显示
            //或者只将hre传入player.jsp，在其里面进行这些信息的爬取操作
            dys.add(dy);
            i++;
        }
        //System.out.println(Post(PostUrl,list));
        return dys;
    }
    /**
     * 百度链接实时推送
     * @param PostUrl
     * @param Parameters
     * @return
     */
    public String Post(String PostUrl,List Parameters){
        if(null == PostUrl || null == Parameters || Parameters.size() ==0){
            return null;
        }
        String result="";
        PrintWriter out=null;
        BufferedReader in=null;
        try {
            //建立URL之间的连接
            URLConnection conn=new URL(PostUrl).openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("Host","data.zz.baidu.com");
            conn.setRequestProperty("User-Agent", "curl/7.12.1");
            conn.setRequestProperty("Content-Length", "83");
            conn.setRequestProperty("Content-Type", "text/plain");
            //发送POST请求必须设置如下两行
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //获取conn对应的输出流
            out=new PrintWriter(conn.getOutputStream());
            //发送请求参数
            String param = "";
            for(Object s : Parameters){
                param += s+"\n";
            }
            out.print(param.trim());
            //进行输出流的缓冲
            out.flush();
            //通过BufferedReader输入流来读取Url的响应
            in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line=in.readLine())!= null){
                result += line;
            }

        } catch (Exception e) {
            System.out.println("发送post请求出现异常！"+e);
            e.printStackTrace();
        } finally{
            try{
                if(out != null){
                    out.close();
                }
                if(in!= null){
                    in.close();
                }

            }catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 播放地址
     * @param url
     * @return
     * @throws IOException
     */
    public Dy getPlay(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Dy dy = new Dy();

        Element e = doc.getElementsByClass("top-dianying").get(0);

        Element el = e.getElementsByClass("top-left").get(0);
        Element er = e.getElementsByClass("top-right").get(0);
        Element ec = doc.getElementById("js-guessb");

        dy.setImgUrl(el.getElementsByTag("img").attr("src"));
            String score = er.getElementsByClass("s").text();
            if(score==null || "".equals(score)){
                dy.setScore("推荐");
            }else{
                dy.setScore(score);
            }

            dy.setIntroduce(er.getElementsByClass("item-desc").get(1).text());
            dy.setName(er.getElementsByTag("h1").text());
            dy.setType(er.getElementsByClass("item").get(0).text());
            dy.setActor(er.getElementsByClass("item-actor").text());
            dy.setYear(er.getElementsByClass("item").get(1).text());
            dy.setDirector(er.getElementsByClass("item").get(3).text());
            dy.setRegion(er.getElementsByClass("item").get(3).text());
//            System.out.println(s.getElementsByClass("item").get(1).text());
//            System.out.println(s.getElementsByClass("item").get(2).text());
//            System.out.println(s.getElementsByClass("item").get(3).text());
//            System.out.println(s.getElementsByClass("item").get(4).text());
            dy.setOnePlay(er.getElementsByClass("js-site-btn").get(0).attr("href"));
            Elements bes = er.getElementsByClass("ea-site");
            List<Hrefs> Hrefs = new ArrayList<Hrefs>();
            for (Element be: bes){
                Hrefs hrefs = new Hrefs();
                hrefs.setName(be.text());
                hrefs.setUrl(be.attr("href"));
                Hrefs.add(hrefs);
            }
            dy.setHrefs(Hrefs);
            List<Dy> dys = new ArrayList<Dy>();
            //System.out.println(ec);
            for(Element c: ec.getElementsByTag("li")){
                Dy d = new Dy();
                d.setName(c.getElementsByTag("a").get(1).text());
                d.setImgUrl(c.getElementsByTag("img").attr("data-src"));
                d.setUrl(c.getElementsByTag("a").get(0).attr("href"));
                dys.add(d);
            }
            dy.setDys(dys);
        return dy;
    }

    /**
     * 轮播
     * @return
     * @throws IOException
     */
    public List<WheelPlanting> getWheelPlanting() throws IOException {
        String url = "https://www.360kan.com/dianying/index.html";
        Document doc = Jsoup.connect(url).get();
        Elements es = doc.getElementsByClass("b-topslidernew-btn");
        Elements _es = doc.getElementsByClass("b-topslidernew-item");
        List<WheelPlanting> wheelPlantings = new ArrayList<WheelPlanting>();
        int i=0;
            for (Element e: es){
                WheelPlanting wheelPlanting = new WheelPlanting();
                String name = e.getElementsByClass("b-topslidernew-tit").get(0).text();
                String title = e.getElementsByClass("b-topslidernew-desc").get(0).text();


                String _url = e.getElementsByTag("a").attr("href");
                String imgUrl = _es.get(i).getElementsByTag("span").attr("style");
                String _imgUrl = imgUrl.substring(21, imgUrl.indexOf(");"));
                wheelPlanting.setImgUrl(_imgUrl);
                int m = _url.indexOf("/m");
                if(m!=-1){
                    wheelPlanting.setUrl(_url.substring(m, _url.indexOf("html")+4));
                }
                wheelPlanting.setTitle(title);
                wheelPlanting.setName(name);
                wheelPlantings.add(wheelPlanting);
                ++i;
            }
        return wheelPlantings;
    }

    /**
     * 电影排行
     * @return
     * @throws IOException
     */
    public List<Object> getRanking() throws IOException {
        String url = "https://www.360kan.com/dianying/index.html";
        Document doc = Jsoup.connect(url).get();
        Elements es = doc.getElementsByClass("js-view");
        List<Object> dys = new ArrayList<Object>();
        int i=1;
        for (Element e: es){
            List<Dy> _dys = new ArrayList<Dy>();
            Elements _es = e.getElementsByTag("li");
            for(Element _e: _es){
                if(i==8){
                    continue;
                }
                Dy dy = new Dy();
                dy.setName(_e.attr("title"));
                String _url = _e.getElementsByTag("a").get(0).attr("href");
                int m = _url.indexOf("/m");
                if(m!=-1){
                    dy.setUrl(_url.substring(m, _url.indexOf("html")+4));
                }
                String img = null;
                try{
                    img = _e.getElementsByTag("img").attr("data-src");
                }catch (Exception Ex){

                }
                dy.setImgUrl(img);
                dy.setActor(_e.getElementsByClass("w-newfigure-desc").get(0).text());
                dy.setIntroduce(_e.getElementsByClass("w-newfigure-desc").get(0).text());
                dys.add(dy);
                i++;
            }
       }
        return dys;
    }

    /**
     * 电视剧
     * @param tvurl
     * @return
     */
    public List<Dy> getTv(String tvurl) throws IOException {
        int i=1;
        Document doc = Jsoup.connect(tvurl).get();
        Elements es = doc.getElementsByClass("js-tongjic");
        List<Dy> dys = new ArrayList<Dy>();
        for(Element e: es){
            if(i==13){
                continue;
            }
            Dy dy = new Dy();
            dy.setUrl("https://www.360kan.com"+e.getElementsByTag("a").get(0).attr("href"));
            dy.setName(e.getElementsByClass("s1").get(0).text());
            dy.setScore(e.getElementsByClass("hint").get(0).text());
            dy.setActor(e.getElementsByClass("star").get(0).text());
            dy.setImgUrl(e.getElementsByTag("img").get(0).attr("src"));
            dys.add(dy);
            i++;
        }
        return dys;
    }

    public Dy getTvPlay(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements es = doc.getElementById("js-site-wrap").getElementsByClass("num-tab-main");
        Element e = null;
        if(es.size()==2){
            e = es.get(1);
        }else{
            e = es.get(0);
        }
        Elements _es = e.getElementsByTag("a");
        Dy dy = new Dy();
        List<Hrefs> hrefs = new ArrayList<Hrefs>();
        for(Element el: _es){
            Hrefs href = new Hrefs();
            href.setUrl(el.attr("href"));
            href.setName(el.attr("data-num"));
            hrefs.add(href);
        }
            dy.setHrefs(hrefs);
        return dy;
    }

    public String getDb(String dburl) {
        String dbs = HttpClient.doGet(dburl);
        return dbs;
    }

    public ArrayList<Dy> dySearch(String url) throws IOException {
        Document doc=Jsoup.connect(url).get();
//		System.out.println("doc"+doc);
        Elements es=doc.getElementsByClass("js-longitem");
//		System.out.println(es);
        ArrayList<Dy> dys=new ArrayList<Dy>();
        for(Element s:es){
//			System.out.println(s);
            String ss=s.toString();
            //视频详情网址
            String uu=s.getElementsByClass("m-mainpic").get(0).getElementsByTag("a").get(0).attr("href");
            //视频图片
            String img=s.getElementsByClass("m-mainpic").get(0).getElementsByTag("img").get(0).attr("src");
            //视频名
            String name=s.getElementsByClass("m-mainpic").get(0).getElementsByTag("a").get(0).attr("title");
            Dy dy=new Dy();
            dy.setUrl(uu.substring(uu.indexOf("/m"), uu.indexOf("html")+4));
            dy.setImgUrl(img);
            dy.setName(name);
            dy.setIntroduce(s.getElementsByTag("p").get(0).text());
            dys.add(dy);
        }
        return dys;
    }

    public List<Dy> getZy(String tvurl) throws IOException {
        Document doc = Jsoup.connect(tvurl).get();
        Elements es = doc.getElementsByClass("item");
        ArrayList<Dy> dys=new ArrayList<Dy>();
        int i=1;
        for(Element s:es){
            if(i>12){
                continue;
            }
            //System.out.println(s.getElementsByTag("a"));

            //视频详情网址
            String uu = s.getElementsByTag("a").attr("href");
            //视频图片
            String img = s.getElementsByTag("img").attr("src");
            //视频名
            String name = s.getElementsByClass("s1").text();
            String score = s.getElementsByClass("hint").text();
            String actor = s.getElementsByClass("star").text();
            Dy dy=new Dy();
            dy.setUrl(uu);
            dy.setImgUrl(img);
            dy.setName(name);
            dy.setScore(score);
            dy.setActor(actor);
            dy.setIntroduce(s.getElementsByTag("p").text());
            dys.add(dy);
            i++;
        }
        return dys;
    }

    public Dy zyplay(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Dy dy = new Dy();
        Elements es = doc.getElementsByClass("w-newfigure");
        List<Hrefs> Hrefs = new ArrayList<Hrefs>();
        for(Element s:es){

            //dy.setScore(s.getElementsByClass("s").text());
            //dy.setIntroduce(s.getElementsByClass("item-desc").get(1).text());
            //dy.setName(s.getElementsByTag("h1").text());
            //dy.setType(s.getElementsByClass("item").get(0).text());

//            System.out.println(s.getElementsByClass("item").get(1).text());
//            System.out.println(s.getElementsByClass("item").get(2).text());
//            System.out.println(s.getElementsByClass("item").get(3).text());
//            System.out.println(s.getElementsByClass("item").get(4).text());


            Hrefs hrefs = new Hrefs();
            hrefs.setName(s.attr("title"));
            hrefs.setUrl(s.getElementsByTag("a").get(0).attr("href"));
            Hrefs.add(hrefs);


        }
        dy.setHrefs(Hrefs);
        return dy;
    }
    public List<Dy> danying(String type) throws IOException {
        String url = null;

        if(type==null || "".equals(type)){
            url = "http://www.360kan.com/rank/dianying";
        }else{
            url="http://www.360kan.com/rank/dianying?type="+type;
        }
        Document doc = Jsoup.connect(url).get();
        List<Dy> dys = new ArrayList<Dy>();
        Elements es = doc.getElementsByClass("m-content");
        for(Element e: es){
            Dy dy = new Dy();
            String _url = e.getElementsByClass("m-item-img").get(0).getElementsByTag("a").attr("href");
            dy.setUrl(_url.substring(_url.indexOf("/m"), _url.indexOf("html")+4));
            dy.setImgUrl(e.getElementsByClass("m-item-img").get(0).getElementsByTag("img").attr("data-src"));
            dy.setYear(e.getElementsByClass("m-item-playcount").get(0).getElementsByTag("span").text());
            dy.setName(e.getElementsByClass("m-item-info").get(0).getElementsByTag("a").text());
            dys.add(dy);
        }
        return dys;
    }
}
