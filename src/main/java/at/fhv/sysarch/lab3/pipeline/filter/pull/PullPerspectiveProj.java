package at.fhv.sysarch.lab3.pipeline.filter.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Mat4;
import javafx.scene.paint.Color;

public class PullPerspectiveProj<I extends Pair<Face, Color>, O extends Pair<Face, Color>> implements IPullFilter<Pair<Face, Color>, Pair<Face, Color>>  {

    private PullPipe<Pair<Face, Color>> pipePredecessor;
    private Mat4 perspectiveMatrix;
    @Override
    public void setPipePredecessor(PullPipe<Pair<Face, Color>> pipe) {
        this.pipePredecessor = pipe;
    }

    public PullPerspectiveProj(Mat4 perspectiveMatrix){
        this.perspectiveMatrix = perspectiveMatrix;
    }

    @Override
    public Pair<Face, Color> read() {
        if(hasNext()){
            Pair<Face, Color> pair = pipePredecessor.read();
            Face face = pair.fst();
            Face newFace = new Face(
                    perspectiveMatrix.multiply(face.getV1()),
                    perspectiveMatrix.multiply(face.getV2()),
                    perspectiveMatrix.multiply(face.getV3()),
                    face
            );
            return new Pair<>(newFace, pair.snd());
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return pipePredecessor.hasNext();
    }
}
