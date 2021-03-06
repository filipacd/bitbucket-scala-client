package play.api.libs.ws

import akka.util.ByteString
import com.fasterxml.jackson.databind.{ JsonNode, ObjectMapper }
import play.api.libs.json.{ JsValue, Json }

trait JsonBodyWritables {

  /**
   * Creates an InMemoryBody with "application/json" content type, using the static ObjectMapper.
   */
  implicit val writeableOf_JsValue: BodyWritable[JsValue] = {
    BodyWritable(a => InMemoryBody(ByteString.fromString(Json.stringify(a))), "application/json")
  }

  def body(objectMapper: ObjectMapper): BodyWritable[JsonNode] = BodyWritable(json =>
    InMemoryBody(ByteString.fromArrayUnsafe(objectMapper.writeValueAsBytes(json))), "application/json")
}

object JsonBodyWritables extends JsonBodyWritables
