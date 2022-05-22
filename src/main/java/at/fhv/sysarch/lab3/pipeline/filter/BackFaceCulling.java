package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipe;

public class BackFaceCulling<I extends Face> implements IFilter<I, Face> {
    private Pipe pipeSuccessor;

    public void setPipeSuccessor(Pipe pipe) {
        this.pipeSuccessor = pipe;
    }

    public void write(Face face) {
        //dot product between the vertex and its normal and if it is larger than 0 the face has to be culled
        if (face.getV1().dot(face.getN1()) <= 0 || face.getV2().dot(face.getN2()) <= 0 || face.getV3().dot(face.getN3()) <= 0) {
            pipeSuccessor.write(face);
        }
    }
}