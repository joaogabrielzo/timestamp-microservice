import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.zo._
import com.zo.TimestampActor._
import com.zo.TimestampMicroservice._
import org.joda.time.DateTime
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import spray.json._

class TimestampMicroserviceSpec extends AnyWordSpec
                                with Matchers
                                with ScalatestRouteTest
                                with TimestampJsonProtocol {
    
    "A Timestamp Microservice" should {
        "return the correct datetime for the given unix timestamp" in {
            Get("/api/timestamp/1443785500") ~> timestampRoute ~> check {
                status shouldBe StatusCodes.OK
                
                responseAs[Timestamp] shouldBe Timestamp("1443785500", new DateTime(1443785500 * 1000L))
            }
        }
        
        "return the correct unix timestamp for the given datetime" in {
            Get("/api/timestamp/2018-03-25") ~> timestampRoute ~> check {
                status shouldBe StatusCodes.OK
                
                responseAs[Timestamp] shouldBe Timestamp("1521946800", new DateTime(1521946800 * 1000L))
            }
        }
    }
    
}
