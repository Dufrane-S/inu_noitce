package com.dufrane.inu_noitce.bbs;

import java.util.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Crawler {
/**
 * 크롤링하여 db에 저장까지하는 함수
 * 최초 db확보용
 * @param target 크롤링할 사이트 0 = 전체, 1 = 학교, 2 = 기숙사, 3 = 국제교류원, 4 = 컴공
 * @param pages 크롤링할 페이지
 * */
    private final String scSeedUrlFront = "fnct1|@@|%2Fbbs%2Finu%2F2006%2FartclList.do%3Fpage%3D";
    private final String scSeedUrlRear = "%26srchColumn%3D%26srchWrd%3D%26bbsClSeq%3D%26bbsOpenWrdSeq%3D%26rgsBgndeStr%3D%26rgsEnddeStr%3D%26isViewMine%3Dfalse%26";
    private final String scSeedUrlHead = "https://inu.ac.kr/inu/1534/subview.do?enc=";
    public void crawl(int target, int pages){
    }

    public String to64(String seed){
        byte[] seedByte = seed.getBytes();
        Base64.Encoder encoder = Base64.getEncoder();

        return new String(encoder.encode(seedByte));
    }

    public void writeInDB(Element element){

    }
}
