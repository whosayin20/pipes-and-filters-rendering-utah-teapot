package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipe;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;
import javafx.scene.paint.Color;

public class PerspectiveProj<I extends Pair<Face, Color>> implements IFilter<I, Pair<Face, Color>> {

    private Pipe<Pair<Face, Color>> pipeSuccessor;

    private Mat4 matrix;

    public PerspectiveProj(Mat4 projTransform) {
        this.matrix = projTransform;
    }

    @Override
    public void setPipeSuccessor(Pipe pipe) {
        this.pipeSuccessor = pipe;
    }


    @Override
    public void write(I pair) {
        Face face = pair.fst();
        Color color = pair.snd();
        Vec4 newV1 = matrix.multiply(face.getV1());
        Vec4 newV2 = matrix.multiply(face.getV2());
        Vec4 newV3 = matrix.multiply(face.getV3());
        Vec4 newN1 = matrix.multiply(face.getN1());
        Vec4 newN2 = matrix.multiply(face.getN2());
        Vec4 newN3 = matrix.multiply(face.getN3());

        Face newFace = new Face(newV1, newV2, newV3, newN1, newN2, newN3);
        Pair<Face, Color> newPair = new Pair<>(newFace, color);
        pipeSuccessor.write(newPair);
    }
}
