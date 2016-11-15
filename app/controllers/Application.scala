package controllers


import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}
import play.api.mvc._
import org.apache.spark.{SparkConf, SparkContext}
import play.twirl.api.Html


object Application extends Controller {

  /*  def index = Action {
      Ok(views.html.index("Your new application is ready."))
    }*/


  def word2vec = Action {

    val conf = new SparkConf().setAppName("Word2VecExample")
      .setMaster("local")
    val sc = new SparkContext(conf)

    val input = sc.textFile("app/resources/testData.tsv").map(line => line.split(" ").toSeq)
    println(input)

    val word2vec = new Word2Vec()
    //    val word2vecmodel = new Word2VecModel()

    val model = word2vec.fit(input)

    val synonyms = model.findSynonyms("1", 5)

    val synonyms2 = model.findSynonyms("accurate", 5)

    var html1: String = "<ul class=\"list-group\">"

    var html2: String = "<ul class=\"list-group\">"

    for ((synonym, cosineSimilarity) <- synonyms) {
      println(raw"$synonym $cosineSimilarity")
      html1 = html1 + "<li class=\"list-group-item\">"
      html1 = html1 + s"$synonym $cosineSimilarity"
      html1 = html1 + "</li>"
    }
    html1 = html1 + "</ul>"

    for ((synonym, cosineSimilarity) <- synonyms2) {
      println(raw"$synonym $cosineSimilarity")
      html2 = html2 + "<li class=\"list-group-item\">"
      html2 = html2 + s"$synonym $cosineSimilarity"
      html2 = html2 + "</li>"
    }

    html2 = html2 + "</ul>"

    html1 = html1 + html2

    println(html1)

    sc.stop()

    Ok(views.html.index(Html.apply(html1)))
  }

}