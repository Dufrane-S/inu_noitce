package com.dufrane.inu_noitce.bbs;


import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleController {
    private final ArticleService articleService;
    @RequestMapping("/article/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id){
        Article article = articleService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @RequestMapping("/articles")
    @CrossOrigin
    public ResponseEntity<List<ArticleResponse>> findAllArticles(){
        List<ArticleResponse> articles = articleService.findall()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);

    }

    @RequestMapping("articles_update_school")
    //@RequestBody로 요청 본문 값 매핑
    public HttpStatus addArticle(AddArticleRequest request){
        //https://okky.kr/questions/186517
        //https://ktko.tistory.com/entry/JAVA-BASE64-%EC%9D%B8%EC%BD%94%EB%94%A9-%EB%94%94%EC%BD%94%EB%94%A9%ED%95%98%EA%B8%B0
        //https://wepplication.github.io/tools/encoder/
        //https://shinb.tistory.com/398
        Base64.Encoder encoder = Base64.getEncoder();
        for(int i = 1; i<10; i++){
            //URL을 변환하는 부분
            String seedURL= "fnct1|@@|%2Fbbs%2Finu%2F2006%2FartclList.do%3Fpage%3D"+ Integer.toString(i) +"%26srchColumn%3D%26srchWrd%3D%26bbsClSeq%3D%26bbsOpenWrdSeq%3D%26rgsBgndeStr%3D%26rgsEnddeStr%3D%26isViewMine%3Dfalse%26";
            byte[] seedByte = seedURL.getBytes();
            seedURL = new String(encoder.encode(seedByte));
            String URL = "https://inu.ac.kr/inu/1534/subview.do?enc="+ seedURL;

            //크롤링하는 부분
            Document doc;
            try {
                doc = Jsoup.connect(URL).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Element table = doc.selectFirst("table[class=board-table horizon1]");
            Element tbody = table.getElementsByTag("tbody").first();
            Elements objects = tbody.select("tr").not(".notice ");


            // elements를 받아 실제로 db에 넣는 부분
            for (Element object : objects) {
                request = AddArticleRequest.builder()
                        .title(object.getElementsByTag("strong").first().text())
                        .org_num(Long.parseLong(object.getElementsByClass("td-num").text().strip()))
                        .url("https://www.inu.ac.kr" + object.getElementsByTag("a").attr("href"))
                        .category1("학교")
                        .writer(object.getElementsByClass("td-write").text())
                        .category2(object.getElementsByClass("td-category").text())
                        .date(object.getElementsByClass("td-date").text())
                        .org_num(Long.parseLong(object.getElementsByClass("td-num").text().strip())).build();
                articleService.save(request);
                try {
                    Thread.sleep(1000); //1초 대기
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        return HttpStatus.OK;
    }

    @CrossOrigin
    @RequestMapping("/search/page")
    public ResponseEntity searchByTitle(@RequestParam(value = "keyword1")String keyword1,
                                        @RequestParam(value = "keyword2", defaultValue = "")String keyword2 ,
                                        @RequestParam(value = "keyword3", defaultValue = "")String keyword3 ,
                                                       @RequestParam(value = "page", defaultValue = "0")int page){
        Page<Article>result;
        if (keyword2.isBlank()){
            result = this.articleService.searchTitle(keyword1, page);
        } else if (keyword3.isBlank()) {
            result = this.articleService.searchTitle(keyword1,keyword2,page);
        }else{
            result = this.articleService.searchTitle(keyword1,keyword2,keyword3,page);
        }

        return ResponseEntity.ok().body(result);
    }
    @CrossOrigin
    @RequestMapping("articles/page")
        public ResponseEntity<Page<Article>> findArticle(@RequestParam(value = "num",defaultValue = "0")int num){
            Page<Article> paging = this.articleService.getAll(num);
            return ResponseEntity.ok()
                    .body(paging);

    }

}
