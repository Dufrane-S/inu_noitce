package com.dufrane.inu_noitce.bbs;


import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
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

        String URL = "https://inu.ac.kr/inu/1534/subview.do?enc=Zm5jdDF8QEB8JTJGYmJzJTJGaW51JTJGMjAwNiUyRmFydGNsTGlzdC5kbyUzRnBhZ2UlM0QxJTI2c3JjaENvbHVtbiUzRCUyNnNyY2hXcmQlM0QlMjZiYnNDbFNlcSUzRCUyNmJic09wZW5XcmRTZXElM0QlMjZyZ3NCZ25kZVN0ciUzRCUyNnJnc0VuZGRlU3RyJTNEJTI2aXNWaWV3TWluZSUzRGZhbHNlJTI2";
        Document doc;
        try {
            doc = Jsoup.connect(URL).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Element table = doc.selectFirst("table[class=board-table horizon1]");
        Element tbody = table.getElementsByTag("tbody").first();
        Elements objects = tbody.select("tr").not(".notice ");
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


                //request.setTitle(object.getElementsByTag("strong").first().text());
                //request.setCategory1("학교");
                //request.setCategory2(object.getElementsByClass("td-category").text());
                //request.setWriter(object.getElementsByClass("td-write").text());
                //request.setDate(object.getElementsByClass("td-date").text());
                //request.setUrl("https://www.inu.ac.kr" + object.getElementsByTag("a").attr("href"));
                //request.setOrg_num(Long.parseLong(object.getElementsByClass("td-num").text().strip()));
                articleService.save(request);

        }



        return HttpStatus.OK;
    }

    @RequestMapping("/search/{title}")
    public ResponseEntity<List<ArticleResponse>> searchByTitle(@PathVariable("title")String title){
        return ResponseEntity.ok().body(articleService.searchTitle(title));
    }


}
