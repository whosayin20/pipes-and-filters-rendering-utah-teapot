package at.fhv.sysarch.lab3.pipeline.filter.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PullScreenSpaceTransformation <I extends Pair<Face, Color>, O extends Pair<Face, Color>> implements IPullFilter<Pair<Face, Color>, Pair<Face, Color>>  {

    private Mat4 screenSpaceMatrix;
    private PullPipe<Pair<Face, Color>> pipePredecessor;

    @Override
    public void setPipePredecessor(PullPipe<Pair<Face, Color>> pipe) {
        this.pipePredecessor = pipe;
    }

    public PullScreenSpaceTransformation(Mat4 screenSpaceMatrix){
        this.screenSpaceMatrix = screenSpaceMatrix;
    }

    @Override
    public Pair<Face, Color> read() {
        if(hasNext()){
            Pair<Face, Color> pair = pipePredecessor.read();
            Face face = pair.fst();
            float v1w = face.getV1().getW();
            float v2w = face.getV2().getW();
            float v3w = face.getV3().getW();
            float n1w = face.getN1().getW();
            float n2w = face.getN2().getW();
            float n3w = face.getN3().getW();

            //V1
            float newV1X = face.getV1().getX() / v1w;
            float newV1Y = face.getV1().getY() / v1w;
            float newV1Z = face.getV1().getZ() / v1w;

            Vec4 newV1 = new Vec4(newV1X, newV1Y, newV1Z,0);

            //V2
            float newV2X = face.getV2().getX() / v2w;
            float newV2Y = face.getV2().getY() / v2w;
            float newV2Z = face.getV2().getZ() / v2w;

            Vec4 newV2 = new Vec4(newV2X, newV2Y, newV2Z, 0);

            //V3
            float newV3X = face.getV3().getX() / v3w;
            float newV3Y = face.getV3().getY() / v3w;
            float newV3Z = face.getV3().getZ() / v3w;

            Vec4 newV3 = new Vec4(newV3X, newV3Y, newV3Z, 0);

            //N1
            float newN1X = face.getN1().getX() / n1w;
            float newN1Y = face.getN1().getY() / n1w;
            float newN1Z = face.getN1().getZ() / n1w;

            Vec4 newN1 = new Vec4(newN1X, newN1Y, newN1Z, 0);

            //N2
            float newN2X = face.getN2().getX() / n2w;
            float newN2Y = face.getN2().getY() / n2w;
            float newN2Z = face.getN2().getZ() / n2w;

            Vec4 newN2 = new Vec4(newN2X, newN2Y, newN2Z, 0);

            //N3
            float newN3X = face.getN3().getX() / n3w;
            float newN3Y = face.getN3().getY() / n3w;
            float newN3Z = face.getN3().getZ() / n3w;

            Vec4 newN3 = new Vec4(newN3X, newN3Y, newN3Z, 0);

            Vec4 a = screenSpaceMatrix.multiply(newV1);
            Vec4 b = screenSpaceMatrix.multiply(newV2);
            Vec4 c = screenSpaceMatrix.multiply(newV3);
            Vec4 d = screenSpaceMatrix.multiply(newN1);
            Vec4 e = screenSpaceMatrix.multiply(newN2);
            Vec4 f = screenSpaceMatrix.multiply(newN3);

            Face newFace = new Face(a, b, c, d, e, f);

            return new Pair(newFace, pair.snd());
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return pipePredecessor.hasNext();
    }
}
