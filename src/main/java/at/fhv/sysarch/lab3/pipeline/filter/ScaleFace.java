package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipe;

public class ScaleFace<I extends Face> implements IFilter<Face, Face> {
    private Pipe pipeSuccessor;
    int screenResolution;
    float scalar;

    public ScaleFace(int viewWidth, int viewHeight) {
        this.screenResolution = viewWidth * viewHeight;
        float SCALE_CONST = (float) (100.0 / screenResolution); // z = 100 / (x * y)
        this.scalar = SCALE_CONST * screenResolution;
    }

    public void setPipeSuccessor(Pipe<Face> pipe) {
        this.pipeSuccessor = pipe;
    }

    //Hier wird es skaliert
    public void write(Face face) { //SourceCode bleibt für push und pull gleich und struktur ein bisschen ändern
        Face scaledFace = new Face(face.getV1().multiply(scalar), face.getV2().multiply(scalar), face.getV3().multiply(scalar), face);
        this.pipeSuccessor.write(scaledFace);
    }
}
