package com.codacy.client.bitbucket.v2.service

import com.codacy.client.bitbucket.v2.User
import com.codacy.client.client.{BitbucketClient, Request, RequestResponse}

class TeamServices(client: BitbucketClient) {

  def list: RequestResponse[Seq[User]] = {
    client.executePaginated(Request(s"https://bitbucket.org/api/2.0/teams", classOf[Seq[User]]))
  }

}