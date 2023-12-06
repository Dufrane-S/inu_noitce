package com.dufrane.inu_noitce.bbs;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

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
    private final String scSeedUrlFront = "fnct1|@@|%2Fbbs%2Finu%2F2006%2FartclList.do%3Fpage%3D";
    private final String scSeedUrlRear = "%26srchColumn%3D%26srchWrd%3D%26bbsClSeq%3D%26bbsOpenWrdSeq%3D%26rgsBgndeStr%3D%26rgsEnddeStr%3D%26isViewMine%3Dfalse%26";
    private final String scSeedUrlHead = "https://inu.ac.kr/inu/1534/subview.do?enc=";


    String cseSeedUrl="https://cse.inu.ac.kr/isis/3519/subview.do?enc=Zm5jdDF8QEB8JTJGYmJzJTJGaXNpcyUyRjM3NiUyRmFydGNsTGlzdC5kbyUzRnBhZ2UlM0QxJTI2c3JjaENvbHVtbiUzRCUyNnNyY2hXcmQlM0QlMjZiYnNDbFNlcSUzRCUyNmJic09wZW5XcmRTZXElM0QlMjZyZ3NCZ25kZVN0ciUzRCUyNnJnc0VuZGRlU3RyJTNEJTI2aXNWaWV3TWluZSUzRGZhbHNlJTI2";



    public List<Elements> crawl(String target, int pages) {
        String seedUrlFront="";
        String seedUrlRear="";
        String seedUrlHead="";
        String urlHead="";
        List<Elements> result = null;
        if (target.equals("inu")) {
            seedUrlFront = this.scSeedUrlFront;
            seedUrlRear = this.scSeedUrlRear;
            seedUrlHead = this.scSeedUrlHead;
        }
        for (int i =0; i<pages; i++){
            String seedUrl = seedUrlFront+Integer.toString(i)+seedUrlRear;
            String URL= seedUrlHead+to64(seedUrl);
            Document doc;
            try {
                doc = Jsoup.connect(URL).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Element table = doc.selectFirst("table[class=board-table horizon1]");
            Element tbody = table.getElementsByTag("tbody").first();
            result.add(tbody.select("tr").not(".notice "));
            try {
                Thread.sleep(1000); //1초 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
