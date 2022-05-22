package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipe;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class ScreenSpaceTransformation<I extends Pair<Face, Color>> implements IFilter<I, Pair<Face, Color>> {
    private Pipe<Pair<Face, Color>> pipeSuccessor;

    private Mat4 matrix;

    public ScreenSpaceTransformation(Mat4 viewportTransform) {
        this.matrix = viewportTransform;
    }

    @Override
    public void setPipeSuccessor(Pipe<Pair<Face, Color>> pipe) {
        this.pipeSuccessor = pipe;
    }

    @Override
    public void write(I pair) {
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

        Vec4 newV1 = new Vec4(newV1X, newV1Y, newV1Z, v1w);

        //V2
        float newV2X = face.getV2().getX() / v2w;
        float newV2Y = face.getV2().getY() / v2w;
        float newV2Z = face.getV2().getZ() / v2w;

        Vec4 newV2 = new Vec4(newV2X, newV2Y, newV2Z, v2w);

        //V3
        float newV3X = face.getV3().getX() / v3w;
        float newV3Y = face.getV3().getY() / v3w;
        float newV3Z = face.getV3().getZ() / v3w;

        Vec4 newV3 = new Vec4(newV3X, newV3Y, newV3Z, v3w);

        //N1
        float newN1X = face.getN1().getX() / n1w;
        float newN1Y = face.getN1().getY() / n1w;
        float newN1Z = face.getN1().getZ() / n1w;

        Vec4 newN1 = new Vec4(newN1X, newN1Y, newN1Z, n1w);

        //N2
        float newN2X = face.getN2().getX() / n2w;
        float newN2Y = face.getN2().getY() / n2w;
        float newN2Z = face.getN2().getZ() / n2w;

        Vec4 newN2 = new Vec4(newN2X, newN2Y, newN2Z, n2w);

        //N3
        float newN3X = face.getN3().getX() / n3w;
        float newN3Y = face.getN3().getY() / n3w;
        float newN3Z = face.getN3().getZ() / n3w;

        Vec4 newN3 = new Vec4(newN3X, newN3Y, newN3Z, n3w);

        Vec4 a = matrix.multiply(newV1);
        Vec4 b = matrix.multiply(newV2);
        Vec4 c = matrix.multiply(newV3);
        Vec4 d = matrix.multiply(newN1);
        Vec4 e = matrix.multiply(newN2);
        Vec4 f = matrix.multiply(newN3);

        Face newFace = new Face(a, b, c, d, e, f);

        Pair newPair = new Pair(newFace, pair.snd());

        pipeSuccessor.write(newPair);
    }
}