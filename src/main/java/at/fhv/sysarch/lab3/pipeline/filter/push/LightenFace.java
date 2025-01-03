package at.fhv.sysarch.lab3.pipeline.filter.push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Pipe;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import com.hackoeur.jglm.Vec3;
import javafx.scene.paint.Color;

public class LightenFace<I extends Pair<Face, Color>> implements IFilter<I, Pair<Face, Color>> {

    private Pipe<Pair<Face,Color>> pipeSuccessor;

    private Vec3 lightPos;

    public LightenFace(Vec3 lightPos) {
        this.lightPos = lightPos;
    }

    @Override
    public void setPipeSuccessor(Pipe<Pair<Face,Color>> pipe) {
        this.pipeSuccessor = pipe;
    }

    @Override
    public void write(I pair) {
        Pair<Face, Color> newPair = pair;
        Face face = pair.fst();
        Color color = pair.snd();


        //If the dot product is below or equal zero it means that the face is not facing the light and is therefore black (assuming no other indirect light).
        float dotProduct = face.getN1().toVec3().dot(lightPos.getUnitVector());
        if (dotProduct <= 0)  {
            pipeSuccessor.write(new Pair<>(face, Color.BLACK));
        }
        pipeSuccessor.write(new Pair<>(face, color.deriveColor(0, 1, dotProduct, 1)));
    }

    private float avgN(Face face) {
        return (face.getN1().getZ() + face.getV2().getZ() + face.getV3().getZ()) / 3;
    }
}