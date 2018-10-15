package com.codacy.client.bitbucket.v2.service

import com.codacy.client.bitbucket.v2
import com.codacy.client.bitbucket.v2.PullRequest
import com.codacy.client.client.{BitbucketClient, Request, RequestResponse}
import play.api.libs.json._

class PullRequestServices(client: BitbucketClient) {

  /*
   * Gets the list of a repository pull requests
   *
   * States: OPEN | MERGED | DECLINED
   *
   */
  def getPullRequests(owner: String, repository: String, states: Seq[String] = Seq("OPEN")): RequestResponse[Seq[PullRequest]] = {
    val url = s"https://bitbucket.org/api/2.0/repositories/$owner/$repository/pullrequests?pagelen=50&state=${states.mkString("&state=")}"

    client.executePaginated(Request(url, classOf[Seq[PullRequest]]))
  }

  /*
   * Gets the list of commits of a pull request
   *
   */
  def getPullRequestCommits(owner: String, repository: String, prId: Long): RequestResponse[Seq[v2.SimpleCommit]] = {
    val url = s"https://bitbucket.org/api/2.0/repositories/$owner/$repository/pullrequests/$prId/commits?pagelen=100"

    client.executePaginated(Request(url, classOf[Seq[v2.SimpleCommit]]))
  }

  def create(owner: String, repository: String, title: String, sourceBranch: String, destinationBranch: String): RequestResponse[JsObject] = {
    val url = s"https://bitbucket.org/api/2.0/repositories/$owner/$repository/pullrequests"

    val payload = Json.obj(
      "title" -> title,
      "source" -> Json.obj(
        "branch" -> Json.obj(
          "name" -> sourceBranch
        )
      ),
      "destination" -> Json.obj(
        "branch" -> Json.obj(
          "name" -> destinationBranch
        )
      )
    )

    client.postJson(Request(url, classOf[JsObject]), payload)
  }

  def postApprove(owner: String, repository: String, prId: Long): RequestResponse[JsObject] = {
    val url = s"https://bitbucket.org/api/2.0/repositories/$owner/$repository/pullrequests/$prId/approve"
    client.postJson(Request(url, classOf[JsObject]), JsNull)
  }

  def deleteApprove(owner: String, repository: String, prId: Long): RequestResponse[Boolean] = {
    val url = s"https://bitbucket.org/api/2.0/repositories/$owner/$repository/pullrequests/$prId/approve"
    client.delete(url)
  }

  def merge(owner: String, repository: String, prId: Long): RequestResponse[JsObject] = {
    val url = s"https://bitbucket.org/api/2.0/repositories/$owner/$repository/pullrequests/$prId/merge"
    client.postJson(Request(url, classOf[JsObject]), JsNull)
  }

  def decline(owner: String, repository: String, prId: Long): RequestResponse[JsObject] = {
    val url = s"https://bitbucket.org/api/2.0/repositories/$owner/$repository/pullrequests/$prId/decline"
    client.postJson(Request(url, classOf[JsObject]), JsNull)
  }

  def getPullRequestsReviewers(owner: String, repository: String, prId: Long): RequestResponse[v2.PullRequestReviewers] = {
    val url = s"https://bitbucket.org/api/2.0/repositories/$owner/$repository/pullrequests/$prId"

    client.execute(Request(url, classOf[v2.PullRequestReviewers]))
  }

}