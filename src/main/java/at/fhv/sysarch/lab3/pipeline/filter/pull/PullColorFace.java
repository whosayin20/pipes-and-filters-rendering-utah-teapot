package at.fhv.sysarch.lab3.pipeline.filter.pull;


import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class PullColorFace<I extends Face, O extends Pair<Face, Color>> implements IPullFilter<Face, Pair<Face, Color>> {

    private PullPipe<Face> pipePredecessor;
    private Color color;
    @Override
    public void setPipePredecessor(PullPipe<Face> pipe) {
        this.pipePredecessor = pipe;
    }

    public PullColorFace(Color color){
        this.color = color;
    }

    @Override
    public Pair<Face, Color> read() {
        if(hasNext()){
            return new Pair<>(pipePredecessor.read(), this.color);
        }
        return null;
    }

    @Override
    public boolean hasNext() {
        return pipePredecessor.hasNext();
    }
}
