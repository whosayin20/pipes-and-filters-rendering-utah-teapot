package at.fhv.sysarch.lab3.pipeline.filter.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.Pipe;

public class ModelSource implements IFilter<Model,Face> {

    private Pipe<Face> pipeSuccessor;

    public void setPipeSuccessor(Pipe<Face> pipe) {
        this.pipeSuccessor = pipe;
    }

    public void write(Model model) {
        for(Face face : model.getFaces()){
            pipeSuccessor.write(face);
        }
    }
}
