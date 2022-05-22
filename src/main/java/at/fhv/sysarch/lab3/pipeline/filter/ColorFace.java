package at.fhv.sysarch.lab3.pipeline.filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipe;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.scene.paint.Color;

public class ColorFace implements IFilter<Face, Pair<Face, Color>> {
    private Pipe<Pair<Face,Color>> pipeSuccessor;

    private Color color;

    public ColorFace(Color color) {
        this.color = color;
    }

    @Override
    public void setPipeSuccessor(Pipe<Pair<Face,Color>> pipe) {
        this.pipeSuccessor = pipe;
    }

    @Override
    public void write(Face input) {
        Pair<Face, Color> pair = new Pair<>(input, color);
        pipeSuccessor.write(pair);
    }
}