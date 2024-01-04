package com.dufrane.inu_noitce.bbs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Crawler {
/**
 * 크롤링하여 db에 저장까지하는 함수
 * 최초 db확보용
 * @param target 크롤링할 사이트 full0 = 전체, inu = 학교, dorm = 기숙사, int = 국제교류원, cse = 컴공, lib = 도서관
 * @param pages 크롤링할 페이지
 * */
    private String inuSeedUrlFront = "fnct1|@@|%2Fbbs%2Finu%2F2006%2FartclList.do%3Fpage%3D";
    private String inuSeedUrlRear = "%26srchColumn%3D%26srchWrd%3D%26bbsClSeq%3D%26bbsOpenWrdSeq%3D%26rgsBgndeStr%3D%26rgsEnddeStr%3D%26isViewMine%3Dfalse%26";
    private String inuSeedUrlHead = "https://inu.ac.kr/inu/1534/subview.do?enc=";

    private String cseSeedUrlFront ="fnct1|@@|%2Fbbs%2Fisis%2F376%2FartclList.do%3Fpage%3D";
    private String cseSeedUrlRear = "%26srchColumn%3D%26srchWrd%3D%26bbsClSeq%3D%26bbsOpenWrdSeq%3D%26rgsBgndeStr%3D%26rgsEnddeStr%3D%26isViewMine%3Dfalse%26";
    private String cseSeedUrlHead ="https://cse.inu.ac.kr/isis/3519/subview.do?enc=";;

    private String dormSeedUrlFront ="fnct1|@@|%2Fbbs%2Fdorm%2F1521%2FartclList.do%3Fpage%3D";
    private String dormSeedUrlRear = "%26srchColumn%3D%26srchWrd%3D%26bbsClSeq%3D%26bbsOpenWrdSeq%3D%26rgsBgndeStr%3D%26rgsEnddeStr%3D%26isViewMine%3Dfalse%26";
    private String dormSeedUrlHead ="https://dorm.inu.ac.kr/dorm/6528/subview.do?enc=";

    private String intSeedUrlFront ="fnct1|@@|%2Fbbs%2Fglobal%2F1412%2FartclList.do%3Fpage%3D";
    private String intSeedUrlRear = "%26srchColumn%3D%26srchWrd%3D%26bbsClSeq%3D%26bbsOpenWrdSeq%3D%26rgsBgndeStr%3D%26rgsEnddeStr%3D%26isViewMine%3Dfalse%26";
    private String intSeedUrlHead ="https://www.inu.ac.kr/global/6018/subview.do?enc=";

    public List<AddArticleRequest> crawl(String target, int pages) {
        String seedUrlFront="";
        String seedUrlRear="";
        String seedUrlHead="";
        String category1=target;
        String urlHead="";
        List<AddArticleRequest> result = new ArrayList<>() {
        };
        AddArticleRequest request=null;
        if (target.equals("inu")) {
            seedUrlFront = this.inuSeedUrlFront;
            seedUrlRear = this.inuSeedUrlRear;
            seedUrlHead = this.inuSeedUrlHead;
            urlHead="https://www.inu.ac.kr";
        }else if (target.equals("cse")){
            seedUrlFront = this.cseSeedUrlFront;
            seedUrlRear = this.cseSeedUrlRear;
            seedUrlHead = this.cseSeedUrlHead;
            urlHead="https://cse.inu.ac.kr/";
        }else if (target.equals("dorm")){
            seedUrlFront = this.dormSeedUrlFront;
            seedUrlRear = this.dormSeedUrlRear;
            seedUrlHead = this.dormSeedUrlHead;
            urlHead="https://dorm.inu.ac.kr";
        } else if (target.equals("int")) {
            seedUrlFront = this.intSeedUrlFront;
            seedUrlRear = this.intSeedUrlRear;
            seedUrlHead = this.intSeedUrlHead;
            urlHead="https://www.inu.ac.kr";
        }
        for (int i =1; i<pages; i++){
            String seedUrl = seedUrlFront+ i +seedUrlRear;
            String URL= seedUrlHead+to64(seedUrl);

            Document doc;
            try {
                doc = Jsoup.connect(URL).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Element table = doc.selectFirst("table[class=board-table horizon1]");
            Element tbody = table.getElementsByTag("tbody").first();
            Elements elements=(tbody.select("tr").not(".notice "));
            if (target.equals("cse")){
                for (Element object : elements){
                    request=  AddArticleRequest.builder()
                            .title(object.getElementsByTag("strong").first().text())
                            .org_num(Long.parseLong(object.getElementsByClass("td-num").text().strip()))
                            .url(urlHead + object.getElementsByTag("a").attr("href"))
                            .category1(target)
                            .writer(object.getElementsByClass("td-write").text())
                            .category2("[일반]")
                            .date(java.sql.Date.valueOf(object.getElementsByClass("td-date").text().replace(".","-")))
                            .org_num(Long.parseLong(object.getElementsByClass("td-num").text().strip())).build();
                    result.add(request);
                }
            } else if (target.equals("int")) {
                for (Element object : elements){
                    request=  AddArticleRequest.builder()
                            .title(object.getElementsByTag("strong").first().text())
                            .org_num(Long.parseLong(object.getElementsByClass("td-num").text().strip()))
                            .url(urlHead + object.getElementsByTag("a").attr("href"))
                            .category1(target)
                            .writer(object.getElementsByClass("td-write").text())
                            .category2("[일반]")
                            .date(java.sql.Date.valueOf(object.getElementsByClass("td-date").text().replace(".","-")))
                            .org_num(Long.parseLong(object.getElementsByClass("td-num").text().strip())).build();
                    result.add(request);
                }
            }else{
                for (Element object : elements){
                    request=  AddArticleRequest.builder()
                            .title(object.getElementsByTag("strong").first().text())
                            .org_num(Long.parseLong(object.getElementsByClass("td-num").text().strip()))
                            .url(urlHead + object.getElementsByTag("a").attr("href"))
                            .category1(target)
                            .writer(object.getElementsByClass("td-write").text())
                            .category2(object.getElementsByClass("td-category").text())
                            .date(java.sql.Date.valueOf(object.getElementsByClass("td-date").text().replace(".","-")))
                            .org_num(Long.parseLong(object.getElementsByClass("td-num").text().strip())).build();
                    result.add(request);
                }
            }

            try {
                Thread.sleep(1000); //1초 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }





        return result;
    }


    public List<AddArticleRequest> crawl_lib(int count){
        List<AddArticleRequest> result = new ArrayList<>() {
        };
        String Lib_URL="https://lib.inu.ac.kr/pyxis-api/1/bulletin-boards/1/bulletins?offset=0&max="+count+"&nameOption=part&isSeq=false&onlyWriter=false";
        String str = null;
        try{
            str = Jsoup.connect(Lib_URL).header("Accept", "text/javascript")
                    .ignoreContentType(true).execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = null;
        try{
            jsonObject = (JSONObject) jsonParser.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        jsonObject = (JSONObject) jsonObject.get("data");
        JSONArray jsonArray = (JSONArray) jsonObject.get("list");
        jsonObject = (JSONObject) jsonArray.get(0);
        for (Object object : jsonArray){
            AddArticleRequest request =null;
            JSONObject jsonObject1 = (JSONObject) object;
            String category2 = null;
            if (jsonObject1.get("bulletinCategory")==null){
                category2 = ("일반");
            }
            else {
                JSONObject jsonObject2 = (JSONObject) jsonObject1.get("bulletinCategory");
                category2 = ((String) jsonObject2.get("name"));
            }
            String date = (String)jsonObject1.get("lastUpdated");
            date= date.split(" ")[0];
            request=  AddArticleRequest.builder()
                    .title((String) jsonObject1.get("title"))
                    .org_num((Long) jsonObject1.get("seqNo"))
                    .url("https://lib.inu.ac.kr/library-guide/news/notice/"+jsonObject1.get("id"))
                    .category1("도서관")
                    .writer((String)jsonObject1.get("writer"))
                    .category2(category2)
                    .date(java.sql.Date.valueOf(date))
                    .org_num((Long)jsonObject1.get("seqNo")).build();
            result.add(request);

            System.out.println(jsonObject1.get("title"));
            System.out.println(jsonObject1.get("seqNo"));
            System.out.println(jsonObject1.get("lastUpdated"));
            System.out.println("https://lib.inu.ac.kr/library-guide/news/notice/"+jsonObject1.get("id"));
            System.out.println(jsonObject1.get("writer"));


        }
        return result;
    }


    public static String to64(String seed){
        byte[] seedByte = seed.getBytes();
        Base64.Encoder encoder = Base64.getEncoder();

        return new String(encoder.encode(seedByte));
    }

    public void writeInDB(Element element){

    }
}
