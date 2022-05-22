package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipe;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class TransFormFace<I extends Face> implements IFilter<I, Face> {

    private Pipe pipeSuccessor;

    private Mat4 matrix;

    @Override
    public void setPipeSuccessor(Pipe pipe) {
        this.pipeSuccessor = pipe;
    }


    public void setTransformationmatrix(Mat4 matrix) {
        this.matrix = matrix;
    }

    @Override
    public void write(I input) {
        Vec4 newV1 = matrix.multiply(input.getV1());
        Vec4 newV2 = matrix.multiply(input.getV2());
        Vec4 newV3 = matrix.multiply(input.getV3());
        Vec4 newN1 = matrix.multiply(input.getN1());
        Vec4 newN2 = matrix.multiply(input.getN2());
        Vec4 newN3 = matrix.multiply(input.getN3());
        Face face = new Face(newV1, newV2, newV3, newN1, newN2, newN3);
        pipeSuccessor.write(face);
    }
}
