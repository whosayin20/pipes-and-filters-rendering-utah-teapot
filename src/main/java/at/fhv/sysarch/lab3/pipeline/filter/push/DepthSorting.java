package at.fhv.sysarch.lab3.pipeline.filter.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipe;

public class DepthSorting<I extends Face> implements IFilter<I, Face> {
    private Pipe pipeSuccessor;


    @Override
    public void setPipeSuccessor(Pipe pipe) {
        this.pipeSuccessor = pipe;
    }

    @Override
    public void write(I face) {
        pipeSuccessor.write(face);
    }
}
