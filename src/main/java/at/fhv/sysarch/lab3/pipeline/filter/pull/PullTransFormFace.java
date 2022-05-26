package at.fhv.sysarch.lab3.pipeline.filter.pull;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec4;

public class PullTransFormFace<I extends Face, O extends Face> implements IPullFilter<Face, Face> {

    private PullPipe<Face> pipePredecessor;
    private Mat4 transformMatrix;

    @Override
    public void setPipePredecessor(PullPipe<Face> pipe) {
        this.pipePredecessor = pipe;
    }

    @Override
    public Face read() {
        if (hasNext()){
            Face face = pipePredecessor.read();
            return new Face(
                    transformMatrix.multiply(face.getV1()),
                    transformMatrix.multiply(face.getV2()),
                    transformMatrix.multiply(face.getV3()),
                    transformMatrix.multiply(face.getN1()),
                    transformMatrix.multiply(face.getN2()),
                    transformMatrix.multiply(face.getN3())
            );

        }


        return null;
    }

    public void setTransformMatrix(Mat4 transformMatrix) {
        this.transformMatrix = transformMatrix;
    }

    @Override
    public boolean hasNext() {
        return pipePredecessor.hasNext();
    }
}
