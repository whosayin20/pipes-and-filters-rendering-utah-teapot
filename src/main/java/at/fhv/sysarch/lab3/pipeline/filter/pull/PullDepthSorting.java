package at.fhv.sysarch.lab3.pipeline.filter.pull;

import at.fhv.sysarch.lab3.obj.Face;

public class PullDepthSorting<I extends Face, O extends Face> implements IPullFilter<Face, Face> {

    private PullPipe<Face> pipePredecessor;

    @Override
    public void setPipePredecessor(PullPipe<Face> pipe) {
        this.pipePredecessor = pipe;
    }

    @Override
    public Face read() {
        if(hasNext()){
            return pipePredecessor.read();
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return pipePredecessor.hasNext();
    }
}
