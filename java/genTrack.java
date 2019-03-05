import javax.vecmath.*;

public class EntryTest {
    static Vector3f p1 = new Vector3f(152.52f,133.13f,72.9f);
    static Vector3f p2 = new Vector3f(156.17f,131.36f,69.78f);
    static Vector3f p3 = new Vector3f(152.91f,131.99f,66.43f);

    static Vector3f q1 = new Vector3f(155.77f,83.46f,74.3f);
    static Vector3f q2 = new Vector3f(154.71f,83.45f,71.05f);
    static Vector3f q3 = new Vector3f(153.82f,83.61f,66.98f);

    static Vector3f verticalOffset = new Vector3f(0,0,5.7735f);
    static float cameraPitch = (float)(Math.PI/6.0);
    static float flyOffset = 10.0f;

    public static Vector3f lineIntersection(Vector3f planePoint, Vector3f planeNormal, Vector3f linePoint, Vector3f lineDirection) {
        if (planeNormal.dot(lineDirection) == 0) {
            return null;
        }
        float t = (planeNormal.dot(planePoint) - planeNormal.dot(linePoint)) / planeNormal.dot(lineDirection);
        lineDirection.scale(t);
        linePoint.add(lineDirection);
        return linePoint;
    }

    public static void main(String[] args) {
        Vector3f linePoint = new Vector3f(p3);
        Vector3f PQ = new Vector3f(q3);
        //Vector3f PQ = new Vector3f(0,1,0);
        PQ.sub(p3);
        //
        Vector3f PQ1= new Vector3f(PQ);
        System.out.println("PQ1 "+ PQ1);
        PQ1.normalize();
        PQ1.scale(flyOffset);//10米
        System.out.println("PQ1 scale "+ PQ1);
        Matrix4f mtx = new Matrix4f();
        //
        mtx.rotZ((float)(Math.PI/2));
        mtx.transform(PQ1);
        Vector3f planNormal = new Vector3f(PQ1);
        System.out.println("planNormal "+ planNormal);
        Vector3f planPoint = new Vector3f(PQ1);
        planPoint.add(linePoint);
        System.out.println("planPoint "+ planPoint);

        Vector3f lineDirection = new Vector3f(planNormal);
        lineDirection.setZ(lineDirection.z + (float)Math.atan(cameraPitch)*flyOffset);
        System.out.println("lineDirection "+ lineDirection);
        Vector3f r = lineIntersection(planPoint,planNormal, linePoint, lineDirection);
        System.out.println("interserct to "+ r);
    }
}
