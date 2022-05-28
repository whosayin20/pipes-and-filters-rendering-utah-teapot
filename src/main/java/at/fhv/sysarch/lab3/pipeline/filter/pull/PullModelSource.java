package at.fhv.sysarch.lab3.pipeline.filter.pull;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.Pipe;

import java.util.ArrayList;
import java.util.List;

public class PullModelSource<I, O extends Face> implements IPullFilter<I, Face> {
    private List<Face> faces;
    private PullPipe<I> pipePredecessor;

    @Override
    public void setPipePredecessor(PullPipe<I> pipe) {

        this.pipePredecessor = pipe;
    }

    @Override
    public Face read() {

        if(hasNext()) {
            return faces.remove(0);
        }
        return null;

    }

    @Override
    public boolean hasNext() {
        return !faces.isEmpty();
    }

    public void setModel(Model model){
        this.faces = new ArrayList<>(model.getFaces());
    }
}
