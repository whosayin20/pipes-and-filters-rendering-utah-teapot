package at.fhv.sysarch.lab3.pipeline.filter.pull;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Vec3;
import javafx.scene.paint.Color;
import at.fhv.sysarch.lab3.pipeline.data.Pair;

public class PullLightenFace<I extends Pair<Face, Color>, O extends Pair<Face, Color>> implements IPullFilter<Pair<Face, Color>, Pair<Face, Color>> {

    private PullPipe<Pair<Face, Color>> pipePredecessor;
    private Vec3 lightPos;
    @Override
    public void setPipePredecessor(PullPipe<Pair<Face, Color>> pipe) {
        this.pipePredecessor = pipe;
    }

    public PullLightenFace(Vec3 lightPos) {
        this.lightPos = lightPos;
    }

    @Override
    public Pair<Face, Color> read() {
        if(!hasNext()){
            return null;
        }
        Pair<Face, Color> pair = pipePredecessor.read();
        Face face = pair.fst();
        Color color = pair.snd();
        float lightfac = face.getN1().toVec3().getUnitVector().dot(lightPos.getUnitVector());
        return new Pair<>(face, color.deriveColor(0, 1, lightfac, 1));
    }

    @Override
    public boolean hasNext() {
        return pipePredecessor.hasNext();
    }
}
