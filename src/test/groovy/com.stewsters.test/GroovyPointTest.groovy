import com.stewsters.util.math.Point2i
import com.stewsters.util.math.Point3i
import groovy.transform.CompileStatic
import org.junit.Test

@CompileStatic
class GroovyPointTest extends GroovyTestCase {

    @Test
    void testOperationOverload2d() {

        Point2i initial = new Point2i(1, 1)
        Point2i second = new Point2i(2, 1)

        Point2i last = initial + second

        assert last.x == 3
        assert last.y == 2

        // Test subtraction
        assert initial == last - second

        Point2i mult = second * 2
        assert mult.x == 4
        assert mult.y == 2

        assert second == mult / 2

    }

    @Test
    void testOperationOverload3d() {

        Point3i initial = new Point3i(1, 1, 1)
        Point3i second = new Point3i(2, 1, 1)

        Point3i last = initial + second

        assert last.x == 3
        assert last.y == 2
        assert last.z == 2

        // Test subtraction
        assert initial == last - second

        Point3i mult = second * 2
        assert mult.x == 4
        assert mult.y == 2
        assert mult.z == 2

        assert second == mult / 2

    }


}