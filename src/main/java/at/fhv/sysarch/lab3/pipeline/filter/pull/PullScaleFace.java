package at.fhv.sysarch.lab3.pipeline.filter.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.Pipe;

import java.util.ArrayList;
import java.util.List;

public class PullScaleFace<I extends Face, O extends Face> implements  IPullFilter<Face, Face> {
    private PullPipe<Face> pipePredecessor;
    int screenResolution;
    float scalar;

    public PullScaleFace(int viewWidth, int viewHeight) {
        this.screenResolution = viewWidth * viewHeight;
        float SCALE_CONST = (float) (100.0 / screenResolution); // z = 100 / (x * y) - always scales teapot to 100, independent of screen resolution
        this.scalar = SCALE_CONST * screenResolution;
    }

    @Override
    public void setPipePredecessor(PullPipe<Face> pipe) {

        this.pipePredecessor = pipe;
    }

    @Override
    public Face read() {

        if(hasNext()){
            Face face = pipePredecessor.read();
            return new Face(face.getV1().multiply(scalar), face.getV2().multiply(scalar), face.getV3().multiply(scalar), face);

        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return pipePredecessor.hasNext();
    }
}
